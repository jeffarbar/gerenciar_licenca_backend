package br.com.telefonica.gd.enums;

public enum RoleEnum {

	ROLE_ADMIN("ADMIN"),
	ROLE_MASTER("MASTER"),
	ROLE_SHARING("SHARING"),
	ROLE_BASIC("BASIC");
	
	public static RoleEnum fromNome(String nome) {
		
		if( nome == null) return null;

		for( int i=0; i< values().length; i++ ) {
			if(nome.equals( values()[i].getNome() )) {
				return values()[i];
			}
		}
		
		return null;
	}
	
	public static RoleEnum fromEnum(String _enum) {
		
		if( _enum == null) return null;

		for( int i=0; i< values().length; i++ ) {
			if(_enum.equals( values()[i].name() )) {
				return values()[i];
			}
		}
		
		return null;
	}
	
	private RoleEnum(String nome) {
		this.nome = nome;
	}
	
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
