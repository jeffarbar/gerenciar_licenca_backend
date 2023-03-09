package br.com.telefonica.gd.enums;

public enum ResponseEnum {

	OK(0 , "OK"),
	ERROR(1,"Erro"),
	NOT_FOUND(2,"Não Encontrado");
	
	private ResponseEnum(int codigo, String msg) {
		this.codigo = codigo;
		this.msg = msg;
	}
	
	private int codigo;
	private String msg;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
