package br.com.telefonica.gd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.telefonica.gd.request.TipoDocumentacaoRequest;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.service.TipoDocumentacaoService;

@RestController
@RequestMapping("/tipoDocumentacao")
public class TipoDocumentacaoController extends Controller {

	@Autowired
	private TipoDocumentacaoService tipoDocumentacaoService;

	@RequestMapping(path = "/", 
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE} , 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER')")
	public ResponseEntity<Response> save(@RequestBody TipoDocumentacaoRequest tipoDocumentacaoRequest) {
		
		try {
			 return new ResponseEntity<>(tipoDocumentacaoService.save(tipoDocumentacaoRequest), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	

	@RequestMapping(path = "/{id}", 
			method = RequestMethod.DELETE, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER')")
	public ResponseEntity<Response> delete(@PathVariable String id) {
		
		try {
			 return new ResponseEntity<>(tipoDocumentacaoService.delete(id), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(path = "/{id}", 
			method = RequestMethod.PUT, 
			produces = {MediaType.APPLICATION_JSON_VALUE} , 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER')")
	public ResponseEntity<Response> update(@PathVariable String id, @RequestBody TipoDocumentacaoRequest tipoDocumentacaoRequest) {
		
		try {
			 return new ResponseEntity<>(tipoDocumentacaoService.update(id, tipoDocumentacaoRequest), HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(path = "/", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER')")
	public ResponseEntity<Response> list() {
		
		try {
			 return new ResponseEntity<>(tipoDocumentacaoService.list(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(path = "/projetoTipoDocumentacao", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER')")
	public ResponseEntity<Response> listProjetoTipoLicenciamento() {
		
		try {
			 return new ResponseEntity<>( tipoDocumentacaoService.listProjetoTipoDocumentacao(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(path = "/{id}", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER')")
	public ResponseEntity<Response> get(@PathVariable String id) {
		
		try {
			 return new ResponseEntity<>(tipoDocumentacaoService.get(id), HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
