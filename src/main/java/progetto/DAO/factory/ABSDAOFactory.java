package progetto.DAO.factory;

import progetto.DAO.Candidato.CandidatoDAOInterface;
import progetto.DAO.Sessione.SessioneDAOInterface;
//abstract factory che si occupa di creare piu famiglie di DAO.
//Nel nostro caso per ogni famiglia abbiamo un solo tipo di DAO, JDBC.
//L'implementazione permette di aggiungere in futuro altre implementazioni
//		e cambiare ulteriormente il tipo di factory ritornato
import progetto.DAO.Utente.UtenteDAOInterface;
import progetto.DAO.Votazione.VotazioneDAOInterface;

public abstract class ABSDAOFactory {
	// singleton DAOFactory
	private static ABSDAOFactory factory = null;

	public static ABSDAOFactory getFactory() {
		if (factory == null)
			factory = new JDBCDAOFactory();
		return factory;
	}

	// factory methods per tutti i dao
	public abstract UtenteDAOInterface getUtenteDAOInstance();

	public abstract CandidatoDAOInterface getCandidatoDAOInstance();

	public abstract VotazioneDAOInterface getVotazioneDAOInstance();

	public abstract SessioneDAOInterface getSessioneDAOInstance();
}