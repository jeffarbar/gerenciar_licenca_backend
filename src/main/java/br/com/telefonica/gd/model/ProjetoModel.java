package br.com.telefonica.gd.model;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Projeto")
public class ProjetoModel {

	 @Id
	 @Indexed
	 private String id;
	 
	 private String nome;
	 
	 private String estado;
	 
	 private String cidade;
	 
	 private String cep;
	 
	 private String endereco;
	 
	 @Indexed
	 private ProjetoClienteModel cliente;
	 
	 @Indexed
	 private ProjetoClienteSharingModel clienteSharing;
	 
	 @Indexed
	 private ProjetoTipoDocumentacaoModel projetoTipoDocumentacao;
	 
	 private LocalDateTime dataCadastro;

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

	public ProjetoClienteModel getCliente() {
		return cliente;
	}

	public void setCliente(ProjetoClienteModel cliente) {
		this.cliente = cliente;
	}

	public ProjetoTipoDocumentacaoModel getProjetoTipoDocumentacao() {
		return projetoTipoDocumentacao;
	}

	public void setProjetoTipoDocumentacao(ProjetoTipoDocumentacaoModel projetoTipoDocumentacao) {
		this.projetoTipoDocumentacao = projetoTipoDocumentacao;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
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

	public ProjetoClienteSharingModel getClienteSharing() {
		return clienteSharing;
	}

	public void setClienteSharing(ProjetoClienteSharingModel clienteSharing) {
		this.clienteSharing = clienteSharing;
	}
	
}
