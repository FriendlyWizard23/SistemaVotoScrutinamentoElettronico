package progetto.DAO.Votazione;

import progetto.DAO.DAOInterface;
import progetto.models.Sessioni.SessioneVoto;
import progetto.models.Utenti.Elettore;
import progetto.models.Votazioni.VotoGeneric;

public interface VotazioneDAOInterface extends DAOInterface<VotoGeneric> {
	/**
	 * Conferma la votazione di un utente per una sessione
	 * 
	 * @param e IDelettore
	 * @param s IDsessione
	 */
	public void confermaVotazione(String elettore, String sessione);

	/**
	 * Restituisce il totale dei votanti per una determinata sessione
	 * 
	 * @param s sessione di voto
	 * @return totale votanti di una determinata sessione di voto
	 */
	public int getTotVotanti(SessioneVoto s);

	/**
	 * Restituisce il numero di aventi diritto al voto della base dati
	 * 
	 * @return numero di aventi diritto al voto
	 */
	public int getTotAventiDirittoAlVoto();

	/**
	 * Determina se un determinato utente ha partecipato ad una sessione
	 * 
	 * @param e elettore
	 * @param s sessione
	 * @return true se e ha partecipato ad s, false altrimenti
	 */
	public boolean partecipatoSessione(String e, String s);
}
