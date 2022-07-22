package progetto.models.Votazioni;

import progetto.models.Sessioni.SessioneVoto;

public abstract class VotoGeneric {
	protected SessioneVoto sv;

	public VotoGeneric(SessioneVoto sv) {
		this.sv = sv;
	}

	public abstract String getTipo();

	public SessioneVoto getSessione() {
		return this.sv;
	}

	public void setSessione(SessioneVoto sv) {
		this.sv = sv;
	}
}