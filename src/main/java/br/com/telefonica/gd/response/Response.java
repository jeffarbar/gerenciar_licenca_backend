package br.com.telefonica.gd.response;

import br.com.telefonica.gd.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

	public Response() {
		this.code = ResponseEnum.OK.getCodigo();
		this.message = ResponseEnum.OK.getMsg();
	}
	
	public Response(ResponseEnum responseEnum) {
		this.code = responseEnum.getCodigo();
		this.message = responseEnum.getMsg();
	}
	
	private int code;
	
	private String message;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
