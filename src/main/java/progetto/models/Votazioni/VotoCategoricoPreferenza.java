package progetto.models.Votazioni;

import java.util.ArrayList;
import java.util.List;

import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;

public class VotoCategoricoPreferenza extends VotoGeneric {
	private Partito p;
	private List<Candidato> cs;

	public VotoCategoricoPreferenza(Partito p, List<Candidato> cs, SessioneVoto s) {
		super(s);
		this.p = p;
		this.cs = new ArrayList<Candidato>(cs);
	}

	public Partito getPartito() {
		return this.p;
	}

	public List<Candidato> getCandidati() {
		return new ArrayList<Candidato>(this.cs);
	}

	@Override
	public String getTipo() {
		return "categoricoPreferenza";
	}

}
