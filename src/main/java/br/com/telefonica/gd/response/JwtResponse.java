package br.com.telefonica.gd.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse extends Response{

	private String jwttoken;
	
	private String username;
	
	private String email;
	
	private String cliente;
	
	private String idCliente;
	
	private String perfil;
	
	private boolean isPrimeiroAcesso;
	
	private boolean isMaster;
	
	
	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public boolean isPrimeiroAcesso() {
		return isPrimeiroAcesso;
	}

	public void setPrimeiroAcesso(boolean isPrimeiroAcesso) {
		this.isPrimeiroAcesso = isPrimeiroAcesso;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	
}
