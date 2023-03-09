package br.com.telefonica.gd.service;

import com.google.common.base.Strings;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.telefonica.gd.dto.DocumentoDto;
import br.com.telefonica.gd.dto.EmailUploadArquivoDto;
import br.com.telefonica.gd.dto.JwtUsuarioDto;
import br.com.telefonica.gd.enums.RoleEnum;
import br.com.telefonica.gd.enums.SituacaoArquivoEnum;
import br.com.telefonica.gd.model.NotificacaoModel;
import br.com.telefonica.gd.model.ProjetoDocumentoModel;
import br.com.telefonica.gd.model.ProjetoModel;
import br.com.telefonica.gd.model.ProjetoTipoDocumentacaoModel;
import br.com.telefonica.gd.repository.ProjetoRepository;
import br.com.telefonica.gd.request.NotificacaoRequest;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.util.DataUtil;
import br.com.telefonica.gd.util.ImagemUtil;
import br.com.telefonica.gd.util.JwtTokenUtil;

@Service
public class DocumentoService {
	
	private static final Logger logger = LogManager.getLogger(DocumentoService.class);

	@Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;
    
    @Autowired
    private DataUtil dataUtil;
    
    @Autowired
    private ProjetoRepository projetoRepository;
    
    @Autowired
    private EnviarEmailService enviarEmailService;
    
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
    
	private static int COMPACTACAO_IMAGEM = 10;
    
    public Response notificacao(NotificacaoRequest notificacaoRequest, String idProjeto, String idDocumento, String requestToken) throws Exception{
    	
    	try {
    		
    		JwtUsuarioDto jwtUsuarioDto = jwtTokenUtil.getJwtUsuarioFromToken(jwtTokenUtil.recuperaToken(requestToken));
    				
    		Optional<ProjetoModel> projetoModel = projetoRepository.findById(idProjeto);
    		
    		if( projetoModel.isPresent() ) {
    		
    			ProjetoModel _projetoModel = projetoModel.get();
    			
    			ProjetoTipoDocumentacaoModel _projetoTipoDocumentacao = _projetoModel.getProjetoTipoDocumentacao();
    			
    			
    			boolean isMaster = RoleEnum.ROLE_SHARING.getNome().equals( jwtUsuarioDto.getPerfil());
    			
    			List<ProjetoDocumentoModel> _docs = _projetoTipoDocumentacao.getDocumentos()
		        		.stream()
		        		.map(d -> incluirNotificacao(d, idDocumento, 
		        				_projetoModel.getCliente().getId(),
		        				_projetoModel.getCliente().getRazaoSocial(), 
		        				_projetoModel.getNome(), 
		        				notificacaoRequest,
		        				isMaster) )
		        		.collect(Collectors.toList());
			
			
				 _projetoTipoDocumentacao.setDocumentos(_docs);
			    
			    _projetoModel.setProjetoTipoDocumentacao( _projetoTipoDocumentacao );
				
				projetoRepository.save(_projetoModel);
			        
			    logger.info("Notificação no documento");
    			
    			return new Response();
    		}
    		
    		throw new NotFoundException();
    			
    	}catch (Exception e) {
			logger.error( String.format("Erro ao deletar arquivo %s", e) );
			throw e;
    	}
    	
    }
    
    public Response aprova(String idProjeto, String idDocumento, String requestToken) throws Exception{
    	
    	try {
    		
    		JwtUsuarioDto jwtUsuarioDto = jwtTokenUtil.getJwtUsuarioFromToken(jwtTokenUtil.recuperaToken(requestToken));
    		
    		Optional<ProjetoModel> projetoModel = projetoRepository.findById(idProjeto);
    		
    		if( projetoModel.isPresent() ) {
    			
    			ProjetoModel _projetoModel = projetoModel.get();
    			
    			ProjetoTipoDocumentacaoModel _projetoTipoDocumentacao = _projetoModel.getProjetoTipoDocumentacao();
    			
    			List<ProjetoDocumentoModel> _docs = _projetoTipoDocumentacao.getDocumentos()
			        		.stream()
			        		.map(d -> aprovarArquivo(d, idDocumento, 
			        				_projetoModel.getCliente().getId(),
			        				_projetoModel.getCliente().getRazaoSocial() , 
			        				_projetoModel.getNome(),
			        				jwtUsuarioDto.getNome()) )
			        		.collect(Collectors.toList());
    			
    			
    			_projetoTipoDocumentacao.setDocumentos(_docs);
			    
			    _projetoModel.setProjetoTipoDocumentacao( _projetoTipoDocumentacao );
    			
    			projetoRepository.save(_projetoModel);
			        
			    logger.info("Documento deletado");
			    
			    return new Response();
    		}
    		
    		throw new NotFoundException();
    		
    	}catch (Exception e) {
			logger.error( String.format("Erro ao deletar arquivo %s", e) );
			throw e;
    	}
    }
    
