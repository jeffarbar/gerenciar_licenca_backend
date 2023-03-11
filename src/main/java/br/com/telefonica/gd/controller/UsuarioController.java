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
import org.springframework.web.bind.annotation.RestController;

import br.com.telefonica.gd.request.RecuperaSenhaRequest;
import br.com.telefonica.gd.request.UsuarioRequest;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends Controller{

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(path = "/", 
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE} , 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','BASIC','SHARING','ADMIN')")
	public ResponseEntity<Response> save(@RequestBody UsuarioRequest usuarioRequest) {
		
		try {
			 return new ResponseEntity<>(usuarioService.save(usuarioRequest), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(path = "/recuperarSenha", 
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE} , 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> recuperarSenha(@RequestBody RecuperaSenhaRequest recuperaSenhaRequest) {
		
		try {
			 return new ResponseEntity<>(usuarioService.recuperarSenha(recuperaSenhaRequest), HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(path = "/atualizarSenha", 
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE} , 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> atualizarSenha(@RequestBody UsuarioRequest usuarioRequest) {
		
		try {
			 return new ResponseEntity<>(usuarioService.atualizarSenha(usuarioRequest), HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
			 return new ResponseEntity<>(usuarioService.delete(id), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(path = "/{id}", 
			method = RequestMethod.PUT, 
			produces = {MediaType.APPLICATION_JSON_VALUE} , 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','BASIC','SHARING','ADMIN')")
	public ResponseEntity<Response> update(@PathVariable String id, @RequestBody UsuarioRequest usuarioRequest) {
		
		try {
			 return new ResponseEntity<>(usuarioService.update(id, usuarioRequest), HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@RequestMapping(path = "/", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','BASIC','SHARING','ADMIN')")
	public ResponseEntity<Response> list() {
		
		try {
			final String requestTokenHeader = request.getHeader("Authorization");
			return new ResponseEntity<>( usuarioService.list(requestTokenHeader), HttpStatus.OK);
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
			return new ResponseEntity<>( usuarioService.listSharing(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@RequestMapping(path = "/{id}", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','BASIC','SHARING','ADMIN')")
	public ResponseEntity<Response> get(@PathVariable String id) {
		
		try {
			 return new ResponseEntity<>( usuarioService.get(id), HttpStatus.OK);
		}catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
