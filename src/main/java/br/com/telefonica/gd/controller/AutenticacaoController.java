package br.com.telefonica.gd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.telefonica.gd.request.JwtRequest;
import br.com.telefonica.gd.service.AutenticacaoService;


@RestController
@CrossOrigin
public class AutenticacaoController extends Controller {


	@Autowired
	private AutenticacaoService autenticacaoService;


	@RequestMapping(value = "/autenticar", method = RequestMethod.POST)
	public ResponseEntity<?> criaToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		return ResponseEntity.ok(autenticacaoService.autenticar(authenticationRequest));
	}

}
