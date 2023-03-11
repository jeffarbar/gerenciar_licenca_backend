package br.com.telefonica.gd.request;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
	
	private String id;
	 
	private String nome;
	
	private boolean perfilAdministrador;
	
	private boolean sharing;
	
	private String telefone;

	private String email;
	
	private String senha;
	
	private String dataCadastro;
	
	private boolean isPrimeiroAcesso;
	
	private ClienteRequest cliente;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isPerfilAdministrador() {
		return perfilAdministrador;
	}

	public void setPerfilAdministrador(boolean perfilAdministrador) {
		this.perfilAdministrador = perfilAdministrador;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public boolean isPrimeiroAcesso() {
		return isPrimeiroAcesso;
	}

	public void setPrimeiroAcesso(boolean isPrimeiroAcesso) {
		this.isPrimeiroAcesso = isPrimeiroAcesso;
	}

	public ClienteRequest getCliente() {
		return cliente;
	}

	public void setCliente(ClienteRequest cliente) {
		this.cliente = cliente;
	}

	public boolean isSharing() {
		return sharing;
	}

	public void setSharing(boolean sharing) {
		this.sharing = sharing;
	}

}
