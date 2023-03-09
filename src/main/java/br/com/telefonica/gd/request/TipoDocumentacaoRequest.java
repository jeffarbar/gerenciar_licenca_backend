package br.com.telefonica.gd.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumentacaoRequest {

	 private String id;
	 
	 private String nome;
	 
	 private int quantidadeDocumentos;
	 
	 private List<DocumentoRequest> documentos;

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

	public List<DocumentoRequest> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoRequest> documentos) {
		this.documentos = documentos;
	}
	 
	 
}
