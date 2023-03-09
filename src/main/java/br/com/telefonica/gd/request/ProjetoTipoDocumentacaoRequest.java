package br.com.telefonica.gd.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoTipoDocumentacaoRequest {

	 private String id;
	 
	 private String nome;
	 
	 private int quantidadeDocumentos;
	 
	 private List<ProjetoDocumentoRequest> documentos;

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

	public int getQuantidadeDocumentos() {
		return quantidadeDocumentos;
	}

	public void setQuantidadeDocumentos(int quantidadeDocumentos) {
		this.quantidadeDocumentos = quantidadeDocumentos;
	}

	public List<ProjetoDocumentoRequest> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<ProjetoDocumentoRequest> documentos) {
		this.documentos = documentos;
	}

	
}
