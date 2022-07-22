package progetto.utils;

import progetto.models.Sessioni.Partito;

public class PARTPOSPair {
	private Partito par;
	private int posizione;

	public PARTPOSPair(Partito par, int posizione) {
		super();
		this.par = par;
		this.posizione = posizione;
	}

	public Partito getPar() {
		return par;
	}

	public void setPar(Partito par) {
		this.par = par;
	}

	public int getPosizione() {
		return posizione;
	}

	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}

	@Override
	public String toString() {
		return "PARTPOSPair [par=" + par + ", posizione=" + posizione + "]";
	}

}
