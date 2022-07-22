package progetto.DAO.lista;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import progetto.DAO.Candidato.CandidatoJDBCDAO;
import progetto.exceptions.DBSQLException;
import progetto.exceptions.UserNotFoundException;
import progetto.logger.Logger;
import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.ListaCandidati;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;
import progetto.utils.DBManager;

public class ListaJDBCDAO implements ListaDAOInterface {

	@Override
	public ListaCandidati get(String id) {
		String q = "select p.nome,p.anno_fondazione,ca.*,l.IDLista from composta_da as cd inner join candidato as ca on cd.Candidato=ca.IDCandidato inner join Lista as l on cd.Lista=l.IDLista inner join Partito as p on p.nome=l.Partito where IDLista = ?;";
		int idlista = Integer.valueOf(id);
		ListaCandidati result = null;
		
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setInt(1, idlista);
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst()) {
				Logger.getInstance().log("CANDIDATO NOT FOUND");
				throw new UserNotFoundException("ERRORE>> Candidato Non Trovato!");
			}
			res.next();
			result = new ListaCandidati(new Partito(res.getString(1), res.getInt(2)));
			result.addCandidato(getCandidato(res));
			result.setIdLista(res.getInt(7));
			while (res.next()) {
				result.addCandidato(getCandidato(res));
			}
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT CandidatoJDBCDAO.java:get");
			throw new DBSQLException("ERRORE>> SQL EXCEPTION");
		}
		return result;
	}

	public Candidato getCandidato(ResultSet res) {
		try {
			return new Candidato(res.getInt(3), res.getString(4), res.getString(5), res.getString(6));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().log("SQL ERROR at ListaJDBCDAO.java:getCandidato");
			throw new DBSQLException("SQL ERROR0");
		}

	}

	@Override
	public List<ListaCandidati> getAll() {
		List<ListaCandidati> superlista = new ArrayList<ListaCandidati>();
		String query = "select IDLista from lista";
		
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst()) {
				Logger.getInstance().log("CANDIDATO NOT FOUND");
				throw new UserNotFoundException("ERRORE>> Candidato Non Trovato!");
			}
			while (res.next()) {
				superlista.add(get(String.valueOf(res.getInt(1))));
			}
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT ListaJDBCDAO.java:getAll");
			throw new DBSQLException("SQL ERROR");
		}
		return superlista;
	}

	@Override
	public List<ListaCandidati> getListe(SessioneVoto s) {
		List<ListaCandidati> superlista = new ArrayList<ListaCandidati>();
		String query = "select IDLista from riguarda_lista where Sessione_voto=?";
		
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, s.getIDSessione());
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst()) {
				Logger.getInstance().log("CANDIDATO NOT FOUND");
				throw new UserNotFoundException("ERRORE>> Candidato Non Trovato!");
			}
			while (res.next()) {
				superlista.add(get(String.valueOf(res.getInt(1))));
			}
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT ListaJDBCDAO.java:getListe");
			throw new DBSQLException("SQL ERROR");
		}
		return superlista;
	}

	/**
	 * Salva la lista di candidati nella base dati
	 */
	@Override
	public void save(ListaCandidati t) {
		Partito partito = t.getPartito();
		String nomepartito = partito.getNome();
		int IDLista = t.getIdLista();
		// salvo prima la lista
		saveTabellaLista(nomepartito, IDLista);
		// salvo tutti i candidati della lista
		saveListaCandidati(IDLista, t.getCandidati());

	}

	private void saveListaCandidati(int IDLista, List<Candidato> candidati) {
		for (Candidato c : candidati) {
			saveCandidatoInLista(IDLista, c.getIDCandidato());
		}
	}

	private void saveCandidatoInLista(int IDLista, int IDCandidato) {
		String q = "insert into composta_da(IDLista, IDCandidato) values (?, ?)";
		
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setInt(1, IDLista);
			p.setInt(2, IDCandidato);
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT ListaJDBCDAO.java:saveCandidatoInLista");
			throw new DBSQLException("SQL ERROR");
		}
	}

	private void saveTabellaLista(String partito, int IDLista) {
		String q = "insert into lista(IDLista, partito) values (?, ?)";
		
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setInt(1, IDLista);
			p.setString(2, partito);
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT ListaJDBCDAO.java:saveTabellaLista");
			throw new DBSQLException("SQL ERROR");
		}
	}

	@Override
	/**
	 * ATTENZIONE: EFFETTO CASCADE!
	 */
	public void update(ListaCandidati t, ListaCandidati u) {
		delete(t);
		save(u);

	}

	@Override
	/**
	 * Cancella lista candidati si sfrutta la CASCADE per cancellare anche le
	 * tabelle legate alla lista
	 */
	public void delete(ListaCandidati t) {
		String q = "delete from lista where IDLista = ?";
		
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setInt(1, t.getIdLista());
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT ListaJDBCDAO:delete");
			throw new DBSQLException("SQL ERROR");
		}

	}

	@Override
	public void saveListaInSessione(ListaCandidati l, SessioneVoto s) {
		// aggiunge record alla tabella riguarda_lista per collegare sessione voto e
		// lista
		Partito par = l.getPartito();
		String q = "insert into riguarda_lista(Lista, Sessione_voto) values (?, ?)";
		
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setInt(1, l.getIdLista());
			p.setString(2, s.getIDSessione());
			p.execute();
			q = "insert into riguarda_partito(Partito,Sessione_voto)values(?,?)";
			p = DBManager.getInstance().prepareStatement(q);
			p.setString(1, par.getNome());
			p.setString(2, s.getIDSessione());
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT ListaJDBCDAO.java:saveListaInSessione");
			throw new DBSQLException("SQL ERROR");
		}

	}

}
