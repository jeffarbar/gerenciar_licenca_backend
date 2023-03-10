package br.com.telefonica.gd.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import br.com.telefonica.gd.dto.DocumentoDto;
import br.com.telefonica.gd.request.ClienteRequest;
import br.com.telefonica.gd.request.DocumentoRequest;
import br.com.telefonica.gd.request.NotificacaoRequest;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.service.DocumentoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/documento")
public class DocumentoController extends Controller {

	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private HttpServletRequest request;
	
	
	@RequestMapping( path = "/incluirDoc/{idProjeto}",
			method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('MASTER','SHARING')")
    public  ResponseEntity<Response> incluirDoc(
    		@PathVariable String idProjeto , @RequestBody List<DocumentoRequest> documentos ) throws IOException {
		
		try {
			return new ResponseEntity<>(documentoService.incluirDoc(idProjeto, documentos), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
    }

	
	@RequestMapping( path = "/upload",
			method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('BASIC','MASTER','SHARING')")
    public  ResponseEntity<Response> upload(
	    		@RequestParam String idProjeto, 
	    		@RequestParam String idDocumento, 
	    		@RequestParam("file") MultipartFile file) throws IOException {
		
		try {
			final String requestTokenHeader = request.getHeader("Authorization");
			return new ResponseEntity<>(documentoService.upload(idProjeto,idDocumento, file, requestTokenHeader), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
    }

	
	@RequestMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable("id") String id) throws IOException {
		
        try {
			
        	DocumentoDto documentoDto = documentoService.download(id);
        	
        	return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(documentoDto.getTipoArquivo() ))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentoDto.getNomeArquivo() + "\"")
                    .body(new ByteArrayResource( documentoDto.getConteudo() ));
            
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
	
		
	@RequestMapping(path = "/{idProjeto}/{idDocumento}", 
			method = RequestMethod.DELETE, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('BASIC','MASTER','SHARING')")
	public ResponseEntity<Response> delete(@PathVariable String idProjeto, @PathVariable String idDocumento) {

		try {
			final String requestTokenHeader = request.getHeader("Authorization");
			return new ResponseEntity<>(documentoService.delete(idProjeto, idDocumento, requestTokenHeader), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(path = "/{idProjeto}/{idDocumento}", 
			method = RequestMethod.PUT, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('BASIC','MASTER','SHARING')")
	public ResponseEntity<Response> aprova(@PathVariable String idProjeto, @PathVariable String idDocumento) {

		try {
			final String requestTokenHeader = request.getHeader("Authorization");
			return new ResponseEntity<>(documentoService.aprova(idProjeto, idDocumento, requestTokenHeader), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(path = "/notificacao/{idProjeto}/{idDocumento}", 
			method = RequestMethod.PUT, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('BASIC','MASTER','SHARING')")
	public ResponseEntity<Response> notificacao(
			@RequestBody NotificacaoRequest notificacaoRequest,
			@PathVariable String idProjeto, 
			@PathVariable String idDocumento) {

		try {
			final String requestTokenHeader = request.getHeader("Authorization");
			return new ResponseEntity<>(documentoService.notificacao(notificacaoRequest,idProjeto, idDocumento, requestTokenHeader), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