    public Response delete(String idProjeto, String idDocumento, String requestToken) throws Exception{
    	
    	try {

    		JwtUsuarioDto jwtUsuarioDto = jwtTokenUtil.getJwtUsuarioFromToken(jwtTokenUtil.recuperaToken(requestToken));
    		
    		Optional<ProjetoModel> projetoModel = projetoRepository.findById(idProjeto);
    		
    		if( projetoModel.isPresent() ) {
    			
    			ProjetoModel _projetoModel = projetoModel.get();
    			
    			ProjetoTipoDocumentacaoModel _projetoTipoDocumentacao = _projetoModel.getProjetoTipoDocumentacao();
    			
    			 List<ProjetoDocumentoModel> _docs = _projetoTipoDocumentacao.getDocumentos()
			        		.stream()
			        		.map(d -> deletarArquivo(d, idDocumento, 
			        				_projetoModel.getCliente().getId(),
			        				_projetoModel.getCliente().getRazaoSocial() , 
			        				_projetoModel.getNome(),
			        				jwtUsuarioDto.getNome()))
			        		.collect(Collectors.toList());
    			
    			
    			 _projetoTipoDocumentacao.setDocumentos(_docs);
			    
			    _projetoModel.setProjetoTipoDocumentacao(_projetoTipoDocumentacao );
    			
    			projetoRepository.save(_projetoModel);
			        
			    logger.info("Documento deletado");
			    
			    return new Response();
    		}
    		
    		throw new NotFoundException();
    		
    	}catch (Exception e) {
			logger.error( String.format("Erro ao deletar arquivo %s", e) );
			throw e;
    	}
    }
    
    
    
