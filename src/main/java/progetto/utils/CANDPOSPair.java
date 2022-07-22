package progetto.utils;

import progetto.models.Sessioni.Candidato;

public class CANDPOSPair {
	private Candidato candidato;
	private int posizione;

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public int getPosizione() {
		return posizione;
	}

	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}

	public CANDPOSPair(Candidato candidato, int posizione) {
		super();
		this.candidato = candidato;
		this.posizione = posizione;
	}

	@Override
	public String toString() {
		return "CANDPOSPair [candidato=" + candidato + ", posizione=" + posizione + "]";
	}

}
