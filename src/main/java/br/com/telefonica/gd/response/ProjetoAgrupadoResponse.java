package br.com.telefonica.gd.response;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoAgrupadoResponse extends Response{ 

	private ProjetoClienteResponse projetoCliente;

	public ProjetoClienteResponse getProjetoCliente() {
		return projetoCliente;
	}

	public void setProjetoCliente(ProjetoClienteResponse projetoCliente) {
		this.projetoCliente = projetoCliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(projetoCliente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjetoAgrupadoResponse other = (ProjetoAgrupadoResponse) obj;
		return Objects.equals(projetoCliente, other.projetoCliente);
	}

	
}
