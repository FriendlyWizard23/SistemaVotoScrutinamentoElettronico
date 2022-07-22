package progetto.utils;

import java.sql.*;

public class DBManager {
	private static DBManager instance;
	private static String url = "jdbc:mysql://localhost:3306/votoelettronico";
	private static String username = "root";
	private static String password = "root1234";
	private Connection connection;

	private DBManager() {
	}

	/**
	 * utilizzato per evitare che si formino diverse istanze del database
	 * 
	 * @return istanza della classe nel caso sia istanziata, nuova istanza della
	 *         classe altrimenti
	 */
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	/***
	 * Apre la connessione con la base dati.
	 * 
	 * @return true se l'apertura e avvenuta con successo, false altrimenti.
	 */
	public boolean openConnection() {
		try {
			connection = DriverManager.getConnection(url, username, password);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/***
	 * Chiude la connessione con la base dati.
	 * 
	 * @return true se la chiusura e avvenuta con successo, false altrimenti.
	 */
	public boolean closeConnection() {
		try {
			connection.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * funzione usata per generare un preparedstatement
	 * 
	 * @param q Stringa del preparedstatement
	 * @return un preparedstatement funzionante
	 */
	public PreparedStatement prepareStatement(String q) {
		PreparedStatement result = null;
		try {
			result = connection.prepareStatement(q);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
}