package br.com.telefonica.gd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.service.CargaInicialService;

@RestController
@RequestMapping("/cargaInicial")
public class CargaInicialController extends Controller {

	@Autowired
	private CargaInicialService cargaInicialService;
	
	@RequestMapping(path = "/carga", 
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> list() {
		
		try {
			 return new ResponseEntity<>( cargaInicialService.cargaInicial(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
