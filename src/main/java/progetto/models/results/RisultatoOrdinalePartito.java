package progetto.models.results;

import java.util.List;

import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;
import progetto.utils.CandidatoOrdinaleBEAN;
import progetto.utils.PartitoOrdinaleBEAN;

public class RisultatoOrdinalePartito extends RisultatoGeneric {
	List<PartitoOrdinaleBEAN> partiti;
	private int totvotazioni;

	public RisultatoOrdinalePartito(SessioneVoto sv, List<PartitoOrdinaleBEAN> partiti, int totvotazioni) {
		super(sv);
		this.partiti = partiti;
		this.totvotazioni = totvotazioni;
	}

	@Override
	public String getVincitore() {
		if (sv.getStrategiaVincitore().equals("maggioranza")) {
			int maxvotiposuno = 0;
			Partito vincitore = null;
			for (PartitoOrdinaleBEAN pob : partiti) {
				if (pob.getPosizione() == 1) {
					if (pob.getNum_voti() > maxvotiposuno) {
						maxvotiposuno = pob.getNum_voti();
						vincitore = pob.getPartito();
					}
				}
			}
			if (vincitore == null) {
				return "NON E' STATO POSSIBILE DEFINIRE ALCUN VINCITORE";
			}
			return "PARTITO " + vincitore.getNome() + "  VINCE CON " + maxvotiposuno
					+ " VOTI IN PRIMA POSIZIONE --> PER MAGGIORANZA";
		} else {
			int maxvotiposuno = 0;
			Partito vincitore = null;
			for (PartitoOrdinaleBEAN pob : partiti) {
				if (pob.getPosizione() == 1) {
					if (pob.getNum_voti() > maxvotiposuno) {
						maxvotiposuno = pob.getNum_voti();
						vincitore = pob.getPartito();
					}
				}
			}
			if (maxvotiposuno >= (totvotazioni / 2) + 1) {
				return "PARTITO: " + vincitore.getNome() + "  VINCE CON " + maxvotiposuno
						+ " VOTI IN PRIMA POSIZIONE --> PER MAGGIORANZA ASSOLUTA";
			}
			return "NON E' STATO POSSIBILE DEFINIRE ALCUN VINCITORE";
		}
	}

	@Override
	public String getRisultatiFull() {
		StringBuilder strbld = new StringBuilder();
		strbld.append("TOT VOTANTI: " + totvotazioni + "\n");
		for (PartitoOrdinaleBEAN cob : partiti) {
			strbld.append("[+]" + cob.getPartito().getNome() + " : PER LA POSIZIONE " + cob.getPosizione()
					+ "HA OTTENUTO VOTI: " + cob.getNum_voti() + "\n");
		}
		return strbld.toString();
	}

}