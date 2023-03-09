package br.com.telefonica.gd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import br.com.telefonica.gd.dto.JwtUsuarioDto;
import br.com.telefonica.gd.enums.RoleEnum;
import br.com.telefonica.gd.model.ClienteModel;
import br.com.telefonica.gd.repository.ClienteRepository;
import br.com.telefonica.gd.request.ClienteRequest;
import br.com.telefonica.gd.response.ClienteResponse;
import br.com.telefonica.gd.response.ListaClienteResponse;
import br.com.telefonica.gd.response.ListaProjetoClienteResponse;
import br.com.telefonica.gd.response.ListaUsuarioResponse;
import br.com.telefonica.gd.response.ProjetoClienteResponse;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.response.UsuarioResponse;
import br.com.telefonica.gd.util.DataUtil;
import br.com.telefonica.gd.util.JwtTokenUtil;

@Service
public class ClienteService {

	private static final Logger logger = LogManager.getLogger(ClienteService.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private DataUtil dataUtil;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public Response save(ClienteRequest clienteRequest) throws Exception{
		
		try {
			ClienteModel clienteModel = convert(clienteRequest);
			clienteModel.setDataCadastro( dataUtil.dataAtual() );
			clienteRepository.save( clienteModel );
			return new Response();
		}catch (Exception e) {
			logger.error( String.format("Erro ao salvar o cliente %s", e) );
			throw e;
		}
	}
	
	public Response update(String id, ClienteRequest clienteRequest) throws Exception, NotFoundException{
		
		try {
		
			Optional<ClienteModel> clienteModel = clienteRepository.findById(id);	
			if( clienteModel.isPresent() ) {
				
				ClienteModel _clienteModel = clienteModel.get();
				
				if(!Strings.isNullOrEmpty(clienteRequest.getCep()) ) {
					_clienteModel.setCep(clienteRequest.getCep());
				}
				if(!Strings.isNullOrEmpty(clienteRequest.getCidade()) ) {
					_clienteModel.setCidade(clienteRequest.getCidade());
				}
				if(!Strings.isNullOrEmpty(clienteRequest.getCnpj()) ) {
					_clienteModel.setCnpj(clienteRequest.getCnpj());
				}
				if(!Strings.isNullOrEmpty(clienteRequest.getEndereco()) ) {
					_clienteModel.setEndereco(clienteRequest.getEndereco());
				}
				if(!Strings.isNullOrEmpty(clienteRequest.getEstado()) ) {
					_clienteModel.setEstado(clienteRequest.getEstado());
				}
				if(!Strings.isNullOrEmpty(clienteRequest.getRazaoSocial()) ) {
					_clienteModel.setRazaoSocial(clienteRequest.getRazaoSocial());
				}
				if(!Strings.isNullOrEmpty(clienteRequest.getSegmento()) ) {
					_clienteModel.setSegmento(clienteRequest.getSegmento());
				}
				
				clienteRepository.save(_clienteModel);
				return new Response();
			}
			
			throw new NotFoundException();
		
		}catch (Exception e) {
			logger.error( String.format("Erro ao atualizar o cliente %s", e) );
			throw e;
		}
	}
	
	public Response delete(String id) throws Exception{
		
		try {
			clienteRepository.deleteById(id);
			return new Response();
		}catch (Exception e) {
			logger.error( String.format("Erro ao deletar o cliente %s", e) );
			throw e;
		}
	}
	
	public Response get(String id) throws Exception, NotFoundException{
		
		try {
			
			Optional<ClienteModel> clienteModel = clienteRepository.findById(id);
			if( clienteModel.isPresent() ) {
				return convert(clienteModel.get());
			}
			throw new NotFoundException();
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao recuperar o cliente %s", e) );
			throw e;
		}
	}
	
	public Response listSharing(String requestToken) throws Exception{
		
		try {
			
			JwtUsuarioDto jwtUsuarioDto = jwtTokenUtil.getJwtUsuarioFromToken(jwtTokenUtil.recuperaToken(requestToken));
			
			List<ClienteResponse> list = null;
			
			if(RoleEnum.ROLE_MASTER.getNome().equals( jwtUsuarioDto.getPerfil() )) {
			
				list = clienteRepository.findBySharingTrue().parallelStream()
					.filter( c -> !c.isMaster() )
					.map( this::convert )
					.collect( Collectors.toList() );
			
			}else {
				
				Optional<ClienteModel> clienteModel = clienteRepository.findById(jwtUsuarioDto.getIdCliente() );

				list = new ArrayList<>();
				if( clienteModel.isPresent() ) {
					list.add(convert(clienteModel.get()));
				}
			}

			return new ListaClienteResponse(list);
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao listar os usuários %s", e) );
			throw e;
		}
	}
	
	public Response list(String requestToken) throws Exception, NotFoundException{

		try {
		
			JwtUsuarioDto jwtUsuarioDto = jwtTokenUtil.getJwtUsuarioFromToken(jwtTokenUtil.recuperaToken(requestToken));
			
			List<ClienteResponse> list =  null;
			
			if(RoleEnum.ROLE_MASTER.getNome().equals( jwtUsuarioDto.getPerfil() )) {
				
				list = clienteRepository.findBySharingFalse().parallelStream()
						.filter( c -> !c.isMaster() )
						.map( this::convert )
						.collect( Collectors.toList() );
			}else {
				
				Optional<ClienteModel> clienteModel = clienteRepository.findById(jwtUsuarioDto.getIdCliente() );

				list = new ArrayList<>();
				if( clienteModel.isPresent() ) {
					list.add(convert(clienteModel.get()));
				}
			}
			
			return new ListaClienteResponse(list);
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao listar os clientes %s", e) );
			throw e;
		}
	}
	
	public Response listProjetoCliente(String requestToken) throws Exception{

		try {
			
			JwtUsuarioDto jwtUsuarioDto = jwtTokenUtil.getJwtUsuarioFromToken(jwtTokenUtil.recuperaToken(requestToken));
			
			List<ProjetoClienteResponse> list  = null;
			if(RoleEnum.ROLE_MASTER.getNome().equals( jwtUsuarioDto.getPerfil() )) {
				
				list = clienteRepository.findBySharingFalse().parallelStream()
						.filter( c -> !c.isMaster() )
						.map( this::convertProjetoCliente )
						.collect( Collectors.toList() );
					
			}else {
				
				Optional<ClienteModel> clienteModel = clienteRepository.findById(jwtUsuarioDto.getIdCliente());

				list = new ArrayList<>();
				if( clienteModel.isPresent() ) {
					list.add(convertProjetoCliente(clienteModel.get()));
				}
			}

			return new ListaProjetoClienteResponse(list);
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao listar os clientes para o projeto %s", e) );
			throw e;
		}
	}
	
	
	
	public ClienteModel convert(ClienteRequest clienteRequest) {
		
		ClienteModel clienteModel = modelMapper.map(clienteRequest, ClienteModel.class);
		return clienteModel;
	}
	
	public ClienteResponse convert(ClienteModel clienteModel) {
		
		ClienteResponse clienteResponse = modelMapper.map(clienteModel, ClienteResponse.class);
		clienteResponse.setDataCadastro(dataUtil.formataData(clienteModel.getDataCadastro()));
		
		return clienteResponse;
	}
	
	public ProjetoClienteResponse convertProjetoCliente(ClienteModel clienteModel) {
		
		ProjetoClienteResponse projetoClienteResponse = modelMapper.map(clienteModel, ProjetoClienteResponse.class);
		
		return projetoClienteResponse;
	}
}
