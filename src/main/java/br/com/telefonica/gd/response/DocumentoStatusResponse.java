package br.com.telefonica.gd.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DocumentoStatusResponse extends Response{

	private int quantidadeDocPendente;
	
	private int quantidadeDocAnalise;
	
	private int quantidadeDocFinalizado;
	
	private List<DocPorMesResponse> docMes = new ArrayList<>();
	
	private List<ProjetoPorLocalidadeResponse> projetoLocalidade = new ArrayList<>();
	
	private List<ProjetoPorClienteResponse> projetoCliente = new ArrayList<>();
	
	public int getQuantidadeDocPendente() {
		return quantidadeDocPendente;
	}

	public void setQuantidadeDocPendente(int quantidadeDocPendente) {
		this.quantidadeDocPendente = quantidadeDocPendente;
	}

	public int getQuantidadeDocAnalise() {
		return quantidadeDocAnalise;
	}

	public void setQuantidadeDocAnalise(int quantidadeDocAnalise) {
		this.quantidadeDocAnalise = quantidadeDocAnalise;
	}

	public int getQuantidadeDocFinalizado() {
		return quantidadeDocFinalizado;
	}

	public void setQuantidadeDocFinalizado(int quantidadeDocFinalizado) {
		this.quantidadeDocFinalizado = quantidadeDocFinalizado;
	}
	
	public List<DocPorMesResponse> getDocMes() {
		return docMes;
	}

	public void setDocMes(List<DocPorMesResponse> docMes) {
		this.docMes = docMes;
	}

	public List<ProjetoPorLocalidadeResponse> getProjetoLocalidade() {
		return projetoLocalidade;
	}

	public void setProjetoLocalidade(List<ProjetoPorLocalidadeResponse> projetoLocalidade) {
		this.projetoLocalidade = projetoLocalidade;
	}

	public List<ProjetoPorClienteResponse> getProjetoCliente() {
		return projetoCliente;
	}

	public void setProjetoCliente(List<ProjetoPorClienteResponse> projetoCliente) {
		this.projetoCliente = projetoCliente;
	}
	
	
}
