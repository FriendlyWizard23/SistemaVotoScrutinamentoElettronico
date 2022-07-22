package progetto.DAO.Sessione;

import java.util.List;

import progetto.DAO.DAOInterface;
import progetto.models.results.RisultatoGeneric;
import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;
import progetto.models.Utenti.Elettore;
import progetto.models.results.RisultatoGeneric;

public interface SessioneDAOInterface extends DAOInterface<SessioneVoto> {
	public void start(SessioneVoto s);

	public void stop(SessioneVoto s);

	public List<SessioneVoto> getAll(Elettore e);

	public RisultatoGeneric getRisultato(SessioneVoto s);

	public List<Candidato> getListCandidati(SessioneVoto s);

	public List<Partito> getListPartiti(SessioneVoto s);
}
