package br.com.telefonica.gd.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaTipoDocumentacaoResponse extends Response {

	private List<TipoDocumentacaoResponse> lista;

	
	
	public ListaTipoDocumentacaoResponse(List<TipoDocumentacaoResponse> lista) {
		super();
		this.lista = lista;
	}

	public List<TipoDocumentacaoResponse> getLista() {
		return lista;
	}

	public void setLista(List<TipoDocumentacaoResponse> lista) {
		this.lista = lista;
	}
	
	
}
