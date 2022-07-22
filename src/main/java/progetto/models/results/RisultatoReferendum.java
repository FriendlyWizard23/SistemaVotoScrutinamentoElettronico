package progetto.models.results;

import progetto.DAO.factory.ABSDAOFactory;
import progetto.DAO.factory.JDBCDAOFactory;
import progetto.models.Sessioni.SessioneVoto;

public class RisultatoReferendum extends RisultatoGeneric {
	private int favorevoli;
	private int nonfavorevoli;

	public RisultatoReferendum(SessioneVoto sv, int favorevoli, int nonfavorevoli) {
		super(sv);
		this.favorevoli = favorevoli;
		this.nonfavorevoli = nonfavorevoli;
	}

	@Override
	public String getVincitore() {
		if (sv.getStrategiaVincitore().equals("conquorum")) {
			int numeroVotanti = ABSDAOFactory.getFactory().getVotazioneDAOInstance().getTotVotanti(sv);
			int numeroAventiDiritto = ABSDAOFactory.getFactory().getVotazioneDAOInstance().getTotAventiDirittoAlVoto();
			if (numeroVotanti > numeroAventiDiritto) {
				if (favorevoli > nonfavorevoli)
					return "VINCITA: FAVOREVOLI";
				else if (favorevoli < nonfavorevoli)
					return "VINCITA: NON FAVOREVOLE";
				else
					return "VINCITA: PAREGGIO";
			} else {
				return "NON HANNO PARTECIPATO ABBASTANZA ELETTORI AVENTI DIRITTO DI VOTO: QUORUM NON RAGGIUNTO!";
			}
		} else {
			if (favorevoli > nonfavorevoli)
				return "VINCITA: FAVOREVOLI";
			else if (favorevoli < nonfavorevoli)
				return "VINCITA: NON FAVOREVOLE";
			else
				return "VINCITA: PAREGGIO";
		}
	}

	@Override
	public String getRisultatiFull() {
		StringBuilder strbld = new StringBuilder();
		strbld.append("[+] voti favorevoli: " + favorevoli + "\n");
		strbld.append("[+] voti sfavorevoli: " + nonfavorevoli);
		return strbld.toString();
	}
}
