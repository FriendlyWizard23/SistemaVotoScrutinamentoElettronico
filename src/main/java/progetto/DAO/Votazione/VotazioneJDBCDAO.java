package progetto.DAO.Votazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import progetto.DAO.Candidato.CandidatoJDBCDAO;
import progetto.DAO.Sessione.SessioneJDBCDAO;
import progetto.exceptions.DBSQLException;
import progetto.logger.Logger;
import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;
import progetto.models.Utenti.Elettore;
import progetto.models.Votazioni.VotoCategoricoCandidato;
import progetto.models.Votazioni.VotoCategoricoPartito;
import progetto.models.Votazioni.VotoCategoricoPreferenza;
import progetto.models.Votazioni.VotoGeneric;
import progetto.models.Votazioni.VotoOrdinaleCandidato;
import progetto.models.Votazioni.VotoOrdinalePartito;
import progetto.models.Votazioni.VotoReferendum;
import progetto.utils.CANDPOSPair;
import progetto.utils.DBManager;
import progetto.utils.PARTPOSPair;

public class VotazioneJDBCDAO implements VotazioneDAOInterface {

	@Override
	/* @ensures t!=null@ */
	public void save(VotoGeneric t) {
		String tipo = t.getTipo();
		String idSessione = t.getSessione().getIDSessione();
		SessioneJDBCDAO ss = new SessioneJDBCDAO();
		if (tipo.equals("referendum")) {
			VotoReferendum vr = (VotoReferendum) t;
			if (vr.isFavorevole()) {
				String query = "update sessione_voto set numero_si=? where IDSessione=?";
				PreparedStatement p = DBManager.getInstance().prepareStatement(query);
				
				try {
					p.setInt(1, ss.get(idSessione).getNumeroSi() + 1);
					p.setString(2, idSessione);
					p.execute();
				} catch (SQLException e) {
					Logger.getInstance().log("SQL ERROR AT VotazioneJDBCDAO.java:save");
					throw new DBSQLException("SQL ERROR");
				}
			} else if (!vr.isFavorevole()) {
				String query = "update sessione_voto set numero_no=? where IDSessione=?";
				PreparedStatement p = DBManager.getInstance().prepareStatement(query);
				
				try {
					p.setInt(1, ss.get(idSessione).getNumeroNo() + 1);
					p.setString(2, idSessione);
					p.execute();
				} catch (SQLException e) {
					Logger.getInstance().log("SQL ERROR AT VotazioneJDBCDAO.java:save");
					throw new DBSQLException("SQL ERROR");
				}
			}

		} else if (tipo.equals("schedabianca")) {
			return;
		} else if (tipo.equals("categoricoPartito")) {
			VotoCategoricoPartito vcp = (VotoCategoricoPartito) t;
			String query = "select numero_voti from riguarda_partito where Sessione_voto=? and Partito=?";
			PreparedStatement p = DBManager.getInstance().prepareStatement(query);
			
			try {
				p.setString(1, idSessione);
				p.setString(2, vcp.getPartitoScelto());
				ResultSet res = p.executeQuery();
				res.next();
				int numVoti = res.getInt(1);
				numVoti++;
				query = "update riguarda_partito set numero_voti=? where Sessione_voto=? and Partito=?";
				p = DBManager.getInstance().prepareStatement(query);
				p.setInt(1, numVoti);
				p.setString(2, idSessione);
				p.setString(3, vcp.getPartitoScelto());
				p.execute();
			} catch (SQLException e) {
				Logger.getInstance().log("SQL ERROR AT: VotazioneJDBCDAO.java:save");
				throw new DBSQLException("SQL ERROR");
			}
		} else if (tipo.equals("categoricoCandidato")) {
			System.out.println("ENTRO");
			VotoCategoricoCandidato vcc = (VotoCategoricoCandidato) t;
			String query = "select numero_voti from riguarda_candidato where Sessione_voto=? and Candidato=?";
			PreparedStatement p = DBManager.getInstance().prepareStatement(query);
			
			try {
				p.setString(1, idSessione);
				p.setInt(2, vcc.getIDCandidatoScelto());
				ResultSet res = p.executeQuery();
				res.next();
				int numVoti = res.getInt(1);
				numVoti++;
				query = "update riguarda_candidato set numero_voti=? where Sessione_voto=? and Candidato=?";
				p = DBManager.getInstance().prepareStatement(query);
				p.setInt(1, numVoti);
				p.setString(2, idSessione);
				p.setInt(3, vcc.getIDCandidatoScelto());
				p.execute();
			} catch (SQLException e) {
				Logger.getInstance().log("SQL ERROR AT: VotazioneJDBCDAO.java:save");
				throw new DBSQLException("SQL ERROR");
			}
		} else if (tipo.equals("categoricoPreferenza")) {
			VotoCategoricoPreferenza vcp = (VotoCategoricoPreferenza) t;
			aggiornaPartito(vcp.getPartito(), idSessione);
			for (Candidato c : vcp.getCandidati()) {
				aggiornaCandidato(c, idSessione);
			}

		} else if (tipo.equals("ordinalepartito")) {
			VotoOrdinalePartito vop = (VotoOrdinalePartito) t;
			List<PARTPOSPair> partitiordinati = vop.getPartitiPosizioni();
			for (PARTPOSPair pair : partitiordinati) {
				Partito partito = pair.getPar();
				int posizione = pair.getPosizione();

				String query = "select numero_voti from ordinale_partito where Sessione_voto=? and Partito=? and posizione=?";
				PreparedStatement p = DBManager.getInstance().prepareStatement(query);
				
				try {
					p.setString(1, vop.getSessione().getIDSessione());
					p.setString(2, partito.getNome());
					p.setInt(3, posizione);
					ResultSet res = p.executeQuery();
					if (!res.isBeforeFirst()) {
						query = "insert into ordinale_partito(sessione_voto,partito,posizione,numero_voti)values(?,?,?,?) ";
						p = DBManager.getInstance().prepareStatement(query);
						p.setString(1, idSessione);
						p.setString(2, partito.getNome());
						p.setInt(3, posizione);
						p.setInt(4, 1);
						p.execute();
					} else {
						res.next();
						int numcurr = res.getInt(1);
						numcurr++;
						query = "update ordinale_partito set numero_voti=? where sessione_voto=? and partito=? and posizione=?";
						p = DBManager.getInstance().prepareStatement(query);
						p.setInt(1, numcurr);
						p.setString(2, idSessione);
						p.setString(3, partito.getNome());
						p.setInt(4, posizione);
						p.execute();

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Logger.getInstance().log("SQL ERROR AT: VotazioneJDBCDAO.java:save");
					throw new DBSQLException("SQL ERROR");
				}
			}
		} else if (tipo.equals("ordinalecandidato")) {
			VotoOrdinaleCandidato voc = (VotoOrdinaleCandidato) t;
			List<CANDPOSPair> candidatiordinati = voc.getCandidatiPosizioni();
			for (CANDPOSPair pair : candidatiordinati) {
				Candidato can = pair.getCandidato();
				int posizione = pair.getPosizione();
				String query = "select numero_voti from ordinale_candidato where Sessione_voto=? and Candidato=? and posizione=?";
				PreparedStatement p = DBManager.getInstance().prepareStatement(query);
				
				try {
					p.setString(1, voc.getSessione().getIDSessione());
					p.setInt(2, can.getIDCandidato());
					p.setInt(3, posizione);
					ResultSet res = p.executeQuery();
					if (!res.isBeforeFirst()) {
						query = "insert into ordinale_candidato(sessione_voto,candidato,posizione,numero_voti)values(?,?,?,?) ";
						p = DBManager.getInstance().prepareStatement(query);
						p.setString(1, idSessione);
						p.setInt(2, can.getIDCandidato());
						p.setInt(3, posizione);
						p.setInt(4, 1);
						p.execute();
					} else {
						res.next();
						int numcurr = res.getInt(1);
						numcurr++;
						query = "update ordinale_candidato set numero_voti=? where sessione_voto=? and candidato=? and posizione=?";
						p = DBManager.getInstance().prepareStatement(query);
						p.setInt(1, numcurr);
						p.setString(2, idSessione);
						p.setInt(3, can.getIDCandidato());
						p.setInt(4, posizione);
						p.execute();

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Logger.getInstance().log("SQL ERROR AT: VotazioneJDBCDAO.java:save");
					throw new DBSQLException("SQL ERROR");
				}
			}
		}

	}

	/* @ensures par!=null && par.getNome()!=null&&IDSessione!=null@ */
	private void aggiornaPartito(Partito par, String IDSessione) {
		String query = "select * from riguarda_partito where Partito=? and Sessione_voto=?";
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		
		try {
			p.setString(1, par.getNome());
			p.setString(2, IDSessione);
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst()) {
				// se non c'e' nulla devo inserire il valore nuovo dentro la tabella
				System.out.println("VUOTO!");
				query = "insert into riguarda_partito(Partito,Sessione_voto,numero_voti) values(?,?,?)";
				p = DBManager.getInstance().prepareStatement(query);
				p.setString(1, par.getNome());
				p.setString(2, IDSessione);
				p.setInt(3, 1);
				p.execute();
			} else {
				// se non c'e' nulla devo inserire il valore nuovo dentro la tabella
				query = "update riguarda_partito set numero_voti=? where Sessione_voto=? and Partito=?";
				p = DBManager.getInstance().prepareStatement(query);
				p.setInt(1, getNumeroVotiPartito(par, IDSessione) + 1);
				p.setString(2, IDSessione);
				p.setString(3, par.getNome());
				p.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void aggiornaCandidato(Candidato can, String IDSessione) {
		System.out.println("ENTRO");
		String query = "select * from riguarda_candidato where Candidato=? and Sessione_voto=?";
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		
		try {
			p.setInt(1, can.getIDCandidato());
			p.setString(2, IDSessione);
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst()) {
				System.out.println("NON CE NULLA");
				// se non c'e' nulla devo inserire il valore nuovo dentro la tabella
				query = "insert into riguarda_candidato(Candidato,Sessione_voto,numero_voti) values(?,?,?)";
				p = DBManager.getInstance().prepareStatement(query);
				p.setInt(1, can.getIDCandidato());
				p.setString(2, IDSessione);
				p.setInt(3, 1);
				p.execute();
			} else {
				// se non c'e' nulla devo inserire il valore nuovo dentro la tabella
				System.out.println("UPDATE");
				query = "update riguarda_candidato set numero_voti=? where Sessione_voto=? and Candidato=?";
				p = DBManager.getInstance().prepareStatement(query);
				p.setInt(1, getNumeroVotiCandidato(can, IDSessione) + 1);
				p.setString(2, IDSessione);
				p.setInt(3, can.getIDCandidato());
				p.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getNumeroVotiPartito(Partito par, String IDSessione) {
		String query = "select numero_voti from riguarda_partito where Sessione_voto=? and Partito=?";
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		
		try {
			p.setString(1, IDSessione);
			p.setString(2, par.getNome());
			ResultSet res = p.executeQuery();
			res.next();
			int numVoti = res.getInt(1);
			return numVoti;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBSQLException("SQL ERRROR");
		}

	}

	private int getNumeroVotiCandidato(Candidato can, String IDSessione) {
		String query = "select numero_voti from riguarda_candidato where Sessione_voto=? and Candidato=?";
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		
		try {
			p.setString(1, IDSessione);
			p.setInt(2, can.getIDCandidato());
			ResultSet res = p.executeQuery();
			res.next();
			int numVoti = res.getInt(1);
			return numVoti;
		} catch (SQLException e) {
			throw new DBSQLException("SQL ERRROR");
		}

	}

	@Override
	public void confermaVotazione(String elettore, String sessione) {
		String query = "insert into vota(Utente, Sessione_voto) values(?, ?)";
		PreparedStatement p = DBManager.getInstance().prepareStatement(query);
		try {
			p.setString(1, elettore);
			p.setString(2, sessione);
			p.execute();
		} catch (SQLException err) {
			Logger.getInstance().log("SQL ERROR AT VotazioneJDBCDAO.java:confermaVotazione");
			throw new DBSQLException("SQL ERROR");
		}

	}

	@Override
	public boolean partecipatoSessione(String e, String s) {
		String q = "select * from vota where Utente = ? and Sessione_voto = ?;";
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		
		try {
			p.setString(1, e);
			p.setString(2, s);
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst()) {
				return false;
			}
		} catch (SQLException sqe) {
			Logger.getInstance().log("SQL ERROR AT VotazioneJDBCDAO.java:partecipatoSessione");
			throw new DBSQLException("SQL ERROR");
		}
		return true;
	}

	@Override
	public int getTotVotanti(SessioneVoto s) {
		String idSessione = s.getIDSessione();
		String q = "select count(*) from vota where Sessione_voto = ?;";
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		
		try {
			p.setString(1, idSessione);
			ResultSet res = p.executeQuery();
			if (!res.isBeforeFirst()) {
				Logger.getInstance().log("SESSIONE NON TROVATA");
				throw new DBSQLException("SESSIONE NON TROVATA");
			}
			res.next();
			return res.getInt(1);
		} catch (SQLException sqe) {
			Logger.getInstance().log("SQL ERROR AT VotazioneJDBCDAO.java:getTotVotantiSessione");
			throw new DBSQLException("SQL ERROR");
		}
	}

	@Override
	public int getTotAventiDirittoAlVoto() {
		String q = "select count(*) from Utente where gestore = '0'";
		PreparedStatement p = DBManager.getInstance().prepareStatement(q);
		
		try {
			ResultSet res = p.executeQuery();
			res.next();
			return res.getInt(1);
		} catch (SQLException sqe) {
			Logger.getInstance().log("SQL ERROR AT VotazioneJDBCDAO.java:getTotAventiDirittoAlVoto");
			throw new DBSQLException("SQL ERROR");
		}

	}

	@Override
	public VotoGeneric get(String id) {
		// DEAD METHOD
		return null;
	}

	@Override
	public List<VotoGeneric> getAll() {
		// DEAD METHOD
		return null;
	}

	@Override
	public void update(VotoGeneric t, VotoGeneric u) {
		// DEAD METHOD

	}

	@Override
	public void delete(VotoGeneric t) {
		// DEAD METHOD

	}

}
