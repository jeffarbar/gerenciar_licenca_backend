package br.com.telefonica.gd.response;

public class ProjetoPorClienteResponse {

	private String cliente;
	
	private int quantidade;
	
	public ProjetoPorClienteResponse() {}

	public ProjetoPorClienteResponse(String cliente, int quantidade) {
		super();
		this.cliente = cliente;
		this.quantidade = quantidade;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
