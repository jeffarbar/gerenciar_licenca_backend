package br.com.telefonica.gd.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.telefonica.gd.enums.RoleEnum;
import br.com.telefonica.gd.model.ClienteModel;
import br.com.telefonica.gd.model.UsuarioModel;
import br.com.telefonica.gd.repository.ClienteRepository;
import br.com.telefonica.gd.repository.UsuarioRepository;
import br.com.telefonica.gd.request.UsuarioRequest;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.util.DataUtil;

@Service
public class CargaInicialService {

	private static final Logger logger = LogManager.getLogger(CargaInicialService.class);
	
	@Value("${email.usuario.principal}") 
	private String emailUsuarioPrincipal;
	
	@Value("${telefone.usuario.principal}") 
	private String telefoneUsuarioPrincipal;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
    @Autowired
    private DataUtil dataUtil;
	
    public Response cargaInicial() throws Exception{
    	
    	try {
    		
			ClienteModel clienteModel = cargaClientePrincipal();
			cargaUsuarioPrincipal(clienteModel);
			
			return new Response();
		
    	}catch (Exception e) {
			logger.error( String.format("Erro na carga inicial %s", e) );
			throw e;
		}
    }
    
	private ClienteModel cargaClientePrincipal() {
		
		Optional<ClienteModel> cliente = clienteRepository.findByMasterTrue();
		
		ClienteModel clienteMaster = null;
		if( cliente.isPresent() ){
			clienteMaster = cliente.get();
		}else {
			clienteMaster = new ClienteModel();
			clienteMaster.setDataCadastro( dataUtil.dataAtual() );
		}
		
		clienteMaster.setMaster(true);
		clienteMaster.setCep("04571-000");
		clienteMaster.setCidade("Cidade Monções");
		clienteMaster.setCnpj("02.558.157/0001-62");
		clienteMaster.setEndereco("Av. Engenheiro Luís Carlos Berrini, 1376");
		clienteMaster.setEstado("São Paulo");
		clienteMaster.setRazaoSocial("Telefônica Brasil S.A");
		clienteMaster.setSegmento("Telecomunicações");

		return clienteRepository.save( clienteMaster );
	}
	
	private void cargaUsuarioPrincipal(ClienteModel clienteModel) {
		
		Optional<UsuarioModel> usuario = usuarioRepository.findByEmail(emailUsuarioPrincipal);
		
		UsuarioModel usuarioPrincial = null;
		if(usuario.isPresent() ) {
			usuarioPrincial = usuario.get();
		}else {
			usuarioPrincial = new UsuarioModel();
			usuarioPrincial.setEmail(emailUsuarioPrincipal);
			usuarioPrincial.setDataCadastro(dataUtil.dataAtual());
		}
			
		usuarioPrincial.setCliente(clienteModel);
		usuarioPrincial.setNome("Usuário Admin");
		usuarioPrincial.setSenha( autenticacaoService.criptografar( autenticacaoService.gerarSenha() ) );
		usuarioPrincial.setPrimeiroAcesso(true);
		usuarioPrincial.setTelefone(telefoneUsuarioPrincipal);
		usuarioPrincial.setPerfil( RoleEnum.ROLE_ADMIN.name() );
			
		usuarioRepository.save(usuarioPrincial);
	}
	
}
