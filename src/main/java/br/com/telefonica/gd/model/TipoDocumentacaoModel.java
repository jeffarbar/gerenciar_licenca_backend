package br.com.telefonica.gd.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("TipoDocumentacao")
public class TipoDocumentacaoModel {

	 @Id
	 @Indexed
	 private String id;
	 
	 private String nome;
	 
	 private int quantidadeDocumentos;
	 
	 private LocalDateTime dataCadastro;
	 
	 private List<DocumentoModel> documentos;

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

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public List<DocumentoModel> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoModel> documentos) {
		this.documentos = documentos;
	}
	 
	 
	 
}
