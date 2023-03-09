package br.com.telefonica.gd;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class TesteMongoDB {

	public static void main(String[] args) {
		
		ConnectionString connectionString = new ConnectionString("mongodb+srv://user_gd:Tivit01@cluster0.qqb7esq.mongodb.net/?retryWrites=true&w=majority");
		MongoClientSettings settings = MongoClientSettings.builder()
		        .applyConnectionString(connectionString)
		        .build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase database = mongoClient.getDatabase("_gb");
		
		
		database.createCollection("Projeto");
		
		//database.createCollection("Cliente")
		
		System.out.println( database.getName() );

	}

}
