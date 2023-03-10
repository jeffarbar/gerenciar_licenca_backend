package br.com.telefonica.gd.request;


import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;

import br.com.telefonica.gd.model.NotificacaoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoDocumentoRequest{

	private String id; 
	
	private String nome;
	
	private boolean obrigatorio;
	
	private String dataCadastro;
	
	private String idArquivoOriginal;
	
	private String dataUpload;
	
	private String nomeUpload;
	
	private String dataAprovacao;
	
	private String nomeAprovacao;
	
	private String dataRejeicao;
	
	private String nomeRejeicao;
	
	private String statusArquito;
	
	private boolean isIncluirSharing;
	
	private List<String> historico;
	
	private List<String> idArquivos;
	
	private List<NotificacaoRequest> notificacoes;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getIdArquivoOriginal() {
		return idArquivoOriginal;
	}

	public void setIdArquivoOriginal(String idArquivoOriginal) {
		this.idArquivoOriginal = idArquivoOriginal;
	}

	public String getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(String dataUpload) {
		this.dataUpload = dataUpload;
	}

	public String getNomeUpload() {
		return nomeUpload;
	}

	public void setNomeUpload(String nomeUpload) {
		this.nomeUpload = nomeUpload;
	}

	public String getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(String dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public String getNomeAprovacao() {
		return nomeAprovacao;
	}

	public void setNomeAprovacao(String nomeAprovacao) {
		this.nomeAprovacao = nomeAprovacao;
	}

	public String getDataRejeicao() {
		return dataRejeicao;
	}

	public void setDataRejeicao(String dataRejeicao) {
		this.dataRejeicao = dataRejeicao;
	}

	public String getNomeRejeicao() {
		return nomeRejeicao;
	}

	public void setNomeRejeicao(String nomeRejeicao) {
		this.nomeRejeicao = nomeRejeicao;
	}

	public String getStatusArquito() {
		return statusArquito;
	}

	public void setStatusArquito(String statusArquito) {
		this.statusArquito = statusArquito;
	}

	public List<String> getHistorico() {
		return historico;
	}

	public void setHistorico(List<String> historico) {
		this.historico = historico;
	}

	public List<String> getIdArquivos() {
		return idArquivos;
	}

	public void setIdArquivos(List<String> idArquivos) {
		this.idArquivos = idArquivos;
	}

	public List<NotificacaoRequest> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<NotificacaoRequest> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public boolean isIncluirSharing() {
		return isIncluirSharing;
	}

	public void setIncluirSharing(boolean isIncluirSharing) {
		this.isIncluirSharing = isIncluirSharing;
	}
	
	
}
