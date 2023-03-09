package br.com.telefonica.gd.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import br.com.telefonica.gd.dto.EmailDto;
import br.com.telefonica.gd.dto.JwtUsuarioDto;
import br.com.telefonica.gd.enums.ResponseEnum;
import br.com.telefonica.gd.enums.RoleEnum;
import br.com.telefonica.gd.model.ClienteModel;
import br.com.telefonica.gd.model.UsuarioModel;
import br.com.telefonica.gd.repository.ClienteRepository;
import br.com.telefonica.gd.repository.UsuarioRepository;
import br.com.telefonica.gd.request.RecuperaSenhaRequest;
import br.com.telefonica.gd.request.UsuarioRequest;
import br.com.telefonica.gd.response.ClienteResponse;
import br.com.telefonica.gd.response.ListaUsuarioResponse;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.response.UsuarioResponse;
import br.com.telefonica.gd.util.DataUtil;
import br.com.telefonica.gd.util.JwtTokenUtil;

@Service
public class UsuarioService {

	private static final Logger logger = LogManager.getLogger(UsuarioService.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private EnviarEmailService enviarEmailService;
	
    @Autowired
    private DataUtil dataUtil;
	
    @Autowired
    private ClienteRepository clienteRepository;
    
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
    
    private void setaClienteMasterSistemico(UsuarioModel usuarioModel) throws Exception{
    	
    	if(usuarioModel.getPerfil().equals(RoleEnum.ROLE_MASTER.getNome())){
		
    		Optional<ClienteModel> clienteMasterModel = clienteRepository.findByMasterTrue();
    		
    		if( clienteMasterModel.isPresent() ) {
    			usuarioModel.setCliente( clienteMasterModel.get() );
    		}
    		throw new Exception("Não foi localizado o cliente master da aplicação");		
    		
		}
    }
    
	public Response save(UsuarioRequest usuarioRequest) throws Exception{
		
		try {
			
			UsuarioModel usuarioModel = convert(usuarioRequest);
			
			usuarioModel.setSenha(autenticacaoService.criptografar(usuarioModel.getSenha()));
			usuarioModel.setDataCadastro(dataUtil.dataAtual());
			usuarioModel.setPrimeiroAcesso(true);

			setaClienteMasterSistemico(usuarioModel);
			
			usuarioRepository.save(usuarioModel);
			
			return new Response();
		
		}catch (Exception e) {
			logger.error( String.format("Erro ao salvar o usuário %s", e) );
			throw e;
		}
	}
	
	public Response update(String id, UsuarioRequest usuarioRequest) throws Exception, NotFoundException{
		
		try {
		
			Optional<UsuarioModel> usuarioModel = usuarioRepository.findById(id);
			if( usuarioModel.isPresent() ) {
				
				UsuarioModel _usuarioModel = usuarioModel.get();
				
				if(!Strings.isNullOrEmpty(usuarioRequest.getNome()) ) {
					_usuarioModel.setNome(usuarioRequest.getNome());
				}
				
				if(!Strings.isNullOrEmpty(usuarioRequest.getTelefone()) ) {
					_usuarioModel.setTelefone(usuarioRequest.getTelefone());
				}
				if(!Strings.isNullOrEmpty(usuarioRequest.getEmail()) ) {
					_usuarioModel.setEmail(usuarioRequest.getEmail());
				}
				
				if( usuarioRequest.isPerfilAdministrador() ) {
					_usuarioModel.setPerfil( RoleEnum.ROLE_MASTER.name() );
				}else if( usuarioRequest.isSharing() ) {
					_usuarioModel.setPerfil( RoleEnum.ROLE_SHARING.name() );
				}else {
					_usuarioModel.setPerfil( RoleEnum.ROLE_BASIC.name() );
				}

				if( usuarioRequest.getCliente() != null && 
						!Strings.isNullOrEmpty(usuarioRequest.getCliente().getId()) ) {
					
					_usuarioModel.setCliente( clienteRepository.findById(usuarioRequest.getCliente().getId()).get() );
				}
				
				_usuarioModel.setPrimeiroAcesso(usuarioRequest.isPrimeiroAcesso());  
				
				setaClienteMasterSistemico(_usuarioModel);
				
				usuarioRepository.save(_usuarioModel);
				return new Response();
			}
			throw new NotFoundException();
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao atualizar o usuário %s", e) );
			throw e;
		}
	}
	
	public Response delete(String id) throws Exception{
		
		try {	
			usuarioRepository.deleteById(id);
			return new Response();
		}catch (Exception e) {
			logger.error( String.format("Erro ao deletar o usuário %s", e) );
			throw e;
		}
	}
	
	public Response get(String id) throws Exception, NotFoundException{
		
		try {
			
			Optional<UsuarioModel> usuarioModel = usuarioRepository.findById(id);
			if( usuarioModel.isPresent() ) {
				return convert(usuarioModel.get());
			}
			throw new NotFoundException();
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao recuperar o usuário %s", e) );
			throw e;
		}
	}
	
	public Response getCliente(String idUsuario) throws Exception{
		
		try {
			
			Optional<UsuarioModel> usuarioModel = usuarioRepository.findById(idUsuario);
			if( usuarioModel.isPresent() ) {
				return clienteService.convert(usuarioModel.get().getCliente());
			}
			throw new NotFoundException();
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao recuperar o cliente %s", e) );
			throw e;
		}
	}
	
	public Response listSharing() throws Exception{
		
		try {
			
			List<UsuarioResponse> list = usuarioRepository.findByPerfil(RoleEnum.ROLE_SHARING.name()).parallelStream()
				.map( this::convert )
				.collect( Collectors.toList() );

			return new ListaUsuarioResponse(list);
		}catch (Exception e) {
			logger.error( String.format("Erro ao listar os usuários %s", e) );
			throw e;
		}
	}
	
	public Response list(String requestToken) throws Exception{
		
		try {
			
			JwtUsuarioDto jwtUsuarioDto = jwtTokenUtil.getJwtUsuarioFromToken(jwtTokenUtil.recuperaToken(requestToken));
			
			List<UsuarioResponse> list = null;
			
			
			
			if(RoleEnum.ROLE_MASTER.getNome().equals( jwtUsuarioDto.getPerfil() )) {
			
				List<String> perfis = new ArrayList<String>(Arrays.asList (new String[]{
						RoleEnum.ROLE_MASTER.name(), RoleEnum.ROLE_BASIC.name(), RoleEnum.ROLE_SHARING.name()}));
				
				list = usuarioRepository.findByPerfilIn(perfis).parallelStream()
						.map( this::convert )
						.collect( Collectors.toList() );
				
			}else if(RoleEnum.ROLE_SHARING.getNome().equals( jwtUsuarioDto.getPerfil() )) {	
				
				List<String> perfis = new ArrayList<String>(Arrays.asList (new String[]{
						RoleEnum.ROLE_SHARING.name()}));
				
				list = usuarioRepository.findByClienteIdAndPerfilIn(jwtUsuarioDto.getIdCliente(), perfis).parallelStream()
						.map( this::convert )
						.collect( Collectors.toList() );
				
			}else{	
				
				List<String> perfis = new ArrayList<String>(Arrays.asList (new String[]{
						RoleEnum.ROLE_BASIC.name()}));
				
				list = usuarioRepository.findByClienteIdAndPerfilIn(jwtUsuarioDto.getIdCliente(), perfis).parallelStream()
						.map( this::convert )
						.collect( Collectors.toList() );
			}
			
			
			return new ListaUsuarioResponse(list);
		}catch (Exception e) {
			logger.error( String.format("Erro ao listar os usuários %s", e) );
			throw e;
		}
	}
	
	public Response atualizarSenha(UsuarioRequest usuarioRequest) throws Exception, NotFoundException{
		
		try {
			
			Optional<UsuarioModel> usuarioModel = usuarioRepository.findByEmail(usuarioRequest.getEmail());
			
			if( usuarioModel.isPresent() ) {
				
				UsuarioModel _usuarioModel = usuarioModel.get();
				
				_usuarioModel.setSenha( autenticacaoService.criptografar( usuarioRequest.getSenha() ) );
				_usuarioModel.setPrimeiroAcesso(false);
				
				usuarioRepository.save(_usuarioModel);
				
				return new Response();
			}else {
				throw new UsernameNotFoundException(String.format("Usuário não encontrado com o e-mail: %s", usuarioRequest.getEmail()) );
			}
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao atualizarSenha senha do usuário %s", e) );
			throw e;
		}
	}
	
	public Response recuperarSenha(RecuperaSenhaRequest recuperaSenhaRequest) throws Exception, NotFoundException{
		
		try {
			
			Optional<UsuarioModel> usuarioModel = usuarioRepository.findByEmail(recuperaSenhaRequest.getEmail());
			
			if( usuarioModel.isPresent() ) {
				
				UsuarioModel _usuarioModel = usuarioModel.get();
				
				String novaSenha = autenticacaoService.gerarSenha();
	
				_usuarioModel.setSenha(autenticacaoService.criptografar( novaSenha ));
				_usuarioModel.setPrimeiroAcesso(true);
				
				usuarioRepository.save(_usuarioModel);
				
				EmailDto emailDto = new EmailDto();
				emailDto.setAssunto("Recupera senha");
				emailDto.setEmailDestino(recuperaSenhaRequest.getEmail());
				
				String[] mensagens = new String[2];
				
				mensagens[0] = _usuarioModel.getNome();
				mensagens[1] = novaSenha;
				
				emailDto.setMensagem(mensagens);
				
				enviarEmailService.recuperaSenha(emailDto);
				
				return new Response();
			}else {
				throw new UsernameNotFoundException(String.format("Usuário não encontrado com o e-mail: %s", recuperaSenhaRequest.getEmail()) );
			}
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao recuperar senha do usuário %s", e) );
			throw e;
		}
	}
	
	private UsuarioModel convert(UsuarioRequest usuarioRequest) {
		
		UsuarioModel usuarioModel = modelMapper.map(usuarioRequest, UsuarioModel.class);

		if(usuarioRequest.isPerfilAdministrador() ) {
			usuarioModel.setPerfil(RoleEnum.ROLE_MASTER.name() );
		}else if(usuarioRequest.isSharing() ) {
			usuarioModel.setPerfil(RoleEnum.ROLE_SHARING.name());
		}else {
			usuarioModel.setPerfil(RoleEnum.ROLE_BASIC.name());
		}
		
		
		return usuarioModel;
	}
	
	private UsuarioResponse convert(UsuarioModel usuarioModel) {
		
		UsuarioResponse usuarioResponse = modelMapper.map(usuarioModel, UsuarioResponse.class);
		usuarioResponse.setDataCadastro( dataUtil.formataData(usuarioModel.getDataCadastro()) );
		
		//if(usuarioModel.getCliente() != null) {
		//	usuarioResponse.setRazaoSocialCliente(usuarioModel.getCliente().getRazaoSocial());
		//}
		
		if(usuarioResponse.getPerfil().equals(RoleEnum.ROLE_MASTER.name())){
			usuarioResponse.setPerfilAdministrador(true);
		}else if(usuarioResponse.getPerfil().equals(RoleEnum.ROLE_SHARING.name())){
			usuarioResponse.setSharing(true);
		}
		
		RoleEnum roleEnum = RoleEnum.fromEnum(usuarioResponse.getPerfil());
		if( roleEnum != null ) {
			usuarioResponse.setPerfil( roleEnum.getNome() );
		}
		
		return usuarioResponse;
	}
}
