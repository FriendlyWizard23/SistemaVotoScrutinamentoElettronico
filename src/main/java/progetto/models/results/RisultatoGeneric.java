package progetto.models.results;

import progetto.models.Sessioni.SessioneVoto;

public abstract class RisultatoGeneric {
	protected SessioneVoto sv;

	public RisultatoGeneric(SessioneVoto sv) {
		this.sv = sv;
	}

	public abstract String getVincitore();

	public abstract String getRisultatiFull();
}
