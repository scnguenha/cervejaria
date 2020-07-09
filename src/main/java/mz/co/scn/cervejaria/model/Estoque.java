
package mz.co.scn.cervejaria.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sidónio Goenha on Jul 7, 2020
 *
 */
public class Estoque {
	// private Collection<Cerveja> cervejas = new ArrayList<Cerveja>();
	private Map<String, Cerveja> cervejas = new HashMap<>();

	public Estoque() {
		Cerveja primeiraCerveja = new Cerveja("Stella Artois", "A cerveja belga mais francesa do mundo :)", "Artois",
				Cerveja.Tipo.LAGER);

		Cerveja segundaCerveja = new Cerveja("Erdinger Weissbier", "Cerveja de trigo alemã", "Erdinger Weissbräu",
				Cerveja.Tipo.WEIZEN);

		this.cervejas.put(primeiraCerveja.getNome(), primeiraCerveja);
		this.cervejas.put(segundaCerveja.getNome(), segundaCerveja);
	}

	public Collection<Cerveja> listarCervejas() {
		return new ArrayList<Cerveja>(this.cervejas.values());
	}

	public void adicionarCerveja(Cerveja cerveja) {
		this.cervejas.put(cerveja.getNome(), cerveja);
	}
	
	/*
	 * public Collection<Cerveja> listarCervejas() { return new ArrayList<Cerveja>(this.cervejas); }
	 * 
	 * public void adicionarCerveja(Cerveja cerveja) { this.cervejas.add(cerveja); }
	 */
}
