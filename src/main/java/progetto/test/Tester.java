package progetto.test;

import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxConnection.CapabilitiesGet;

import progetto.DAO.Candidato.CandidatoJDBCDAO;
import progetto.DAO.Sessione.SessioneJDBCDAO;
import progetto.DAO.Utente.UtenteJDBCDAO;
import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.SessioneVoto;
import progetto.models.Utenti.ABSUtente;

public class Tester {
	public static void main(String args[]) {
		/*
		 * System.out.println("SESSIONE VOTO:"); sessioneTest(); OK();
		 * System.out.println("UTENTE:"); utenteTest(); OK();
		 * System.out.println("CANDIDATO: "); candidatoTest(); OK();
		 */
		System.out.println("RISULTATI:");
		testRisultati();

	}

	private static void testRisultati() {
		SessioneJDBCDAO ss = new SessioneJDBCDAO();
		SessioneVoto s = ss.get("000000000002");
		System.out.println(s.toString());
		System.out.println(ss.getRisultato(s).getVincitore());
	}

	private static void candidatoTest() {
		CandidatoJDBCDAO ut = new CandidatoJDBCDAO();
		Candidato u = ut.get("1");
		System.out.println(u.toString());
		List<Candidato> lsu = ut.getAll();
		System.out.println("LIST CONTAINS: " + lsu.size());
		for (int i = 0; i < lsu.size(); i++) {
			u = lsu.get(i);
			System.out.println(u.toString());
		}
		System.out.println("");
		lsu = ut.getCandidati("LegaNord");
		for (int i = 0; i < lsu.size(); i++) {
			u = lsu.get(i);
			System.out.println(u.toString());
		}
		System.out.println("---------- TEST: SAVE ----------");

		Candidato can = new Candidato(69, "TEST", "TEST", "LegaNord");
		Candidato can2 = new Candidato(70, "TEST2", "TEST2", "LegaNord");
		Candidato can3 = new Candidato(71, "TEST3", "TEST3", "LegaNord");
		ut.save(can);
		ut.save(can2);
		lsu = ut.getCandidati("LegaNord");
		for (int i = 0; i < lsu.size(); i++) {
			u = lsu.get(i);
			System.out.println(u.toString());
		}
		System.out.println("---------- TEST: DELETE ----------");
		ut.delete(can);
		lsu = ut.getCandidati("LegaNord");
		for (int i = 0; i < lsu.size(); i++) {
			u = lsu.get(i);
			System.out.println(u.toString());
		}
		System.out.println("---------- TEST: UPDATE ----------");
		ut.update(can2, can3);
		lsu = ut.getCandidati("LegaNord");
		for (int i = 0; i < lsu.size(); i++) {
			u = lsu.get(i);
			System.out.println(u.toString());
		}
	}

	private static void utenteTest() {
		UtenteJDBCDAO ut = new UtenteJDBCDAO();
		ABSUtente u = ut.get("RMRLSN00P11F205O");
		System.out.println(u.toString());
		List<ABSUtente> lsu = ut.getAll();
		System.out.println("LIST CONTAINS: " + lsu.size());
		for (int i = 0; i < lsu.size(); i++) {
			u = lsu.get(i);
			System.out.println(u.toString());
		}

	}

	private static void sessioneTest() {
		SessioneJDBCDAO ss = new SessioneJDBCDAO();
		System.out.println(" -------- TEST GET -------- ");
		SessioneVoto sv = ss.get("000000000001");
		System.out.println(sv.toString());

		System.out.println(" -------- TEST SAVE -------- ");
		String iDSessioner = "000000000003";
		String tipologiaVoto = "test";
		String strategiaVincitore = "test";
		String domandaReferendum = "test";
		int numero_si = 0;
		int numero_no = 0;
		boolean status = false;
		boolean isnew = true;
		SessioneVoto nuova = new SessioneVoto(iDSessioner, tipologiaVoto, strategiaVincitore, domandaReferendum,
				numero_si, numero_no, status, isnew);
		ss.save(nuova);
		ss.start(nuova);
		List<SessioneVoto> lsv = ss.getAll();
		System.out.println("LIST CONTAINS: " + lsv.size());
		printListSessione(lsv);

		System.out.println(" -------- TEST DELETE -------- ");
		ss.delete(nuova);
		lsv = ss.getAll();
		System.out.println("LIST CONTAINS: " + lsv.size());
		printListSessione(lsv);

	}

	private static void printListSessione(List<SessioneVoto> lsv) {
		SessioneVoto sv;
		for (int i = 0; i < lsv.size(); i++) {
			sv = lsv.get(i);
			System.out.println(sv.toString());
		}
	}

	public static void OK() {
		System.out.println(
				"================================================== OK ==================================================");
	}
}
