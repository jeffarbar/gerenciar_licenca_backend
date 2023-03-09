package br.com.telefonica.gd.controller;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.telefonica.gd.request.ProjetoRequest;
import br.com.telefonica.gd.request.ReportRequest;
import br.com.telefonica.gd.response.Response;
import br.com.telefonica.gd.service.ReportService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/report")
public class ReportController extends Controller {

	@Autowired
	private ReportService reportService;
	
	@RequestMapping(path = "/", 
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE} , 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER')")
	public ResponseEntity<Response> list(@RequestBody ReportRequest reportRequest) {
		
		try {
			 return new ResponseEntity<>( reportService.list(reportRequest) , HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(path = "/export", 
			method = RequestMethod.POST, 
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyRole('MASTER')")
	public ResponseEntity<Resource> export(@RequestBody ReportRequest reportRequest) {
		
		try {
			
			InputStreamResource resource = reportService.export(reportRequest);
	    	return ResponseEntity.ok()
	    		 .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	    		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"projetos.xlsx\"")
	    	    .body(resource);
			
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
