package br.com.telefonica.gd.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoResponse extends Response{

	 private String id;
	 
	 private String nome;
	
	 private String estado;
	 
	 private String cidade;
	 
	 private String cep;
	 
	 private String endereco;
	 
	 private ProjetoClienteResponse cliente;
	 
	 private ProjetoClienteSharingResponse clienteSharing;
	 
	 private String dataCadastro;
	 
	 private ProjetoTipoDocumentacaoResponse tipoDocumentacao;

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

	public ProjetoClienteResponse getCliente() {
		return cliente;
	}

	public void setCliente(ProjetoClienteResponse cliente) {
		this.cliente = cliente;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public ProjetoTipoDocumentacaoResponse getTipoDocumentacao() {
		return tipoDocumentacao;
	}

	public void setTipoDocumentacao(ProjetoTipoDocumentacaoResponse tipoDocumentacao) {
		this.tipoDocumentacao = tipoDocumentacao;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public ProjetoClienteSharingResponse getClienteSharing() {
		return clienteSharing;
	}

	public void setClienteSharing(ProjetoClienteSharingResponse clienteSharing) {
		this.clienteSharing = clienteSharing;
	}

}
