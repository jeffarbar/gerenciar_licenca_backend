package br.com.telefonica.gd.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoTipoDocumentacaoModel {

	 @Id
	 @Indexed
	 private String id;
	 
	 private String nome;
	 
	 @Indexed
	 private List<ProjetoDocumentoModel> documentos;
	 
	 private String situacaoDocumentacao;
	 
	 private String dataCadastro;

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

	public List<ProjetoDocumentoModel> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<ProjetoDocumentoModel> documentos) {
		this.documentos = documentos;
	}

	public String getSituacaoDocumentacao() {
		return situacaoDocumentacao;
	}

	public void setSituacaoDocumentacao(String situacaoDocumentacao) {
		this.situacaoDocumentacao = situacaoDocumentacao;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	 
}
