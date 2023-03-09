package br.com.telefonica.gd.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaUsuarioResponse extends Response {

	private List<UsuarioResponse> lista;

	public ListaUsuarioResponse() {
		super();
	}
	
	
	public ListaUsuarioResponse(List<UsuarioResponse> lista) {
		super();
		this.lista = lista;
	}

	public List<UsuarioResponse> getLista() {
		return lista;
	}

	public void setLista(List<UsuarioResponse> lista) {
		this.lista = lista;
	}
	
	
}
