package br.com.telefonica.gd.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumentacaoResponse extends Response{

	 private String id;
	 
	 private String nome;
	 
	 private int quantidadeDocumentos;
	 
	 private String dataCadastro;
	 
	 private List<DocumentoResponse> documentos;

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

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String _dataCadastro) {
		this.dataCadastro = _dataCadastro;
	}

	public List<DocumentoResponse> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoResponse> documentos) {
		this.documentos = documentos;
	}
	 
	 
}
