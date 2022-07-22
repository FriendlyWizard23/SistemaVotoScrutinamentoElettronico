package progetto.models.results;

import java.util.List;

import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.SessioneVoto;
import progetto.utils.CandidatoOrdinaleBEAN;

public class RisultatoOrdinaleCandidato extends RisultatoGeneric {
	List<CandidatoOrdinaleBEAN> candidati;
	private int totvotazioni;

	public RisultatoOrdinaleCandidato(SessioneVoto sv, List<CandidatoOrdinaleBEAN> candidati, int totvotazioni) {
		super(sv);
		this.candidati = candidati;
		this.totvotazioni = totvotazioni;
	}

	@Override
	public String getVincitore() {
		if (sv.getStrategiaVincitore().equals("maggioranza")) {
			int maxvotiposuno = 0;
			Candidato vincitore = null;
			for (CandidatoOrdinaleBEAN cob : candidati) {
				if (cob.getPosizione() == 1) {
					if (cob.getNum_voti() > maxvotiposuno) {
						maxvotiposuno = cob.getNum_voti();
						vincitore = cob.getCandidato();
					}
				}
			}
			if (vincitore == null) {
				return "NON E' STATO POSSIBILE DEFINIRE ALCUN VINCITORE";
			}
			return "CANDIDATO: " + vincitore.getNome() + " " + vincitore.getCognome() + " VINCE CON " + maxvotiposuno
					+ " VOTI IN PRIMA POSIZIONE --> PER MAGGIORANZA";
		} else {
			int maxvotiposuno = 0;
			Candidato vincitore = null;
			for (CandidatoOrdinaleBEAN cob : candidati) {
				if (cob.getPosizione() == 1) {
					if (cob.getNum_voti() > maxvotiposuno) {
						maxvotiposuno = cob.getNum_voti();
						vincitore = cob.getCandidato();
					}
				}
			}
			if (maxvotiposuno >= (totvotazioni / 2) + 1) {
				return "CANDIDATO: " + vincitore.getNome() + " " + vincitore.getCognome() + " VINCE CON "
						+ maxvotiposuno + " VOTI IN PRIMA POSIZIONE --> PER MAGGIORANZA ASSOLUTA";
			}
			return "NON E' STATO POSSIBILE DEFINIRE ALCUN VINCITORE";
		}
	}

	@Override
	public String getRisultatiFull() {
		StringBuilder strbld = new StringBuilder();
		strbld.append("TOT VOTANTI: " + totvotazioni + "\n");
		for (CandidatoOrdinaleBEAN cob : candidati) {
			strbld.append("[+]" + cob.getCandidato().getNome() + " " + cob.getCandidato().getCognome()
					+ " : PER LA POSIZIONE " + cob.getPosizione() + "HA OTTENUTO VOTI: " + cob.getNum_voti() + "\n");
		}
		return strbld.toString();
	}

}
