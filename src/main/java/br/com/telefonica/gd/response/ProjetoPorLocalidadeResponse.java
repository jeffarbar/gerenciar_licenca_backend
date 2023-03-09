package br.com.telefonica.gd.response;

public class ProjetoPorLocalidadeResponse {

	private String localidade;
	
	private int quantidade;
	
	public ProjetoPorLocalidadeResponse() {}

	public ProjetoPorLocalidadeResponse(String localidade, int quantidade) {
		super();
		this.localidade = localidade;
		this.quantidade = quantidade;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
