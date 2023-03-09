package br.com.telefonica.gd.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaProjetoTipoDocumentacaoResponse extends Response {

	
	
	public ListaProjetoTipoDocumentacaoResponse(List<ProjetoTipoDocumentacaoResponse> lista) {
		super();
		this.lista = lista;
	}

	private List<ProjetoTipoDocumentacaoResponse> lista;

	public List<ProjetoTipoDocumentacaoResponse> getLista() {
		return lista;
	}

	public void setLista(List<ProjetoTipoDocumentacaoResponse> lista) {
		this.lista = lista;
	}
	
	
}
