package progetto.DAO.lista;

import java.util.List;

import progetto.DAO.DAOInterface;
import progetto.models.Sessioni.ListaCandidati;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;

public interface ListaDAOInterface extends DAOInterface<ListaCandidati> {
	public List<ListaCandidati> getListe(SessioneVoto s);

	public void saveListaInSessione(ListaCandidati l, SessioneVoto s);
}
