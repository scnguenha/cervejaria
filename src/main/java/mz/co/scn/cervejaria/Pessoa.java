
package mz.co.scn.cervejaria;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author Sidónio Goenha on Jul 7, 2020
 *
 */
@XmlSeeAlso({PessoaFisica.class})
public abstract class Pessoa {
	protected String nome;
	protected List<Endereco> endereco;
	protected Long id;

	@XmlAttribute(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Endereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}
}
