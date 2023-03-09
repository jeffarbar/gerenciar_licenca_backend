package br.com.telefonica.gd.response;

import java.util.List;

import br.com.telefonica.gd.model.ProjetoDocumentoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoTipoDocumentacaoResponse {

	 private String id;
	 
	 private String nome;
	 
	 private List<ProjetoDocumentoResponse> documentos;
	 
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

	public List<ProjetoDocumentoResponse> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<ProjetoDocumentoResponse> documentos) {
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

	public void setDataCadastro(String _dataCadastro) {
		this.dataCadastro = _dataCadastro;
	}
	 
	 
}
