package progetto.models.Sessioni;

import java.util.Objects;

public class Partito implements PartecipanteSessione {
	private String nome;
	private int anno_fondazione;
	private int totvoti;

	public Partito(String nome, int anno_fondazione) {
		this.nome = nome;
		this.anno_fondazione = anno_fondazione;
	}

	@Override
	public boolean isPartito() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCandidato() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAnno_fondazione() {
		return anno_fondazione;
	}

	public void setAnno_fondazione(int anno_fondazione) {
		this.anno_fondazione = anno_fondazione;
	}

	@Override
	public String toString() {
		return "Partito [nome=" + nome + ", anno_fondazione=" + anno_fondazione + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(anno_fondazione, nome);
	}

	public int getTotvoti() {
		return totvoti;
	}

	public void setTotvoti(int totvoti) {
		this.totvoti = totvoti;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Partito))
			return false;
		Partito other = (Partito) obj;
		return anno_fondazione == other.anno_fondazione && Objects.equals(nome, other.nome);
	}

}
