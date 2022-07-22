package progetto.models.Votazioni;

import progetto.models.Sessioni.SessioneVoto;

public class VotoCategoricoPartito extends VotoGeneric {
	private String nomePartito;

	public VotoCategoricoPartito(String nomePartito, SessioneVoto ss) {
		super(ss);
		this.nomePartito = nomePartito;
	}

	@Override
	public String getTipo() {
		return "categoricoPartito";
	}

	public String getPartitoScelto() {
		return nomePartito;
	}
}
