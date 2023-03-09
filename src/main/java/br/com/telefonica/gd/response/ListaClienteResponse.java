package br.com.telefonica.gd.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaClienteResponse extends Response {

	List<ClienteResponse> lista;

	public ListaClienteResponse(List<ClienteResponse> lista) {
		super();
		this.lista = lista;
	}

	public List<ClienteResponse> getLista() {
		return lista;
	}

	public void setLista(List<ClienteResponse> lista) {
		this.lista = lista;
	}
	
	

}
