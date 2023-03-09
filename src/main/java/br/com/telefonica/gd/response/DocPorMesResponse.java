package br.com.telefonica.gd.response;

public class DocPorMesResponse {

	private String mes;
	
	private int pendente;

	private int analise;

	private int finalizado;

	public DocPorMesResponse() {}
	
	public DocPorMesResponse(String mes, int pendente, int analise, int finalizado) {
		super();
		this.mes = mes;
		this.pendente = pendente;
		this.analise = analise;
		this.finalizado = finalizado;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public int getPendente() {
		return pendente;
	}

	public void setPendente(int pendente) {
		this.pendente = pendente;
	}

	public int getAnalise() {
		return analise;
	}

	public void setAnalise(int analise) {
		this.analise = analise;
	}

	public int getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(int finalizado) {
		this.finalizado = finalizado;
	}


}
