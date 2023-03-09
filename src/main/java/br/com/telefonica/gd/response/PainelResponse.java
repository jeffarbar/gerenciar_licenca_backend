package br.com.telefonica.gd.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PainelResponse extends ListaProjetoResponse{

	public PainelResponse() {}
	
	public PainelResponse(List<ProjetoResponse> lista) {
		super(lista);
	}

	private int quantidadeTotal;
	
	private int quantidadeDocumentoOk;

	public int getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(int quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}

	public int getQuantidadeDocumentoOk() {
		return quantidadeDocumentoOk;
	}

	public void setQuantidadeDocumentoOk(int quantidadeDocumentoOk) {
		this.quantidadeDocumentoOk = quantidadeDocumentoOk;
	}
	
	
}
