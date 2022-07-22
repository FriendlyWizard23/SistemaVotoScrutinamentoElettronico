package generictests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import progetto.DAO.Sessione.SessioneJDBCDAO;
import progetto.models.Sessioni.SessioneVoto;
import progetto.models.Utenti.ABSUtente;
import progetto.models.Utenti.Gestore;
import progetto.models.Votazioni.VotoGeneric;
import progetto.models.Votazioni.VotoReferendum;
import progetto.utils.CodiceFiscale;
import progetto.utils.UserBean;

class GenericTests {

	@Test
	void UserTests() {
		ABSUtente abs=new Gestore("test","test");
		assertFalse(abs.isElettore());
		assertTrue(abs.isGestore());
	}
	@Test
	void VotazioniTests() {
		VotoGeneric v=new VotoReferendum(true,null);
		System.out.println(v.getTipo());
		assertEquals("referendum",v.getTipo());
	}
	@Test
	void cfparsertest() {
		CodiceFiscale cf=new CodiceFiscale("LMRJBI48R13H123M");
		assertTrue(cf.validate());
	}
}