    public Response upload(String idProjeto, String idDocumento, MultipartFile upload, String requestToken) throws Exception {

    	Optional<ProjetoModel> projetoModel = projetoRepository.findById(idProjeto);
    	
    	try {
    		
    		JwtUsuarioDto jwtUsuarioDto = jwtTokenUtil.getJwtUsuarioFromToken(jwtTokenUtil.recuperaToken(requestToken));
    		 
    		if( projetoModel.isPresent() ) {
    			
    			ProjetoModel _projetoModel = projetoModel.get();
    			ProjetoTipoDocumentacaoModel _projetoTipoDocumentacao = _projetoModel.getProjetoTipoDocumentacao();
    			
    			if( _projetoTipoDocumentacao != null && 
    					_projetoTipoDocumentacao.getDocumentos() != null &&
    					!_projetoTipoDocumentacao.getDocumentos().isEmpty()) {
    		
			
    				String idArquivoPrincipal = uploadArquivoPrincipal(upload);

			        List<ProjetoDocumentoModel> _docs = null;
			        
			        boolean isPdf = upload.getContentType().contains("pdf");
			        if(isPdf) {
			        	
			        	List<String> listaIdArquivos = uploadArquivoPdf(upload);
  	
			        	_docs = _projetoTipoDocumentacao.getDocumentos()
				        		.stream()
				        		.map(d -> incluirArquivo( d, idDocumento, 
				        				idArquivoPrincipal , 
				        				listaIdArquivos,
				        				jwtUsuarioDto.getNome()))
				        		.collect(Collectors.toList());
			        }else {
			        	
			        	List<String> listaIdArquivos = uploadArquivo(upload);
			        	
			        	_docs = _projetoTipoDocumentacao.getDocumentos()
					        	.stream()
					        	.map(d -> incluirArquivo( d, idDocumento, 
					        			idArquivoPrincipal , 
					        			listaIdArquivos,
					        			jwtUsuarioDto.getNome()))
					        	.collect(Collectors.toList());
			        }
			        
			        logger.info("Arquivo salvo");
	    		      
			        _projetoTipoDocumentacao.setDocumentos(_docs);
			   
			        
			        _projetoModel.setProjetoTipoDocumentacao(_projetoTipoDocumentacao);
			      
			        projetoRepository.save(_projetoModel);
			        
			        logger.info("Documento atualizado");
			        	        
			        EmailUploadArquivoDto emailUploadArquivoDto = new EmailUploadArquivoDto();
			   
			        emailUploadArquivoDto.setAssunto( 
			        		 String.format("Upload de documento Sistema GD Cliente %s", _projetoModel.getCliente().getRazaoSocial() ));
			         
			        String[] mensagens = new String[3];
			        
			        mensagens[0] = _projetoModel.getCliente().getRazaoSocial();
			        mensagens[1] = _projetoTipoDocumentacao.getNome();
			        mensagens[2] = _projetoModel.getNome();
			        
			        emailUploadArquivoDto.setMensagem(mensagens);
			        
			        if(_projetoModel.getCliente() != null) {
			        	emailUploadArquivoDto.setIdCliente(_projetoModel.getCliente().getId()	);
			        }
			        
			        enviarEmailService.uploadArquivo(emailUploadArquivoDto);
			        
			        return new Response(); 
    			}
    		}
    	
    		throw new NotFoundException();
	        
    	}catch (Exception e) {
    		
    		if( projetoModel.isPresent() ) {
    			
    			ProjetoModel _projetoModel = projetoModel.get();
    		
	    		EmailUploadArquivoDto emailUploadArquivoDto = new EmailUploadArquivoDto();
	    		
	    		emailUploadArquivoDto.setAssunto( 
		        		 String.format("Erro ao realizar upload de documento Sistema GD Cliente %s", _projetoModel.getCliente().getRazaoSocial() ));
		         
		        String[] mensagens = new String[3];
     
		        mensagens[0] = _projetoModel.getCliente().getRazaoSocial();
		        
		        ProjetoTipoDocumentacaoModel _projetoTipoDocumentacao = _projetoModel.getProjetoTipoDocumentacao();
		        if( _projetoTipoDocumentacao != null ) {
		        	mensagens[1] = _projetoTipoDocumentacao.getNome();
		        }else {
		        	mensagens[1] = "Não foi possível localizar o arquivo";
		        }
		        mensagens[2] = _projetoModel.getNome();
		        
		        emailUploadArquivoDto.setMensagem(mensagens);
		        
		        if(_projetoModel.getCliente() != null) {
		        	emailUploadArquivoDto.setIdCliente(_projetoModel.getCliente().getId()	);
		        }
		        
		        enviarEmailService.uploadArquivo(emailUploadArquivoDto);
    		}
    		
			logger.error( String.format("Erro ao realizar upload do arquivo %s", e) );
			throw e;
		}
    }
  
