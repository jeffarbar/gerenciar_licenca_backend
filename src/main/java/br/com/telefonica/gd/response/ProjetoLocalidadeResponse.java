package br.com.telefonica.gd.response;

public class ProjetoLocalidadeResponse {

private String mes;
	
	private int valor;
	
	public ProjetoLocalidadeResponse() {}

	public ProjetoLocalidadeResponse(String mes, int valor) {
		super();
		this.mes = mes;
		this.valor = valor;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	
}
