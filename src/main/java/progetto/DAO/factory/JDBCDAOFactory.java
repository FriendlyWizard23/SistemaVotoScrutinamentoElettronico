package progetto.DAO.factory;

import progetto.DAO.Candidato.CandidatoDAOInterface;
import progetto.DAO.Candidato.CandidatoJDBCDAO;
import progetto.DAO.Sessione.SessioneDAOInterface;
import progetto.DAO.Sessione.SessioneJDBCDAO;
import progetto.DAO.Utente.UtenteDAOInterface;
import progetto.DAO.Utente.UtenteJDBCDAO;
import progetto.DAO.Votazione.VotazioneDAOInterface;
import progetto.DAO.Votazione.VotazioneJDBCDAO;

public class JDBCDAOFactory extends ABSDAOFactory {
	private static UtenteDAOInterface daoUtente = null;
	private static CandidatoDAOInterface daoCandidato = null;
	private static VotazioneDAOInterface daoVotazione = null;
	private static SessioneDAOInterface daoSessione = null;

	public UtenteDAOInterface getUtenteDAOInstance() {
		if (daoUtente == null)
			daoUtente = new UtenteJDBCDAO();
		return daoUtente;
	}

	public CandidatoDAOInterface getCandidatoDAOInstance() {
		if (daoCandidato == null)
			daoCandidato = new CandidatoJDBCDAO();
		return daoCandidato;
	}

	public VotazioneDAOInterface getVotazioneDAOInstance() {
		if (daoVotazione == null)
			daoVotazione = new VotazioneJDBCDAO();
		return daoVotazione;
	}

	@Override
	public SessioneDAOInterface getSessioneDAOInstance() {
		if (daoSessione == null)
			daoSessione = new SessioneJDBCDAO();
		return daoSessione;
	}

}