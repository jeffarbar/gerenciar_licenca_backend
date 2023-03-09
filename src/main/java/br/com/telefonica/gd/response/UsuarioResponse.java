package br.com.telefonica.gd.response;

import org.springframework.data.mongodb.core.index.Indexed;

import br.com.telefonica.gd.model.ClienteModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse extends Response{

	private String id;
	 
	private String nome;
	
	private String perfil;
	
	private boolean perfilAdministrador;
	
	private boolean sharing;
	
	private String telefone;
	
	private String email;
	
	private String dataCadastro;
	
	private boolean isPrimeiroAcesso;
	
	//private String razaoSocialCliente;
	
	private ClienteResponse cliente;

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

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
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

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String _dataCadastro) {
		this.dataCadastro = _dataCadastro;
	}

	public boolean isPrimeiroAcesso() {
		return isPrimeiroAcesso;
	}

	public void setPrimeiroAcesso(boolean isPrimeiroAcesso) {
		this.isPrimeiroAcesso = isPrimeiroAcesso;
	}

	public ClienteResponse getCliente() {
		return cliente;
	}

	public void setCliente(ClienteResponse cliente) {
		this.cliente = cliente;
	}

	public boolean isSharing() {
		return sharing;
	}

	public void setSharing(boolean sharing) {
		this.sharing = sharing;
	}

}
