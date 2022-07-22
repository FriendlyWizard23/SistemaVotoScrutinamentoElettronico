package progetto.utils;

import progetto.models.Sessioni.Partito;

public class PartitoOrdinaleBEAN {
	private Partito partito;
	private int posizione;
	private int num_voti;

	public PartitoOrdinaleBEAN(Partito partito, int posizione, int num_voti) {
		super();
		this.partito = partito;
		this.posizione = posizione;
		this.num_voti = num_voti;
	}

	public Partito getPartito() {
		return partito;
	}

	public void setPartito(Partito partito) {
		this.partito = partito;
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
