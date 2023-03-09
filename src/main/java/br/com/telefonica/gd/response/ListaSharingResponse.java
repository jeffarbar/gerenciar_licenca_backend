package br.com.telefonica.gd.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaSharingResponse extends Response{

	private List<SharingResponse> lista;

	public ListaSharingResponse(List<SharingResponse> lista) {
		super();
		this.lista = lista;
	}

	public List<SharingResponse> getLista() {
		return lista;
	}

	public void setLista(List<SharingResponse> lista) {
		this.lista = lista;
	}
	
}
