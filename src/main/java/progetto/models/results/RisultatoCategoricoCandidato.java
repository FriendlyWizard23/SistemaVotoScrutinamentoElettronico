package progetto.models.results;

import java.util.List;

import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;

public class RisultatoCategoricoCandidato extends RisultatoGeneric {
	private List<Candidato> lc;
	private int totVotazioni;

	public RisultatoCategoricoCandidato(SessioneVoto sv, List<Candidato> lc, int totVotazioni) {
		super(sv);
		this.lc = lc;
		this.totVotazioni = totVotazioni;
	}

	@Override
	public String getVincitore() {
		int max = -1;
		Candidato vinc = null;
		if (sv.getStrategiaVincitore().equals("maggioranza")) {

			// TODO: CONTROLLARE CHE NON SIANO TUTTI UGUALI I VALORI

			for (Candidato c : lc) {
				if (c.getTotVoti() > max) {
					max = c.getTotVoti();
					vinc = c;
				}
			}
			return ("CANDIDATO VINCITORE: " + vinc.getNome() + " " + vinc.getCognome() + " CON VOTI: "
					+ vinc.getTotVoti() + " --> MAGGIORANZA");
		} else {
			for (Candidato c : lc) {
				if (c.getTotVoti() >= totVotazioni / 2 + 1) {
					vinc = c;
				}
			}
			if (vinc == null) {
				return ("IMPOSSIBILE DEFINIRE UN VINCITORE, MAGGIORANZA ASSOLUTA NON RAGGIUNTA");
			}
			return ("CANDIDATO VINCITORE: " + vinc.getNome() + " " + vinc.getCognome() + "CON VOTI: "
					+ vinc.getTotVoti() + " --> MAGGIORANZA ASSOLUTA");
		}
	}

	@Override
	public String getRisultatiFull() {
		StringBuilder strbld = new StringBuilder();
		strbld.append("[+] Totale Votanti: " + totVotazioni + "\n");
		for (Candidato c : lc) {
			strbld.append("[+] " + c.getNome() + " " + c.getCognome() + ": " + c.getTotVoti() + " voti\n");
		}
		return strbld.toString();

	}

}
