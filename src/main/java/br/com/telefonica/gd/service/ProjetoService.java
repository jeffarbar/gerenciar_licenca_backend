package br.com.telefonica.gd.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.google.common.base.Strings;

import br.com.telefonica.gd.enums.SituacaoArquivoEnum;
import br.com.telefonica.gd.enums.SituacaoDocumentacaoEnum;
import br.com.telefonica.gd.model.ClienteModel;
import br.com.telefonica.gd.model.NotificacaoModel;
import br.com.telefonica.gd.model.ProjetoClienteModel;
import br.com.telefonica.gd.model.ProjetoClienteSharingModel;
import br.com.telefonica.gd.model.ProjetoDocumentoModel;
import br.com.telefonica.gd.model.ProjetoModel;
import br.com.telefonica.gd.model.ProjetoTipoDocumentacaoModel;
import br.com.telefonica.gd.repository.ClienteRepository;
import br.com.telefonica.gd.repository.ProjetoRepository;
import br.com.telefonica.gd.request.ProjetoClienteRequest;
import br.com.telefonica.gd.request.ProjetoDocumentoRequest;
import br.com.telefonica.gd.request.DocumentoRequest;
import br.com.telefonica.gd.request.NotificacaoRequest;
import br.com.telefonica.gd.request.ProjetoRequest;
import br.com.telefonica.gd.request.ProjetoTipoDocumentacaoRequest;
import br.com.telefonica.gd.request.TipoDocumentacaoRequest;
import br.com.telefonica.gd.response.ListaProjetoResponse;
import br.com.telefonica.gd.response.ProjetoResponse;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.response.TipoDocumentacaoResponse;
import br.com.telefonica.gd.util.DataUtil;

@Service
public class ProjetoService {
	
	private static final Logger logger = LogManager.getLogger(ProjetoService.class);

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ProjetoRepository projetoRepository;
		
    @Autowired
    private DataUtil dataUtil;
    

	public Response save(ProjetoRequest projetoRequest) throws Exception{
		
		try {
				
			List<ProjetoTipoDocumentacaoRequest> listaTipoDocumentacao = projetoRequest.getTipoDocumentacoes();
			
			LocalDateTime data = dataUtil.dataAtual();
			
			listaTipoDocumentacao.stream().forEach( documentacaoRequest -> {

				ProjetoModel projetoModel = convert( projetoRequest );
				
				ProjetoTipoDocumentacaoModel tipoDocumentacaoModel = modelMapper.map(documentacaoRequest, ProjetoTipoDocumentacaoModel.class);
				
				ProjetoClienteModel projetoClienteModel = modelMapper.map(projetoRequest.getCliente(), ProjetoClienteModel.class);
				projetoModel.setCliente(projetoClienteModel);
				
				if( projetoRequest.getClienteSharing() != null ) {
					
					ProjetoClienteSharingModel  projetoClienteSharingModel = modelMapper.map(projetoRequest.getClienteSharing(), ProjetoClienteSharingModel.class);
					projetoModel.setClienteSharing(projetoClienteSharingModel);
					
				}
				
				List<ProjetoDocumentoModel> listaDocumento = new ArrayList<>();
				
				if(documentacaoRequest.getDocumentos() != null) {
					documentacaoRequest.getDocumentos()
						.stream()
						.forEach( docRequest ->{
							ProjetoDocumentoModel doc = modelMapper.map(docRequest, ProjetoDocumentoModel.class);
							doc.setStatusArquito(SituacaoArquivoEnum.PENDENTE.name());
							doc.setDataCadastro(data);
							listaDocumento.add(doc);
						});
						
					tipoDocumentacaoModel.setDocumentos(listaDocumento);
				}
				
				projetoModel.setProjetoTipoDocumentacao(tipoDocumentacaoModel);
				
				projetoModel.setDataCadastro(data);
				
				projetoRepository.save( projetoModel );
				
			});
			
			return new Response();
		}catch (Exception e) {
			logger.error( String.format("Erro ao salvar o projeto %s", e) );
			throw e;
		}
	}
	
	public Response update(String id, ProjetoRequest projetoRequest) throws Exception, NotFoundException{
		
		try {
			
			Optional<ProjetoModel> projetoModel = projetoRepository.findById(id);
			
			if( projetoModel.isPresent() ) {
				
				// caso tenho mais de uma licença na nova alteração criar um projeto novo
				List<ProjetoTipoDocumentacaoRequest> listaTipoDocumentacaoRequest = projetoRequest.getTipoDocumentacoes();
				
				if( listaTipoDocumentacaoRequest != null && !listaTipoDocumentacaoRequest.isEmpty() ) {
				
					ProjetoTipoDocumentacaoRequest tipoDocumentacaoRequest = listaTipoDocumentacaoRequest.get(0);
						
					ProjetoModel _projetoModel = projetoModel.get();
					
					_projetoModel.setNome(projetoRequest.getNome());
					_projetoModel.setCep( projetoRequest.getCep());
					_projetoModel.setCidade( projetoRequest.getCidade() );
					_projetoModel.setEstado(projetoRequest.getEstado());
					_projetoModel.setEndereco(projetoRequest.getEndereco());
					
					ProjetoClienteModel  _clienteProjetoModel = modelMapper.map(projetoRequest.getCliente(), ProjetoClienteModel.class);
					_projetoModel.setCliente(_clienteProjetoModel);_projetoModel.setCliente(_clienteProjetoModel);
					
					if(projetoRequest.getClienteSharing() != null) {	
						ProjetoClienteSharingModel  _clienteSharingProjetoModel = modelMapper.map(projetoRequest.getClienteSharing(), ProjetoClienteSharingModel.class);
						_projetoModel.setClienteSharing(_clienteSharingProjetoModel);
					}
						
					ProjetoTipoDocumentacaoModel _projetoTipoDocumentacaoModel = modelMapper.map(tipoDocumentacaoRequest, ProjetoTipoDocumentacaoModel.class);
					_projetoTipoDocumentacaoModel.setDataCadastro( _projetoModel.getProjetoTipoDocumentacao().getDataCadastro() );
				
					if( _projetoModel.getProjetoTipoDocumentacao().getDocumentos() != null ) {
						_projetoTipoDocumentacaoModel.setDocumentos(_projetoModel.getProjetoTipoDocumentacao().getDocumentos());
					}					
					
					_projetoModel.setProjetoTipoDocumentacao(_projetoTipoDocumentacaoModel);
					
					projetoRepository.save(_projetoModel);
				}
				
				return new Response(); 
			}
			
			throw new NotFoundException();
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao atualizar o projeto %s", e) );
			throw e;
		}
	}
	
