package br.com.telefonica.gd.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    private String emailDestino;
    
    private String[] mensagem;
    
    private String assunto;
	
    public String getEmailDestino() {
		return emailDestino;
	}
	public void setEmailDestino(String emailDestino) {
		this.emailDestino = emailDestino;
	}
	public String[] getMensagem() {
		return mensagem;
	}
	public void setMensagem(String[] mensagem) {
		this.mensagem = mensagem;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
    
    
}
