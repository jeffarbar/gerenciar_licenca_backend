package br.com.telefonica.gd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.telefonica.gd.request.TipoDocumentacaoRequest;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.service.EnviarEmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EnviarEmailService enviarEmailService;
	
	@RequestMapping(path = "/", 
			method = RequestMethod.POST, 
			produces = {MediaType.TEXT_PLAIN_VALUE} , 
			consumes = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> save(@RequestBody String email) {
		
		try {
			String resultado = enviarEmailService.teste(email);			
			return new ResponseEntity<>(resultado, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
