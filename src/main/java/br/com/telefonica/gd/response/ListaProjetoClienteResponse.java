package br.com.telefonica.gd.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaProjetoClienteResponse extends Response {


	List<ProjetoClienteResponse> lista;
	
	public ListaProjetoClienteResponse(List<ProjetoClienteResponse> lista) {
		super();
		this.lista = lista;
	}


	public List<ProjetoClienteResponse> getLista() {
		return lista;
	}

	public void setLista(List<ProjetoClienteResponse> lista) {
		this.lista = lista;
	}
	
	
}
