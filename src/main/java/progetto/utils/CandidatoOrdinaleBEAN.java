package progetto.utils;

import progetto.models.Sessioni.Candidato;

public class CandidatoOrdinaleBEAN {

	private Candidato candidato;
	private int posizione;
	private int num_voti;

	public CandidatoOrdinaleBEAN(Candidato candidato, int posizione, int num_voti) {
		super();
		this.candidato = candidato;
		this.posizione = posizione;
		this.num_voti = num_voti;
	}

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

	public int getNum_voti() {
		return num_voti;
	}

	public void setNum_voti(int num_voti) {
		this.num_voti = num_voti;
	}

}
