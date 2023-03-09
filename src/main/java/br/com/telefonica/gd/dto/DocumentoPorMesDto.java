package br.com.telefonica.gd.dto;

public class DocumentoPorMesDto {
	
	
	public DocumentoPorMesDto(int pendente, int analise, int finalizado) {
		super();
		this.pendente = pendente;
		this.analise = analise;
		this.finalizado = finalizado;
	}

	private int pendente;
	
	private int analise;

	private int finalizado;
	

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

