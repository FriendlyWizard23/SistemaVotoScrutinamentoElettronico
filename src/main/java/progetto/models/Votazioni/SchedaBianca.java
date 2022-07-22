package progetto.models.Votazioni;

import progetto.models.Sessioni.SessioneVoto;

public class SchedaBianca extends VotoGeneric {
	public SchedaBianca(SessioneVoto sv) {
		super(sv);
	}

	@Override
	public String getTipo() {
		return "schedabianca";
	}

}
