package br.com.telefonica.gd.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.service.PainelService;

@RestController
@RequestMapping("/painel")
public class PainelController extends Controller {

	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private PainelService painelService;
	
	@RequestMapping(path = "/{idCliente}", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','BASIC','SHARING','ADMIN')")
	public ResponseEntity<Response> list(@PathVariable("idCliente") String idCliente) {
		
		try {
			final String requestTokenHeader = request.getHeader("Authorization");
			return new ResponseEntity<>( painelService.list(idCliente, requestTokenHeader), HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(path = "/agrupado", 
			method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER','SHARING','ADMIN')")
	public ResponseEntity<Response> listAgrupado() {
		
		try {
			final String requestTokenHeader = request.getHeader("Authorization");
			return new ResponseEntity<>( painelService.listAgrupado(requestTokenHeader), HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
