package progetto.models.Votazioni;

import progetto.models.Sessioni.SessioneVoto;

public class VotoReferendum extends VotoGeneric {
	private boolean favorevole;

	public VotoReferendum(boolean favorevole, SessioneVoto sv) {
		super(sv);
		this.favorevole = favorevole;
	}

	public boolean isFavorevole() {
		return favorevole;
	}

	@Override
	public String getTipo() {
		return "referendum";
	}
}
