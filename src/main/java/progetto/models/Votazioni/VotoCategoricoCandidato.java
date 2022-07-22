package progetto.models.Votazioni;

import progetto.models.Sessioni.SessioneVoto;

public class VotoCategoricoCandidato extends VotoGeneric {

	private int idcandidato;

	public VotoCategoricoCandidato(int IDCandidato, SessioneVoto sv) {
		super(sv);
		this.idcandidato = IDCandidato;
	}

	@Override
	public String getTipo() {
		return "categoricoCandidato";
	}

	public int getIDCandidatoScelto() {
		return idcandidato;
	}

}
