
package mz.co.scn.cervejaria;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sidónio Goenha on Jul 7, 2020
 *
 */
@XmlRootElement
public class PessoaFisica extends Pessoa {
	private String cpf;
	
	private String dadoTransiente = "dadoTransiente";
	
	@XmlTransient
	public String getDadoTransiente() {
		return dadoTransiente;
	}

	public void setDadoTransiente(String dadoTransiente) {
		this.dadoTransiente = dadoTransiente;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
