package br.com.telefonica.gd.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoRequest {

	private String id; 
	
	private String nome;
	
	private boolean obrigatorio;
	
	private String _dataCadastro;

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

	public boolean isObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public String _getDataCadastro() {
		return _dataCadastro;
	}

	public void _setDataCadastro(String dataCadastro) {
		this._dataCadastro = dataCadastro;
	}

	
}
