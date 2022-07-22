package progetto.DAO.Sessione;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import progetto.exceptions.DBSQLException;
import progetto.exceptions.SessionNotFoundException;
import progetto.logger.Logger;
import progetto.models.results.RisultatoCategoricoCandidato;
import progetto.models.results.RisultatoCategoricoPartito;
import progetto.models.results.RisultatoGeneric;
import progetto.models.results.RisultatoOrdinaleCandidato;
import progetto.models.results.RisultatoOrdinalePartito;
import progetto.models.results.RisultatoReferendum;
import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.ListaCandidati;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;
import progetto.models.Utenti.Elettore;
import progetto.utils.CandidatoOrdinaleBEAN;
import progetto.utils.DBManager;
import progetto.utils.PartitoOrdinaleBEAN;
import progetto.utils.SINOPair;

public class SessioneJDBCDAO implements SessioneDAOInterface {

	public boolean status(String IDSessione) {
		String query = "select stato from Sessione_voto WHERE IDSessione=?";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, IDSessione);
			ResultSet res = p.executeQuery();
			res.next();
			return res.getBoolean(1);
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT: SessioneJDBCDAO.java:status");
			throw new DBSQLException("SQL ERROR");
		}

	}

	public boolean isNew(String IDSessione) {
		String query = "select isnew from Sessione_voto WHERE IDSessione=?";
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, IDSessione);
			ResultSet res = p.executeQuery();
			res.next();
			boolean rep = res.getBoolean(1);
			if (rep == true) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT: SessioneJDBCDAO.java:isNew");
			throw new DBSQLException("SQL ERROR");
		}

	}

	public void setNotNew(String IDSessione) {
		String query = "UPDATE Sessione_voto SET isnew=0 where IDSessione=?";
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, IDSessione);
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:setNotNew");
			throw new DBSQLException("SQL ERROR");
		}
	}

	public List<Candidato> getListaCandidatiFromPartito(Partito par) {
		List<Candidato> can = new ArrayList<Candidato>();
		String query = "select c.* from candidato as c where Partito=?";
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, par.getNome());
			ResultSet res = p.executeQuery();
			while (res.next()) {
				can.add(parseCandidato(res));
			}
			return can;
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:getListCandidati");
			throw new DBSQLException("SQL ERROR");
		}
	}

	public ListaCandidati getListaCandidati(String IDSessione, Partito partito) {
		String query = "Select c.* from candidato as c inner join composta_da as cd on cd.Candidato=c.IDCandidato inner join lista as l on l.IDLista=cd.Lista inner join riguarda_lista as rl on rl.Lista=l.IDLista where rl.Sessione_voto=? and l.Partito=?";
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		ListaCandidati lc = new ListaCandidati(partito);
		try {
			p.setString(1, IDSessione);
			p.setString(2, partito.getNome());
			ResultSet res = p.executeQuery();
			while (res.next()) {
				lc.addCandidato(parseCandidato(res));
			}
			return lc;
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:getListCandidati");
			throw new DBSQLException("SQL ERROR");
		}
	}

	public Partito getPartito(String nome) {
		String q = "select p.* from partito where nome = ?;";
		SessioneVoto result = null;
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setString(1, nome);
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst()) {
				Logger.getInstance().log("PARTITO INESISTENTE: getPartito()");
				throw new DBSQLException("PARTITO INESTISTENTE");
			}
			res.next();
			Partito part = new Partito(res.getString(1), res.getInt(2));
			return part;
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:getPartito");
			throw new DBSQLException("SQL ERROR");
		}
	}

	public boolean exists(String IDSessione) {
		String q = "select * from sessione_voto where IDSessione = ?;";
		SessioneVoto result = null;
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setString(1, IDSessione);
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:exists");
			throw new DBSQLException("Problemi con la base dati, riprovare! Context: get");
		}
	}

	private SessioneVoto getSessioneFromResult(ResultSet res) {
		SessioneVoto result = null;
		try {
			String iDSessioner = res.getString(1);
			String tipologiaVoto = res.getString(6);
			String strategiaVincitore = res.getString(8);
			String domandaReferendum = res.getString(3);
			int numero_si = res.getInt(4);
			int numero_no = res.getInt(5);
			boolean status = res.getBoolean(2);
			boolean isnew = res.getBoolean(7);
			result = new SessioneVoto(iDSessioner, tipologiaVoto, strategiaVincitore, domandaReferendum, numero_si,
					numero_no, status, isnew);
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:27");
			throw new DBSQLException("ERRORE: BASE DATI SQL");
		}
		return result;
	}

	@Override
	/**
	 * Restituisce una sessione di voto dato il suo ID
	 */
	public SessioneVoto get(String id) {
		String q = "select * from sessione_voto where IDSessione = ?;";
		SessioneVoto result = null;
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setString(1, id);
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst()) {
				Logger.getInstance().log("SESSIONE NON TROVATA");
				throw new SessionNotFoundException("ERRORE: SESSIONE INESISTENTE");
			}
			// prendi i risultati
			while (res.next())
				result = getSessioneFromResult(res);
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:54");
			throw new DBSQLException("Problemi con la base dati, riprovare! Context: get");
		}
		return result;
	}

	/**
	 * Restituisce tutte le sessioni di voto ATTIVE
	 */
	@Override
	public List<SessioneVoto> getAll() {
		String q = "select * from sessione_voto";
		List<SessioneVoto> result = new ArrayList<SessioneVoto>();
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			ResultSet res = p.executeQuery();
			while (res.next()) {
				result.add(getSessioneFromResult(res));
			}
			return result;
		} catch (SQLException sqe) {
			Logger.getInstance().log("SQL ERROR AT: SessioneJDBCDAO.java:77");
			throw new DBSQLException("SQL ERROR!");
		}

	}

	@Override
	/**
	 * Salva una sessione di voto nella base dati
	 */
	public void save(SessioneVoto sv) {
		String q = "insert into sessione_voto(IDSessione, stato, domanda_referendum, numero_si, numero_no, tipologia,isnew,strategiaVincitore) values (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setString(1, sv.getIDSessione());
			p.setBoolean(2, sv.getStatus());
			p.setString(3, sv.getDomandaReferendum());
			p.setInt(4, sv.getNumeroSi());
			p.setInt(5, sv.getNumeroNo());
			p.setString(6, sv.getTipologiaVoto());
			p.setBoolean(7, sv.getIsnew());
			p.setString(8, sv.getStrategiaVincitore());
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO:103");
			throw new DBSQLException("SQL ERROR");
		}

		// notifyObservers();

	}

	@Override
	public void update(SessioneVoto t, SessioneVoto u) {
		delete(t);
		save(u);

	}

	@Override
	public void delete(SessioneVoto t) {
		String IDSessione = t.getIDSessione();
		String q = "delete from sessione_voto where IDSessione = ?";

		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setString(1, IDSessione);
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO:121");
			throw new DBSQLException("SQL ERROR");
		}

	}

	@Override
	public void start(SessioneVoto s) {
		String query = "update Sessione_voto set stato = '1' where IDSessione = ?;";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, s.getIDSessione());
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:120");
			throw new DBSQLException("SQL ERROR");
		}
		// notifyObservers();

	}

	@Override
	public void stop(SessioneVoto s) {
		String query = "update Sessione_voto set stato = 0 where IDSessione = ?;";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, s.getIDSessione());
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:134");
			throw new DBSQLException("SQL ERROR");
		}
		// notifyObservers();

	}

	/**
	 * RESTITUISCE UNA LISTA DI SESSIONI IN CUI L'UTENTE e NON HA VOTATO
	 */
	@Override
	public List<SessioneVoto> getAll(Elettore e) {
		String q = "select * from sessione_voto where IDSessione not in (select Sessione_voto from vota where Utente=?;";
		List<SessioneVoto> result = new ArrayList<SessioneVoto>();

		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		try {
			p.setString(1, e.getCF());
			ResultSet res = p.executeQuery();
			while (res.next()) {
				result.add(getSessioneFromResult(res));
			}
			return result;
		} catch (SQLException sqe) {
			Logger.getInstance().log("SQL ERROR AT: SessioneJDBCDAO.java:getAll");
			throw new DBSQLException("SQL ERROR!");
		}
	}

	@Override
	public List<Candidato> getListCandidati(SessioneVoto s) {
		List<Candidato> ls = new ArrayList<Candidato>();
		String query = "select c.*,rc.numero_voti from candidato as c inner join riguarda_candidato as rc on rc.Candidato=c.IDCandidato where rc.Sessione_voto=?";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, s.getIDSessione());
			ResultSet res = p.executeQuery();
			while (res.next()) {
				Candidato can = parseCandidato(res);
				// prendo il totale voti ottenuto dal singolo candidato
				can.setTotVoti(res.getInt(5));
				ls.add(can);
			}
			return ls;
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:getListCandidati");
			throw new DBSQLException("SQL ERROR");
		}
	}

	private Candidato parseCandidato(ResultSet rs) {
		try {
			return new Candidato(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java: parseCandidato");
			throw new DBSQLException("SQL ERROR");
		}
	}

	private Partito parsePartito(ResultSet rs) {
		try {
			return new Partito(rs.getString(1), rs.getInt(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java: parsePartito");
			throw new DBSQLException("SQL ERROR");
		}
	}

	/**
	 * Data una sessione di voto restituisce i partiti che ne fanno parte.
	 */
	@Override
	public List<Partito> getListPartiti(SessioneVoto s) {
		List<Partito> ls = new ArrayList<Partito>();
		String query = "select p.*,rp.numero_voti from partito as p inner join riguarda_partito as rp on rp.Partito=p.nome where rp.Sessione_voto=?";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, s.getIDSessione());
			ResultSet res = p.executeQuery();
			while (res.next()) {
				Partito par = parsePartito(res);
				// setta il numero di voti ottenuto da quel partito
				par.setTotvoti(res.getInt(3));
				ls.add(par);
			}
			return ls;
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:getListCandidati");
			throw new DBSQLException("SQL ERROR");
		}
	}

	@Override
	public RisultatoGeneric getRisultato(SessioneVoto s) {
		RisultatoGeneric rg = null;
		String idSessione = s.getIDSessione();
		String query;
		PreparedStatement p;

		if (s.getTipologiaVoto().equals("categoricoPartito")) {
			List<Partito> lp = getListPartiti(s);
			rg = new RisultatoCategoricoPartito(s, lp, getTotVotazioni(s));
		} else if (s.getTipologiaVoto().equals("categoricoPreferenza")) {
			List<Candidato> lc = getListCandidati(s);
			rg = new RisultatoCategoricoCandidato(s, lc, getTotVotazioni(s));
		} else if (s.getTipologiaVoto().equals("referendum")) {
			SINOPair pair = getSiNo(s);
			rg = new RisultatoReferendum(s, pair.getNumero_si(), pair.getNumero_no());
		} else if (s.getTipologiaVoto().equals("ordinalecandidato")) {
			List<CandidatoOrdinaleBEAN> lista = getcandidatoordinaleresults(s);
			rg = new RisultatoOrdinaleCandidato(s, lista, getTotVotazioni(s));
		} else if (s.getTipologiaVoto().equals("ordinalepartito")) {
			List<PartitoOrdinaleBEAN> lista = getpartitoordinaleresults(s);
			rg = new RisultatoOrdinalePartito(s, lista, getTotVotazioni(s));
		}
		return rg;
	}

	private List<CandidatoOrdinaleBEAN> getcandidatoordinaleresults(SessioneVoto sv) {
		List<CandidatoOrdinaleBEAN> lista = new ArrayList<CandidatoOrdinaleBEAN>();
		String query = "select c.*,oc.posizione,oc.numero_voti from ordinale_candidato as oc inner join candidato as c on oc.Candidato = c.IDCandidato where oc.Sessione_voto=?";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, sv.getIDSessione());
			ResultSet res = p.executeQuery();
			while (res.next()) {
				Candidato can = new Candidato(res.getInt(1), res.getString(2), res.getString(3), res.getString(4));
				CandidatoOrdinaleBEAN cob = new CandidatoOrdinaleBEAN(can, res.getInt(5), res.getInt(6));
				lista.add(cob);
			}
			return lista;
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT: SessioneJDBCDAO.java:getcandidatoordinaleresults");
			throw new DBSQLException("SQL ERROR");
		}
	}

	private List<PartitoOrdinaleBEAN> getpartitoordinaleresults(SessioneVoto sv) {
		List<PartitoOrdinaleBEAN> lista = new ArrayList<PartitoOrdinaleBEAN>();
		String query = "select p.*,op.posizione,op.numero_voti from ordinale_partito as op inner join partito as p on op.Partito = p.nome where op.Sessione_voto=?";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, sv.getIDSessione());
			ResultSet res = p.executeQuery();
			while (res.next()) {
				Partito can = new Partito(res.getString(1), res.getInt(2));
				PartitoOrdinaleBEAN cob = new PartitoOrdinaleBEAN(can, res.getInt(3), res.getInt(4));
				lista.add(cob);
			}
			return lista;
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT: SessioneJDBCDAO.java:getcandidatoordinaleresults");
			throw new DBSQLException("SQL ERROR");
		}
	}

	private SINOPair getSiNo(SessioneVoto s) {
		String query = "select numero_si,numero_no from Sessione_voto where IDSessione=?";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, s.getIDSessione());
			ResultSet res = p.executeQuery();
			res.next();
			return new SINOPair(res.getInt(1), res.getInt(2));
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:getSiNo");
			throw new DBSQLException("SQL ERROR");
		}
	}

	private int getTotVotazioni(SessioneVoto s) {
		String query = "select count(*) from vota where vota.Sessione_voto=?";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, s.getIDSessione());
			ResultSet res = p.executeQuery();
			res.next();
			return res.getInt(1);
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT: SessioneJDBCDAO.java:getTotVotazioni");
			throw new DBSQLException("SQL ERROR");
		}
	}

	public List<Partito> getListPartiti() {
		List<Partito> ls = new ArrayList<Partito>();
		String query = "select p.* from partito as p";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			ResultSet res = p.executeQuery();
			while (res.next()) {
				ls.add(parsePartito(res));
			}
			return ls;
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:getAllPartiti");
			throw new DBSQLException("SQL ERROR");
		}
	}

	public List<Candidato> getListCandidati() {
		List<Candidato> ls = new ArrayList<Candidato>();
		String query = "select c.* from candidato as c";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			ResultSet res = p.executeQuery();
			while (res.next()) {
				ls.add(parseCandidato(res));
			}
			return ls;
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:getAllCandidati");
			throw new DBSQLException("SQL ERROR");
		}
	}

	public void saveCategoricoPartito(List<Partito> ls, SessioneVoto sv) {
		this.save(sv);
		String query = "INSERT INTO riguarda_partito(Partito,Sessione_voto) VALUES (?,?)";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		for (Partito par : ls) {
			p = DBManager.getInstance().prepareStatement(query);
			try {
				p.setString(1, par.getNome());
				p.setString(2, sv.getIDSessione());
				p.execute();
			} catch (SQLException e) {
				Logger.getInstance().log("SQL ERROR: SessioneJDBCDAO.java:saveCategoricoPartito");
				throw new DBSQLException("SQL ERROR");
			}

		}
	}

	public void saveCategoricoCandidato(List<Candidato> lc, SessioneVoto sv) {
		this.save(sv);
		String query = "INSERT INTO riguarda_candidato(Candidato,Sessione_voto) VALUES (?,?)";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		for (Candidato can : lc) {
			p = DBManager.getInstance().prepareStatement(query);
			try {
				p.setInt(1, can.getIDCandidato());
				p.setString(2, sv.getIDSessione());
				p.execute();
			} catch (SQLException e) {
				Logger.getInstance().log("SQL ERROR: SessioneJDBCDAO.java:saveCategoricoCandidato");
				throw new DBSQLException("SQL ERROR");
			}

		}
	}

	public void disattivaSessione(String sessione) {
		String query = "UPDATE Sessione_voto SET stato=0 where IDSessione=?";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, sessione);
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:disattivaSessione");
			throw new DBSQLException("SQL ERROR");
		}
	}

	public void attivaSessione(String sessione) {
		String query = "UPDATE Sessione_voto SET stato=1 where IDSessione=?";

		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, sessione);
			p.execute();
		} catch (SQLException e) {
			Logger.getInstance().log("SQL ERROR AT SessioneJDBCDAO.java:attivaSessione");
			throw new DBSQLException("SQL ERROR");
		}
	}

}
