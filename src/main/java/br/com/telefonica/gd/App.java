package br.com.telefonica.gd;
		
import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication			
@EnableMongoRepositories
public class App {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(App.class, args);
	}
			
	@Bean		
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
}