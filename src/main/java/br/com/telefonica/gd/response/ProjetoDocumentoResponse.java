package br.com.telefonica.gd.response;

import java.time.LocalDateTime;
import java.util.List;

import br.com.telefonica.gd.model.NotificacaoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoDocumentoResponse {

	private String id;
	
	private String nome;
	
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
	
	private List<NotificacaoModel> notificacoes;
	
	private boolean isNotificacaoRecebidaUsuario;

	private boolean isNotificacaoRecebidaMaster;

	
	public boolean isNotificacaoRecebidaUsuario() {
		return isNotificacaoRecebidaUsuario;
	}

	public void setNotificacaoRecebidaUsuario(boolean isNotificacaoRecebidaUsuario) {
		this.isNotificacaoRecebidaUsuario = isNotificacaoRecebidaUsuario;
	}

	public boolean isNotificacaoRecebidaMaster() {
		return isNotificacaoRecebidaMaster;
	}

	public void setNotificacaoRecebidaMaster(boolean isNotificacaoRecebidaMaster) {
		this.isNotificacaoRecebidaMaster = isNotificacaoRecebidaMaster;
	}

	public List<NotificacaoModel> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<NotificacaoModel> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public List<String> getHistorico() {
		return historico;
	}

	public void setHistorico(List<String> historico) {
		this.historico = historico;
	}

	public String getNomeUpload() {
		return nomeUpload;
	}

	public void setNomeUpload(String nomeUpload) {
		this.nomeUpload = nomeUpload;
	}

	public String getNomeAprovacao() {
		return nomeAprovacao;
	}

	public void setNomeAprovacao(String nomeAprovacao) {
		this.nomeAprovacao = nomeAprovacao;
	}

	public String getNomeRejeicao() {
		return nomeRejeicao;
	}

	public void setNomeRejeicao(String nomeRejeicao) {
		this.nomeRejeicao = nomeRejeicao;
	}

	private List<String> idArquivos;

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

	public List<String> getIdArquivos() {
		return idArquivos;
	}

	public void setIdArquivos(List<String> idArquivos) {
		this.idArquivos = idArquivos;
	}

	public String getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(String dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public String getDataRejeicao() {
		return dataRejeicao;
	}

	public void setDataRejeicao(String dataRejeicao) {
		this.dataRejeicao = dataRejeicao;
	}

	public String getStatusArquito() {
		return statusArquito;
	}

	public void setStatusArquito(String statusArquito) {
		this.statusArquito = statusArquito;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public boolean isIncluirSharing() {
		return isIncluirSharing;
	}

	public void setIncluirSharing(boolean isIncluirSharing) {
		this.isIncluirSharing = isIncluirSharing;
	}
	
}
