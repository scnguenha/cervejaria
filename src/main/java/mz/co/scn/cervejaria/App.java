package mz.co.scn.cervejaria;

import java.util.Arrays;

import javax.xml.bind.JAXB;

import org.apache.commons.fileupload.ParameterParser;

/**
*
* @author Sidónio Goenha on Jul 3, 2020
*
*/
public class App {
	public static void main(String[] args) {
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setCpf("12345678909");
		pessoaFisica.setNome("Alexandre Saudate");
		
		Endereco endereco = new Endereco();
		endereco.setCep("12345-678");
		
		pessoaFisica.setEndereco(Arrays.asList(endereco));
		
		JAXB.marshal(pessoaFisica, System.out);
		
	}
}
