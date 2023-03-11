package br.com.telefonica.gd.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;

import br.com.telefonica.gd.request.NotificacaoRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoDocumentoModel {

	@Indexed
	private String id; 
	
	@Indexed
	private String nome;
	
	private boolean isIncluirSharing;
	
	private LocalDateTime dataCadastro;
	
	private String idArquivoOriginal;
	
	private LocalDateTime dataUpload;
	
	private String nomeUpload;
	
	private LocalDateTime dataAprovacao;
	
	private String nomeAprovacao;
	
	private LocalDateTime dataRejeicao;
	
	private String nomeRejeicao;
	
	private String statusArquito;
	
	private List<String> historico;
	
	private List<String> idArquivos;
	
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

	public LocalDateTime getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(LocalDateTime dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public LocalDateTime getDataRejeicao() {
		return dataRejeicao;
	}

	public void setDataRejeicao(LocalDateTime dataRejeicao) {
		this.dataRejeicao = dataRejeicao;
	}

	public String getStatusArquito() {
		return statusArquito;
	}

	public void setStatusArquito(String statusArquito) {
		this.statusArquito = statusArquito;
	}

	public String getIdArquivoOriginal() {
		return idArquivoOriginal;
	}

	public void setIdArquivoOriginal(String idArquivoOriginal) {
		this.idArquivoOriginal = idArquivoOriginal;
	}

	public LocalDateTime getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(LocalDateTime dataUpload) {
		this.dataUpload = dataUpload;
	}

	public List<String> getIdArquivos() {
		return idArquivos;
	}

	public void setIdArquivos(List<String> idArquivos) {
		this.idArquivos = idArquivos;
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

	public List<String> getHistorico() {
		return historico;
	}

	public void setHistorico(List<String> historico) {
		this.historico = historico;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public boolean isIncluirSharing() {
		return isIncluirSharing;
	}

	public void setIncluirSharing(boolean isIncluirSharing) {
		this.isIncluirSharing = isIncluirSharing;
	}
	
}
