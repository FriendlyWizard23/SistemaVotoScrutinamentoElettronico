import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import progetto.DAO.Sessione.SessioneJDBCDAO;
import progetto.DAO.Utente.UtenteJDBCDAO;
import progetto.exceptions.DBSQLException;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;
import progetto.models.Utenti.ABSUtente;
import progetto.utils.DBManager;
import progetto.utils.SHA256;
import progetto.utils.UserBean;

class DatabaseTest {
	@BeforeAll
	public static void setup() {
		DBManager.getInstance().openConnection();	
	}

	@Test
	void testLogin() {
		UtenteJDBCDAO usr=new UtenteJDBCDAO();
		ABSUtente log=usr.login(new UserBean("TESTGESTORE",SHA256.hash("test1234")));
		assertEquals(true,log.isGestore());
	}
	@Test
	void testSessioneExists() {
		SessioneJDBCDAO sess=new SessioneJDBCDAO();
		assertNotNull(sess.get("000000000001"));
	}
	@Test
	void testCandidatiPartito() {
		SessioneJDBCDAO sess=new SessioneJDBCDAO();
		assertNotNull(sess.getListaCandidatiFromPartito(new Partito("EuropaVerde",123)));
	}
	@Test
	void creaSessioneEsistente() {
		SessioneJDBCDAO sess=new SessioneJDBCDAO();
		SessioneVoto sv=new SessioneVoto("000000000001", null, null, null, 0, 0, false, false);
		Exception exception = assertThrows(DBSQLException.class, () -> sess.save(sv));
		assertEquals("SQL ERROR", exception.getMessage());
	}
}
