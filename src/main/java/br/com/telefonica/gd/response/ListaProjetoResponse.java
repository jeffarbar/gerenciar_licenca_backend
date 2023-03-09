package br.com.telefonica.gd.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaProjetoResponse extends Response {

	public ListaProjetoResponse() {}
	
	public ListaProjetoResponse(List<ProjetoResponse> lista) {
		super();
		this.lista = lista;
	}

	private List<ProjetoResponse> lista;

	public List<ProjetoResponse> getLista() {
		return lista;
	}

	public void setLista(List<ProjetoResponse> lista) {
		this.lista = lista;
	}
	
	
}
