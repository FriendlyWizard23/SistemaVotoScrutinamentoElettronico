package progetto.models.results;

import java.util.List;

import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;

public class RisultatoCategoricoPartito extends RisultatoGeneric {
	private List<Partito> lp = null;
	private int totaleVotazioni;

	public RisultatoCategoricoPartito(SessioneVoto sv, List<Partito> lp, int totaleVotazioni) {
		super(sv);
		this.lp = lp;
		this.totaleVotazioni = totaleVotazioni;
	}

	@Override
	public String getVincitore() {
		int max = -1;
		Partito vinc = null;
		if (sv.getStrategiaVincitore().equals("maggioranza")) {

			// TODO: CONTROLLARE CHE NON SIANO TUTTI UGUALI I VALORI

			for (Partito p : lp) {
				if (p.getTotvoti() > max) {
					max = p.getTotvoti();
					vinc = p;
				}
			}
			return ("PARTITO VINCITORE: " + vinc.getNome() + " CON VOTI: " + vinc.getTotvoti() + " PER MAGGIORANZA");
		} else {
			for (Partito p : lp) {
				if (p.getTotvoti() >= totaleVotazioni / 2 + 1) {
					vinc = p;
				}
			}
			if (vinc == null) {
				return ("IMPOSSIBILE DEFINIRE UN VINCITORE, MAGGIORANZA ASSOLUTA NON RAGGIUNTA");
			}
			return ("PARTITO VINCITORE: " + vinc.getNome() + " CON VOTI: " + vinc.getTotvoti()
					+ " PER MAGGIORANZA ASSOLUTA");
		}
	}

	@Override
	public String getRisultatiFull() {
		StringBuilder strbld = new StringBuilder();
		strbld.append("[+] Totale Votanti: " + totaleVotazioni + "\n");
		for (Partito p : lp) {
			strbld.append("[+] " + p.getNome() + ": " + p.getTotvoti() + " voti\n");
		}
		return strbld.toString();
	}

}
