package br.com.telefonica.gd.service;

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
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import br.com.telefonica.gd.enums.ResponseEnum;
import br.com.telefonica.gd.model.ClienteModel;
import br.com.telefonica.gd.model.DocumentoModel;
import br.com.telefonica.gd.model.TipoDocumentacaoModel;
import br.com.telefonica.gd.repository.TipoDocumentacaoRepository;
import br.com.telefonica.gd.request.DocumentoRequest;
import br.com.telefonica.gd.request.TipoDocumentacaoRequest;
import br.com.telefonica.gd.response.ListaProjetoTipoDocumentacaoResponse;
import br.com.telefonica.gd.response.ListaTipoDocumentacaoResponse;
import br.com.telefonica.gd.response.ProjetoClienteResponse;
import br.com.telefonica.gd.response.ProjetoTipoDocumentacaoResponse;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.response.TipoDocumentacaoResponse;
import br.com.telefonica.gd.util.DataUtil;

@Service
public class TipoDocumentacaoService {
	
	private static final Logger logger = LogManager.getLogger(TipoDocumentacaoService.class);

	@Autowired
	private ModelMapper modelMapper;
	
    @Autowired
    private DataUtil dataUtil;
	
	@Autowired
	private TipoDocumentacaoRepository tipoDocumentacaoRepository;
	
	public Response save(TipoDocumentacaoRequest tipoDocumentacaoRequest) throws Exception{
		
		try {	
			
			TipoDocumentacaoModel tipoDocumentacaoModel = convert(tipoDocumentacaoRequest);
			
			if( tipoDocumentacaoModel.getDocumentos() != null ) {

				List<DocumentoModel> docsModel = tipoDocumentacaoModel.getDocumentos()
					.stream()
					.map( d-> montaDocumento(d) )
					.collect(Collectors.toList());
				
				tipoDocumentacaoModel.setDocumentos(docsModel);			
			}
			
			
			tipoDocumentacaoModel.setDataCadastro(dataUtil.dataAtual());
			
			tipoDocumentacaoRepository.save( tipoDocumentacaoModel );
			
			return new Response();
		}catch (Exception e) {
			logger.error( String.format("Erro ao salvar o tipo de licenciamento %s", e) );
			throw e;
		}
	}
	
	private DocumentoModel montaDocumento(DocumentoModel documentoModel) {
		documentoModel.setId( UUID.randomUUID().toString() );
		return documentoModel;
	}
	
	public Response update(String id, TipoDocumentacaoRequest tipoDocumentacaoRequest) throws Exception, NotFoundException{
		
		try {
		
			Optional<TipoDocumentacaoModel> tipoDocumentacaoModel = tipoDocumentacaoRepository.findById(id);
			if( tipoDocumentacaoModel.isPresent() ) {
				
				TipoDocumentacaoModel _tipoDocumentacaoModel = tipoDocumentacaoModel.get();
				
				if(!Strings.isNullOrEmpty(tipoDocumentacaoRequest.getNome()) ) {
					_tipoDocumentacaoModel.setNome(tipoDocumentacaoRequest.getNome());
				}
		
				if(tipoDocumentacaoRequest.getDocumentos() != null 
						&& !tipoDocumentacaoRequest.getDocumentos().isEmpty() ) {
					
					
					List<DocumentoModel> listaDocModel = new ArrayList<>();
					
					tipoDocumentacaoRequest.getDocumentos()
						.stream()
						.forEach( doc -> {
							DocumentoModel docModel = new DocumentoModel();
							
							if(doc.getId() == null || !doc.getId().contains("-")) {
								docModel.setId(UUID.randomUUID().toString());
							}else {
								docModel.setId(doc.getId());
							}
							
							docModel.setNome(doc.getNome());
							docModel.setObrigatorio(doc.isObrigatorio());
							docModel.setDataCadastro( dataUtil.formataData( doc._getDataCadastro()));
							listaDocModel.add( docModel );
						});
					
					_tipoDocumentacaoModel.setDocumentos(listaDocModel);
				}
				
				tipoDocumentacaoRepository.save(_tipoDocumentacaoModel);
				return new Response();
			}
			throw new NotFoundException();
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao atualizar o tipo de licenciamento %s", e) );
			throw e;
		}
	}
	
