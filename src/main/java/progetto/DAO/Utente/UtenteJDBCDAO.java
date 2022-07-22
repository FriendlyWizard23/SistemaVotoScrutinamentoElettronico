package progetto.DAO.Utente;

import java.sql.PreparedStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import progetto.models.Utenti.ABSUtente;
import progetto.models.Utenti.Elettore;
import progetto.models.Utenti.Gestore;
import progetto.utils.*;
import progetto.exceptions.DBSQLException;
import progetto.logger.Logger;
import progetto.exceptions.*;

public class UtenteJDBCDAO implements UtenteDAOInterface {

	/* @ requires usr.getCodiceFiscale() != null && usr.getPassword() != null; @ */
	@Override
	public ABSUtente login(UserBean usr) {
		String query = "select * from utente where CF=? and password=?";
		ABSUtente result = null;
		
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, usr.getCodiceFiscale());
			p.setString(2, usr.getPassword());

			ResultSet res = p.executeQuery();

			// nessun risultato
			if (!res.isBeforeFirst()) {
				Logger.getInstance().log("SQL ERROR AT UtenteJDBCDAO.java:38");
				throw new UserNotFoundException("USER NOT FOUND!");
			}

			// prendi i risultati
			res.next();
			result = getUtente(res);
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT UtenteJDBCDAO.java:38");
			throw new DBSQLException("UNKNOWN DATABASE ERROR IN THE LOGIN PROCEDURE");
		}
		
		return result;
	}

	private ABSUtente getUtente(ResultSet res) {
		ABSUtente result = null;
		String cf, privil, pwd;

		try {
			cf = res.getString(1);
			pwd = res.getString(2);
			privil = res.getString(3);
			// vedi il tipo d'utente
			if (privil.equals("0"))
				result = new Elettore(cf, pwd);
			else
				result = new Gestore(cf, pwd);
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT UtenteJDBCDAO.java:getUtente");
			throw new DBSQLException("UNKNOWN DATABASE ERROR IN THE LOGIN PROCEDURE");
		}

		return result;
	}

	/* @ requires id!=null @ */
	@Override
	/**
	 * Restituisce tutte le informazioni di un utente
	 */
	public ABSUtente get(String id) {
		String q = "select * from utente where cf = ?;";
		ABSUtente result = null;
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setString(1, id);
			ResultSet res = p.executeQuery();
			// nessun risultato
			if (!res.isBeforeFirst()) {
				Logger.getInstance().log("USER NOT FOUND");
				throw new UserNotFoundException("ERRORE>> Utente Non Trovato!");
			}
			res.next();
			result = getUtente(res);
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT UtenteJDBCDAO.java:79");
			throw new DBSQLException("ERRORE>> SQL EXCEPTION");
		}

		return result;
	}

	@Override
	/**
	 * Restituisce tutti quanti gli utenti
	 */
	public List<ABSUtente> getAll() {
		List<ABSUtente> results = new ArrayList<ABSUtente>();
		String q = "select * from Utente;";
		PreparedStatement preparedStatement = DBManager.getInstance().prepareStatement(q);
		try {
			ResultSet res = preparedStatement.executeQuery();
			// prendi i risultati
			while (res.next())
				results.add(getUtente(res));
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT UtenteJDBCDAO.java:95");
			throw new DBSQLException("ERRORE>> SQL EXCEPTION");

		}

		return results;
	}

	@Override
	public void save(ABSUtente t) {
		// dead_method
	}

	@Override
	public void update(ABSUtente t, ABSUtente u) {
		// dead_method

	}

	@Override
	public void delete(ABSUtente t) {
		// dead_method

	}

	@Override
	public boolean registraElettore(ABSUtente t, String pass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getId(ABSUtente u) {
		// TODO Auto-generated method stub
		return 0;
	}

}
