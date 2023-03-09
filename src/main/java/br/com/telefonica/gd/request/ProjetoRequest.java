package br.com.telefonica.gd.request;


import java.util.List;

import br.com.telefonica.gd.response.ProjetoClienteResponse;
import br.com.telefonica.gd.response.ProjetoClienteSharingResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoRequest {

	private String id;
	
	private String nome;
	
	private String estado;
	 
	private String cidade;
	 
	private String cep;
	 
	private String endereco;
	
	private ProjetoClienteRequest cliente;
	
	private ProjetoClienteSharingRequest clienteSharing;
	
	private List<ProjetoTipoDocumentacaoRequest> tipoDocumentacoes;

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

	public ProjetoClienteRequest getCliente() {
		return cliente;
	}

	public void setCliente(ProjetoClienteRequest cliente) {
		this.cliente = cliente;
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

	public List<ProjetoTipoDocumentacaoRequest> getTipoDocumentacoes() {
		return tipoDocumentacoes;
	}

	public void setTipoDocumentacoes(List<ProjetoTipoDocumentacaoRequest> tipoDocumentacoes) {
		this.tipoDocumentacoes = tipoDocumentacoes;
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

	public ProjetoClienteSharingRequest getClienteSharing() {
		return clienteSharing;
	}

	public void setClienteSharing(ProjetoClienteSharingRequest clienteSharing) {
		this.clienteSharing = clienteSharing;
	}

	
	
}