	public Response delete(String id) throws Exception{
		
		try {
			
			tipoDocumentacaoRepository.deleteById(id);
			return new Response();
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao deletar o tipo de licenciamento %s", e) );
			throw e;
		}
	}
	
	public Response get(String id) throws Exception, NotFoundException{
		
		try {
			Optional<TipoDocumentacaoModel> tipoDocumentacaoModel = tipoDocumentacaoRepository.findById(id);
			
			if( tipoDocumentacaoModel.isPresent() ) {
				return convert(tipoDocumentacaoModel.get());
			}
			throw new NotFoundException();
			
		}catch (Exception e) {
			logger.error( String.format("Erro ao recuperar o cliente %s", e) );
			throw e;
		}
	}
	
	public Response list() throws Exception{
		
		try {
		
			List<TipoDocumentacaoResponse> list = tipoDocumentacaoRepository.findAll().parallelStream()
					.map( this::convert )
					.collect( Collectors.toList() );
				
				return new ListaTipoDocumentacaoResponse(list);
				
		}catch (Exception e) {
			logger.error( String.format("Erro ao recuperar o cliente %s", e) );
			throw e;
		}
	}
	
	public Response listProjetoTipoDocumentacao() throws Exception{
		
		try {
		
			List<ProjetoTipoDocumentacaoResponse> list = tipoDocumentacaoRepository.findAll().parallelStream()
					.map( this::convertProjetoTipoDocumentacao )
					.collect( Collectors.toList() );
				
				return new ListaProjetoTipoDocumentacaoResponse(list);
				
		}catch (Exception e) {
			logger.error( String.format("Erro ao recuperar o cliente %s", e) );
			throw e;
		}
	}
	
	
	
	
	public TipoDocumentacaoModel convert(TipoDocumentacaoRequest tipoDocumentacaoRequest) {
		
		TipoDocumentacaoModel tipoDocumentacaoModel = modelMapper.map(tipoDocumentacaoRequest, TipoDocumentacaoModel.class);
		
		return tipoDocumentacaoModel;
	}
	
	public TipoDocumentacaoResponse convert(TipoDocumentacaoModel tipoDocumentacaoModel) {
		
		TipoDocumentacaoResponse tipoDocumentacaoResponse = modelMapper.map(tipoDocumentacaoModel, TipoDocumentacaoResponse.class);	
		tipoDocumentacaoResponse.setDataCadastro( dataUtil.formataData(tipoDocumentacaoModel.getDataCadastro()) );
		
		if(tipoDocumentacaoResponse.getDocumentos() != null) {
			tipoDocumentacaoResponse.setQuantidadeDocumentos( tipoDocumentacaoResponse.getDocumentos().size() );
		}else {
			tipoDocumentacaoResponse.setDocumentos(new ArrayList<>() );
			tipoDocumentacaoResponse.setQuantidadeDocumentos(0);
		}
		
		return tipoDocumentacaoResponse;
	}
	
	
	public ProjetoTipoDocumentacaoResponse convertProjetoTipoDocumentacao(TipoDocumentacaoModel tipoDocumentacaoModel) {
		
		ProjetoTipoDocumentacaoResponse projetoTipoDocumentacaoResponse = modelMapper.map(tipoDocumentacaoModel, ProjetoTipoDocumentacaoResponse.class);
		projetoTipoDocumentacaoResponse.setDataCadastro( dataUtil.formataData( tipoDocumentacaoModel.getDataCadastro() ) );
		
		return projetoTipoDocumentacaoResponse;
	}

}
