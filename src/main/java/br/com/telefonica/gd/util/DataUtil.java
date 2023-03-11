package br.com.telefonica.gd.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class DataUtil {
	
	private static final Logger logger = LogManager.getLogger(DataUtil.class);
	
	private  static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	public  LocalDateTime dataAtual() {
		return LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	}
	
	public  LocalDateTime formataData( String data ) {
		
		if( data == null ) {
			return null;
		}
		
		return LocalDateTime.parse(data, formatter);
	}
	
	public  String formataData( LocalDateTime data ) {
		if( data == null ) {
			return "";
		}
		
		return data.format(formatter);
	}
	
	public String recuperaMesAno(LocalDateTime data) {
		
		try {	
			if(data == null) {
				return null;
			}
			return data.getMonth().name().substring(0,3) +"/"+ data.getYear();
			
		}catch (Exception e) {
			logger.error("Erro no metodo recuperaMesAno " + e.getMessage());
			return null;
		}
		
	}

	/*
	public static void main(String[] args) {
		
		DataUtil d = new DataUtil();
		
		System.out.println( d.dataAtual() );
		
		System.out.println( d.formataData( d.dataAtual() ) );
		
		System.out.println( d.formataData("07/03/2023 00:00:00") );
	}
	*/
	
}
