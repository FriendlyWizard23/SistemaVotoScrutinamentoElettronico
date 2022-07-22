package progetto.DAO.Candidato;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import progetto.exceptions.DBSQLException;
import progetto.exceptions.UserNotFoundException;
import progetto.logger.Logger;
import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.ListaCandidati;
import progetto.models.Sessioni.Partito;
import progetto.models.Utenti.ABSUtente;
import progetto.models.Utenti.Elettore;
import progetto.models.Utenti.Gestore;
import progetto.utils.DBManager;

public class CandidatoJDBCDAO implements CandidatoDAOInterface {

	public Candidato getCandidato(ResultSet res) {
		Candidato result = null;
		String nome, cognome, partito;
		int IDCandidato;

		try {
			IDCandidato = res.getInt(1);
			nome = res.getString(2);
			cognome = res.getString(3);
			partito = res.getString(4);
			// vedi il tipo d'utente
			result = new Candidato(IDCandidato, nome, cognome, partito);
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT CandidatoJDBCDAO.java:getCandidato");
			throw new DBSQLException("UNKNOWN DATABASE ERROR IN THE LOGIN PROCEDURE");
		}
		return result;
	}

	@Override
	public Candidato get(String id) {
		String q = "select * from candidato where IDCandidato = ?;";
		int idcandidato = Integer.valueOf(id);
		Candidato result = null;
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setInt(1, idcandidato);
			ResultSet res = p.executeQuery();
			// nessun risultato
			if (!res.isBeforeFirst()) {
				Logger.getInstance().log("CANDIDATO NOT FOUND");
				throw new UserNotFoundException("ERRORE>> Candidato Non Trovato!");
			}
			res.next();
			result = getCandidato(res);
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT CandidatoJDBCDAO.java:get");
			throw new DBSQLException("ERRORE>> SQL EXCEPTION");
		}
		return result;
	}

	@Override
	public List<Candidato> getAll() {
		List<Candidato> results = new ArrayList<Candidato>();
		String q = "select * from Candidato order by partito;";
		PreparedStatement preparedStatement = DBManager.getInstance().prepareStatement(q);
		try {
			ResultSet res = preparedStatement.executeQuery();
			// prendi i risultati
			while (res.next())
				results.add(getCandidato(res));
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT CandidatoJDBCDAO.java:getAll");
			throw new DBSQLException("ERRORE>> SQL EXCEPTION");

		}
		return results;
	}

	@Override
	public void save(Candidato t) {
		String q = "insert into Candidato(IDCandidato, nome, cognome, partito) values (?, ?, ?, ?)";
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setInt(1, t.getIDCandidato());
			p.setString(2, t.getNome());
			p.setString(3, t.getCognome());
			p.setString(4, t.getPartito());
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT CandidatoJDBC.java:save");
			throw new DBSQLException("SQL ERROR");
		}
	}

	@Override
	public void update(Candidato t, Candidato u) {
		delete(t);
		save(u);

	}

	@Override
	public void delete(Candidato t) {
		String q = "delete from candidato where IDCandidato = ?";
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setInt(1, t.getIDCandidato());
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT CandidatoJDBCDAO:delete");
			throw new DBSQLException("SQL ERROR");
		}
	}

	/**
	 * RESTITUISCE LA LISTA DEI CANDIDATI DI UN DETERMINATO PARTITO
	 */
	@Override
	public List<Candidato> getCandidati(Partito partito) {
		String q = "select * from Candidato where partito = ?;";
		List<Candidato> result = new ArrayList<Candidato>();
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setString(1, partito.getNome());
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst())
				return new ArrayList<Candidato>();
			while (res.next())
				result.add(getCandidato(res));
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT CandidatoJDBC.java:getCandidati");
			throw new DBSQLException("SQL ERROR");
		}

		return result;
	}

	/**
	 * RESTITUISCE LA LISTA DEI CANDIDATI DI UN DETERMINATO PARTITO
	 * 
	 * @param STRINGA dell ' ID partito del quale ottenere la lista dei candidati
	 * @return lista dei candidati
	 */
	public List<Candidato> getCandidati(String partito) {
		String q = "select * from Candidato where partito = ?;";
		List<Candidato> result = new ArrayList<Candidato>();
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setString(1, partito);
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst())
				return new ArrayList<Candidato>();
			while (res.next())
				result.add(getCandidato(res));
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT CandidatoJDBC.java:getCandidati");
			throw new DBSQLException("SQL ERROR");
		}

		return result;
	}

	@Override
	/**
	 * Inserisce il candidato nella lista
	 */
	public void save(Candidato candidato, ListaCandidati listacandidati) {
		String q = "insert into composta_da(Candidato, Lista) values (?, ?)";
		int idCandidato = candidato.getIDCandidato();
		int idLista = listacandidati.getIdLista();
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setInt(1, idCandidato);
			p.setInt(2, idLista);
			p.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().log("SQL ERROR AT CandidatoJDBCDAO.java:save");
			throw new DBSQLException("SQL ERROR");
		}

	}

	/**
	 * @param candidato      ID DEL CANDIDATO DA INSERIRE NELLA LISTA
	 * @param listacandidati ID DELLA LISTA
	 */
	public void save(int candidato, int listacandidati) {
		String q = "insert into composta_da(Candidato, Lista) values (?, ?)";
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setInt(1, candidato);
			p.setInt(2, listacandidati);
			p.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().log("SQL ERROR AT CandidatoJDBCDAO.java:save");
			throw new DBSQLException("SQL ERROR");
		}

	}

}
