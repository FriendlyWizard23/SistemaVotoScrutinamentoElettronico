package progetto.models.Sessioni;

import java.util.Objects;

public class Candidato implements PartecipanteSessione {
	private String nome, cognome, partito;
	private int IDCandidato;
	private int totVoti;

	public Candidato(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
	}

	public Candidato(int iDCandidato, String nome, String cognome, String partito) {
		this.IDCandidato = iDCandidato;
		this.nome = nome;
		this.cognome = cognome;
		this.partito = partito;
	}

	public String getNome() {
		return this.nome;
	}

	public String getCognome() {
		return this.cognome;
	}

	@Override
	public boolean isPartito() {
		return false;
	}

	@Override
	public boolean isCandidato() {
		return true;
	}

	public String getPartito() {
		return partito;
	}

	public void setPartito(String partito) {
		this.partito = partito;
	}

	public int getIDCandidato() {
		return IDCandidato;
	}

	public void setIDCandidato(int iDCandidato) {
		IDCandidato = iDCandidato;
	}

	@Override

	public String toString() {
		return "[" + nome + " " + cognome + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Candidato))
			return false;
		Candidato other = (Candidato) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(nome, other.nome);
	}

	public int getTotVoti() {
		return totVoti;
	}

	public void setTotVoti(int totVoti) {
		this.totVoti = totVoti;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

}