    private String uploadArquivoPrincipal(MultipartFile upload) throws IOException {
    	
    	DBObject metadata = new BasicDBObject();
        metadata.put("tamanhoArquivo", upload.getSize());
        
        return template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata).toString();

    }
    
    private List<String> uploadArquivo(MultipartFile upload) throws Exception {
    	
    	final BufferedImage read = ImageIO.read(upload.getInputStream());
    	
    	byte[] miniConteudo = ImagemUtil.compressImage(read, COMPACTACAO_IMAGEM);
    	
    	InputStream inputStream = new ByteArrayInputStream(miniConteudo);
    	
    	DBObject metadata = new BasicDBObject();
        metadata.put("tamanhoArquivo", miniConteudo.length );
        
        String id = template.store( inputStream , upload.getOriginalFilename(), upload.getContentType(), metadata).toString();

        return Arrays.asList( new String[] {id} );
    }
    
    private List<String> uploadArquivoPdf(MultipartFile upload) throws Exception {
    	
    	List<String> result = new ArrayList<>();
    	
    	List<byte[]> listaArquivos = convertPDFtoJpeg(upload.getInputStream());

    	listaArquivos.parallelStream()
    		.forEach( conteudo ->{
    			
    			DBObject metadata = new BasicDBObject();
    	        metadata.put("tamanhoArquivo",conteudo.length);

    			String id = template.store(new ByteArrayInputStream(conteudo), "licenciamento.jpeg", "image/jpeg", metadata).toString();
    			
    			result.add( id  );

    		});
    	
    	return result;
    }
    
    private ProjetoDocumentoModel incluirArquivo(ProjetoDocumentoModel documento, 
    		String idDocumento, String idArquivoPrincipal, List<String> listaIdArquivos, String nomeIncluiu){
    	
    	if(idDocumento.equals(documento.getId())) {
    		
    		LocalDateTime data = dataUtil.dataAtual();	
    		String historio = geraHistorico("Upload realizado", nomeIncluiu, data );
    		
    		if( documento.getHistorico() == null ) {
    			documento.setHistorico( Arrays.asList(historio) );
    		}else {
    			documento.getHistorico().add( historio );
    		}
    		
    		documento.setNomeUpload(nomeIncluiu);
    		documento.setDataUpload( data );
    		documento.setIdArquivoOriginal(idArquivoPrincipal); 
    		documento.setStatusArquito( SituacaoArquivoEnum.ANALISE.name() );
    		documento.setIdArquivos(listaIdArquivos);
    		
    	}
    	return documento;
    }
    
    private String geraHistorico(String msg, String nome, LocalDateTime data) {
    	return String.format("%s por, %s na data de %s", msg,nome, dataUtil.formataData(data));
    }
    
    public List<byte[]> convertPDFtoJpeg(InputStream pdf) throws Exception{
    	
    	PDDocument document = null;
    	
    	try {
  
    		List<byte[]> listaResultado = new ArrayList<>();
    	
	    	document = PDDocument.load(pdf);
	        PDFRenderer pdfRenderer = new PDFRenderer(document);
	        
	        int numPage = document.getNumberOfPages();
	        for (int page = 0; page < numPage; ++page) {
	        	
	        	BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
	        	byte[] miniConteudo = ImagemUtil.compressImage(bim, COMPACTACAO_IMAGEM);
	            
	            listaResultado.add( miniConteudo );
	        	 
	        }
	        
	        document.close();
	        
	        return listaResultado;
        
    	}catch (Exception e) {
			throw new Exception("Erro ao converter pdf em imagem " + e );
		}finally {
			if(document != null)document.close();
    	}
    }
    
    
    private ProjetoDocumentoModel incluirNotificacao(ProjetoDocumentoModel documento, 
    		String idDocumento, String idCliente, 
    		String nomeCliente, String nomeProjeto, 
    		NotificacaoRequest notificacaoRequest, boolean isMaster) {
    	
    	if(idDocumento.equals(documento.getId())) {
    		
    		NotificacaoModel _notificacao = new NotificacaoModel();
    		
    		_notificacao.setMensagem(notificacaoRequest.getMensagem());
    		_notificacao.setAutor(notificacaoRequest.getAutor());
    		_notificacao.setData( dataUtil.formataData(dataUtil.dataAtual()));
    		
    		if( documento.getNotificacoes() != null ) {
    			documento.getNotificacoes().add( _notificacao );
    		}else {
    			documento.setNotificacoes( Arrays.asList(_notificacao) );
    		}
    		
    		if(isMaster) {
    			documento.setNotificacaoRecebidaMaster(false);
    			documento.setNotificacaoRecebidaUsuario(true);
    		}else {
    			documento.setNotificacaoRecebidaMaster(true);
    			documento.setNotificacaoRecebidaUsuario(false);
    		}	
    		
	        EmailUploadArquivoDto emailUploadArquivoDto = new EmailUploadArquivoDto();
			   
	        emailUploadArquivoDto.setAssunto( 
	        		 String.format("Existe uma notificação do documento do Sistema GD Cliente %s", nomeCliente));
	         
	        String[] mensagens = new String[3];
	        
	        mensagens[0] = nomeProjeto;
	        mensagens[1] = nomeCliente;
	        mensagens[2] = documento.getNome();
	        
	        emailUploadArquivoDto.setMensagem(mensagens);
	        
	        if(idCliente != null) {
	        	emailUploadArquivoDto.setIdCliente(idCliente);
	        }
	        
	        enviarEmailService.deleteArquivo(emailUploadArquivoDto);
    	}
    	
    	return documento;
    	
    }
    
    private ProjetoDocumentoModel aprovarArquivo(ProjetoDocumentoModel documento, 
    		String idDocumento, String idCliente, String nomeCliente, String nomeProjeto, String nomeAprovou) {
    	
    	if(idDocumento.equals(documento.getId())) {
    		
    		LocalDateTime data = dataUtil.dataAtual();	
    		String historio = geraHistorico("Aprovação realizada", nomeAprovou, data );
    		
    		if( documento.getHistorico() == null ) {
    			documento.setHistorico( Arrays.asList(historio) );
    		}else {
    			documento.getHistorico().add( historio );
    		}
    		
    		documento.setNomeAprovacao(nomeAprovou);
    		documento.setStatusArquito( SituacaoArquivoEnum.APROVADO.name() );
    		documento.setDataAprovacao( data);
    		
	        EmailUploadArquivoDto emailUploadArquivoDto = new EmailUploadArquivoDto();
			   
	        emailUploadArquivoDto.setAssunto( 
	        		 String.format("Oba... Foi aprovado o documento do Sistema GD Cliente %s", nomeCliente));
	         
	        String[] mensagens = new String[3];
	        
	        mensagens[0] = nomeProjeto;
	        mensagens[1] = nomeCliente;
	        mensagens[2] = documento.getNome();
	        
	        emailUploadArquivoDto.setMensagem(mensagens);
	        
	        if(idCliente != null) {
	        	emailUploadArquivoDto.setIdCliente(idCliente);
	        }
	        
	        enviarEmailService.deleteArquivo(emailUploadArquivoDto);
    		
    	}
    	
    	return documento;
    	
    }
    
    private ProjetoDocumentoModel deletarArquivo(ProjetoDocumentoModel documento, 
    		String idDocumento, String idCliente, String nomeCliente, String nomeProjeto, String nomeRejeitou){
    	
    	if(idDocumento.equals(documento.getId())) {
    		
    		if(!Strings.isNullOrEmpty(documento.getIdArquivoOriginal()) ) {
    			Query queryIdArquivoOriginal = new Query(GridFsCriteria.where(documento.getIdArquivoOriginal()) );
    			template.delete(queryIdArquivoOriginal);	
    		}
    		
    		if( documento.getIdArquivos() != null && !documento.getIdArquivos().isEmpty() ) {
    			documento.getIdArquivos().stream().forEach( _idDoc -> {
    				Query query = new Query(GridFsCriteria.where(_idDoc) );
    				template.delete(query);
    			});
    		}
    		
    		LocalDateTime data = dataUtil.dataAtual();	
    		String historio = geraHistorico("Rejeição realizada", nomeRejeitou, data );
    		
    		if( documento.getHistorico() == null ) {
    			documento.setHistorico( Arrays.asList(historio) );
    		}else {
    			documento.getHistorico().add( historio );
    		}
    		
    		documento.setIdArquivoOriginal(null);
    		documento.setIdArquivos(null);
    		documento.setDataUpload( null );
    		documento.setDataAprovacao(null);
    		documento.setNomeRejeicao(nomeRejeitou);
    		documento.setDataRejeicao(data);
    		documento.setStatusArquito(SituacaoArquivoEnum.PENDENTE.name());
    		
	        EmailUploadArquivoDto emailUploadArquivoDto = new EmailUploadArquivoDto();
			   
	        emailUploadArquivoDto.setAssunto( 
	        		 String.format("Rejeitou um documento do Sistema GD Cliente %s", nomeCliente));
	         
	        String[] mensagens = new String[3];
	        
	        mensagens[0] = nomeProjeto;
	        mensagens[1] = nomeCliente;
	        mensagens[2] = documento.getNome();
	        
	        emailUploadArquivoDto.setMensagem(mensagens);
	        
	        if(idCliente != null) {
	        	emailUploadArquivoDto.setIdCliente(idCliente);
	        }
	        
	        enviarEmailService.deleteArquivo(emailUploadArquivoDto);
    		
    	}
    	return documento;
    	
    }

    public DocumentoDto download(String idArquivo) throws Exception {

    	try {
    	
	        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(idArquivo)) );
	
	        DocumentoDto documento = new DocumentoDto();
	        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
	        	documento.setNomeArquivo(gridFSFile.getFilename());
	        	documento.setTipoArquivo(gridFSFile.getMetadata().get("_contentType").toString());
	        	documento.setTamanho( gridFSFile.getMetadata().get("tamanhoArquivo").toString() );
	        	documento.setConteudo( IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );	
	        }

	        return documento;
        
    	}catch (Exception e) {
			logger.error( String.format("Erro ao realizar download do arquivo %s", e) );
			throw e;
		}
    }
    
    public static void main(String[] args) {
		
    	/*
		try {
			
			FileInputStream fis = new FileInputStream (new File("C:\\Users\\80845262\\Documents\\trabalho\\projetos\\pdf_editar\\arquivos\\","original.pdf"));
			
			List<InputStream> lis = convertPDFtoJpeg(fis);	
			
			lis.stream().forEach( i ->{
				
				try {	
					Files.write(Paths.get("C:\\Users\\80845262\\Documents\\trabalho\\projetos\\pdf_editar\\arquivos\\", 
							"teste.jpg") , i.readAllBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} );
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    		*/
    	
	}
}