	public Response delete(String id) throws Exception{
		
		try {
			
			projetoRepository.deleteById(id);
			return new Response();
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao deletar o projeto %s", e) );
			throw e;
		}
	}
	
	public Response get(String id) throws Exception, NotFoundException{
		
		try {
			
			Optional<ProjetoModel> projetoModel = projetoRepository.findById(id);
			
			if( projetoModel.isPresent() ) {
				return convert(projetoModel.get());
			}
			
			throw new NotFoundException();
		
		}catch (Exception e) {
			logger.error( String.format("Erro ao recuperar o projeto %s", e) );
			throw e;
		}
	}
	
	public Response listSharing(String idClienteSharing) throws Exception{
		
		try {
		
			List<ProjetoResponse> list = projetoRepository.findByClienteSharingIdOrderByDataCadastroDesc(idClienteSharing).parallelStream()
					.map( this::convert )
					.collect( Collectors.toList() );
		
			return new ListaProjetoResponse(list);
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao listar os projetos %s", e) );
			throw e;
		}
	}
	
	public Response list(String idCliente) throws Exception{
		
		try {
		
			List<ProjetoResponse> list = projetoRepository.findByClienteIdOrderByDataCadastroDesc(idCliente).parallelStream()
					.map( this::convert )
					.collect( Collectors.toList() );
		
			return new ListaProjetoResponse(list);
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao listar os projetos %s", e) );
			throw e;
		}
	}
	
	
	
	public Response listFinalizado() throws Exception{
		
		try {
			
			List<ProjetoResponse> list = projetoRepository.findAll( Sort.by(Sort.Direction.DESC, "dataCadastro") )
				.parallelStream()
				.filter( p -> !isProjetoPendente(p) )
				.map( this::convert )
				
				.collect( Collectors.toList() );
			
			return new ListaProjetoResponse(list);
		
		}catch (Exception e) {
			logger.error( String.format("Erro ao listar os projetos %s", e) );
			throw e;
		}
	}
	
	public Response list() throws Exception{
		
		try {
			
			List<ProjetoResponse> list = projetoRepository.findAll( Sort.by(Sort.Direction.DESC, "dataCadastro") )
				.parallelStream()
				.filter( p -> isProjetoPendente(p) )
				.map( this::convert )
				
				.collect( Collectors.toList() );
			
			return new ListaProjetoResponse(list);
		
		}catch (Exception e) {
			logger.error( String.format("Erro ao listar os projetos %s", e) );
			throw e;
		}
	}

	
	public boolean isProjetoPendente( ProjetoModel projetoModel ) {

		return !(projetoModel.getProjetoTipoDocumentacao() != null 
				&& projetoModel.getProjetoTipoDocumentacao().getDocumentos() != null
				&& projetoModel.getProjetoTipoDocumentacao().getDocumentos()
				.stream()
				.allMatch( d -> { return SituacaoArquivoEnum.APROVADO.name().equals(d.getStatusArquito() ); }));
		
	}
	
	public ProjetoModel convert(ProjetoRequest projetoRequest) {
		
		ProjetoModel projetoModel = modelMapper.map(projetoRequest, ProjetoModel.class);
		return projetoModel;
	}
	
	public ProjetoResponse convert(ProjetoModel projetoModel) {
		
		ProjetoResponse projetoResponse = modelMapper.map(projetoModel, ProjetoResponse.class);

		projetoResponse.setDataCadastro( dataUtil.formataData( projetoModel.getDataCadastro() ) );
		
		/*
		if(projetoResponse.getTipoLicenciamento() != null 
				&& projetoResponse.getTipoLicenciamento().getDocumentos() != null
				&& projetoResponse.getTipoLicenciamento().getDocumentos().isEmpty() ){
				
		*/
		
		
		if(projetoResponse.getTipoDocumentacao() != null 
				&& projetoResponse.getTipoDocumentacao().getDocumentos() != null
				&& projetoResponse.getTipoDocumentacao().getDocumentos()
				.stream()
				.allMatch( d -> { return SituacaoArquivoEnum.APROVADO.name().equals(d.getStatusArquito() ); })){
			
			projetoResponse.getTipoDocumentacao().setSituacaoDocumentacao( SituacaoDocumentacaoEnum.FINALIZADO.name() );
		}else {
			projetoResponse.getTipoDocumentacao().setSituacaoDocumentacao( SituacaoDocumentacaoEnum.PENDENTE.name() );
		}
	    
		
		return projetoResponse;
	}
}
