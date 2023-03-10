package br.com.telefonica.gd.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.telefonica.gd.dto.JwtUsuarioDto;
import br.com.telefonica.gd.enums.RoleEnum;
import br.com.telefonica.gd.model.UsuarioModel;
import br.com.telefonica.gd.repository.UsuarioRepository;
import br.com.telefonica.gd.request.JwtRequest;
import br.com.telefonica.gd.response.JwtResponse;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.util.JwtTokenUtil;

@Service
public class AutenticacaoService implements UserDetailsService{

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<UsuarioModel> usuarioModel = usuarioRepository.findByEmail(email);
		
		if ( usuarioModel.isPresent() ) {
			
			UsuarioModel _usuarioModel = usuarioModel.get();
			List<GrantedAuthority> authorities = new ArrayList<>();
			
			if( _usuarioModel.getPerfil() != null ) {
				authorities.add( new SimpleGrantedAuthority(_usuarioModel.getPerfil()) );
			}
			
			return new User( _usuarioModel.getEmail() , _usuarioModel.getSenha() ,authorities);
			
			//return new User(email, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",authorities);
		} else {
			throw new UsernameNotFoundException(String.format("Usu??rio n??o encontrado com o e-mail: %s", email) );
		}
	}
	
	
	public JwtResponse autenticar(JwtRequest authenticationRequest) throws Exception {
	
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(), authenticationRequest.getSenha() ));
			
			final UserDetails userDetails = loadUserByUsername(authenticationRequest.getEmail());
			
			UsuarioModel usuarioModel = usuarioRepository.findByEmail(authenticationRequest.getEmail()).get();
			
			boolean isMaster = userDetails.getAuthorities().stream()
					.filter( item -> RoleEnum.ROLE_MASTER.name().equals( item.getAuthority() ) ||
							RoleEnum.ROLE_ADMIN.name().equals( item.getAuthority() ))
					.findFirst().isPresent();
			
			String idCliente = null;
			String razaoSocial = null;
			
			if( usuarioModel.getCliente() != null ) {
				idCliente = usuarioModel.getCliente().getId();
				razaoSocial = usuarioModel.getCliente().getRazaoSocial();
			}
			
			final String token = jwtTokenUtil.generateToken(
					usuarioModel.getNome(), 
					usuarioModel.getEmail(), 
					idCliente, 
					RoleEnum.fromEnum(usuarioModel.getPerfil()).getNome());
			
			
			JwtResponse jwtResponse = new JwtResponse();		
					
			jwtResponse.setJwttoken(token);
			jwtResponse.setEmail(authenticationRequest.getEmail() );
			
			jwtResponse.setUsername( usuarioModel.getNome() );
			jwtResponse.setPrimeiroAcesso(usuarioModel.isPrimeiroAcesso());
			jwtResponse.setCliente( razaoSocial );
			jwtResponse.setIdCliente( idCliente );
			jwtResponse.setPerfil( RoleEnum.fromEnum(usuarioModel.getPerfil()).getNome() );
			
			jwtResponse.setMaster(isMaster);
			
			return jwtResponse;
			
		} catch (DisabledException e) {
			throw new Exception("Usu??rio desabilitado", e);
		} catch (BadCredentialsException e) {
			throw new Exception("Credenciais inv??lidas", e);
		}
	}
	
	public String criptografar(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}

	
	public String gerarSenha() {
		String s = gerarSenha(12);
		//System.out.println( s);
		return s;
	}
	
	public String gerarSenha(int len) {
		
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*()-[]";
		 
        SecureRandom random = new SecureRandom();
 
        return IntStream.range(0, len)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
	}

}
