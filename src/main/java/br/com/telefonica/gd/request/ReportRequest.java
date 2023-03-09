package br.com.telefonica.gd.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {

	private String idCliente;
	
	private String[] dataPesquisa;

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String[] getDataPesquisa() {
		return dataPesquisa;
	}

	public void setDataPesquisa(String[] dataPesquisa) {
		this.dataPesquisa = dataPesquisa;
	}
	
}
