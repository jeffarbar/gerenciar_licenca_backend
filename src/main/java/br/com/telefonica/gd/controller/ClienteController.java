package br.com.telefonica.gd.controller;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.telefonica.gd.request.ClienteRequest;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.service.ClienteService;


@RestController
@RequestMapping("/cliente")
public class ClienteController extends Controller {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteService clienteService;

	@RequestMapping(path = "/", 
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE} , 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','ADMIN')")
	public ResponseEntity<Response> save(@RequestBody ClienteRequest clienteRequest) {
		
		try {
			 return new ResponseEntity<>(clienteService.save(clienteRequest), HttpStatus.OK);
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
			 return new ResponseEntity<>(clienteService.delete(id), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(path = "/{id}", 
			method = RequestMethod.PUT, 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','ADMIN')")
	public ResponseEntity<Response> update(@PathVariable String id, @RequestBody ClienteRequest clienteRequest) {
		
		try {
			 return new ResponseEntity<>(clienteService.update(id, clienteRequest), HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(path = "/", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('BASIC','MASTER','SHARING','ADMIN')")
	public ResponseEntity<Response> list() {
		
		try {
			final String requestTokenHeader = request.getHeader("Authorization");
			return new ResponseEntity<>( clienteService.list(requestTokenHeader), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(path = "/listSharing", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','SHARING','ADMIN')")
	public ResponseEntity<Response> listSharing() {
		
		try {
			final String requestTokenHeader = request.getHeader("Authorization");
			return new ResponseEntity<>( clienteService.listSharing(requestTokenHeader), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(path = "/projetoCliente", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('BASIC','MASTER','SHARING','ADMIN')")
	public ResponseEntity<Response> listProjetoCliente() {
		
		try {
			final String requestTokenHeader = request.getHeader("Authorization");
			return new ResponseEntity<>( clienteService.listProjetoCliente(requestTokenHeader), HttpStatus.OK);
		 
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@RequestMapping(path = "/{id}", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE} )
	@PreAuthorize("hasAnyRole('BASIC','MASTER','ADMIN')")
	public  ResponseEntity<Response> get(@PathVariable String id) {
		
		try {
			 return new ResponseEntity<>( clienteService.get(id), HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
