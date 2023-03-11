package br.com.telefonica.gd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.telefonica.gd.request.ClienteRequest;
import br.com.telefonica.gd.request.ProjetoRequest;
import br.com.telefonica.gd.response.ClienteResponse;
import br.com.telefonica.gd.response.ListaClienteResponse;
import br.com.telefonica.gd.response.ListaProjetoResponse;
import br.com.telefonica.gd.response.ProjetoResponse;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.service.ClienteService;
import br.com.telefonica.gd.service.ProjetoService;

@RestController
@RequestMapping("/projeto")
public class ProjetoController extends Controller{

	@Autowired
	private ProjetoService projetoService;

	@RequestMapping(path = "/", 
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE} , 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','ADMIN')")
	public ResponseEntity<Response> save(@RequestBody ProjetoRequest projetoRequest) {
		
		try {
			 return new ResponseEntity<>(projetoService.save(projetoRequest), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(path = "/{id}", 
			method = RequestMethod.DELETE, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','ADMIN')")
	public ResponseEntity<Response> delete(@PathVariable String id) {
		
		try {
			 return new ResponseEntity<>(projetoService.delete(id), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(path = "/{id}", 
			method = RequestMethod.PUT, 
			produces = {MediaType.APPLICATION_JSON_VALUE} , 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','ADMIN')")
	public  ResponseEntity<Response> update(@PathVariable String id, @RequestBody ProjetoRequest projetoRequest) {
		
		try {
			 return new ResponseEntity<>( projetoService.update(id, projetoRequest), HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(path = "/", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','ADMIN')")
	public ResponseEntity<Response> list() {
		
		try {
			 return new ResponseEntity<>( projetoService.list(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(path = "/finalizado", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','ADMIN')")
	public ResponseEntity<Response> listFinalizado() {
		
		try {
			 return new ResponseEntity<>( projetoService.listFinalizado(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(path = "/{id}", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE} )
	@PreAuthorize("hasAnyRole('MASTER','BASIC','SHARING','ADMIN')")
	public ResponseEntity<Response> get(@PathVariable String id) {
		
		try {
			 return new ResponseEntity<>( projetoService.get(id), HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
