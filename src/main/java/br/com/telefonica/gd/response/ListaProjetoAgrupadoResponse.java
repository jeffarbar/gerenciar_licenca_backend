package br.com.telefonica.gd.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaProjetoAgrupadoResponse  extends Response {
	

	private List<ProjetoAgrupadoResponse> lista;

	public List<ProjetoAgrupadoResponse> getLista() {
		return lista;
	}

	public void setLista(List<ProjetoAgrupadoResponse> lista) {
		this.lista = lista;
	}
	
}
