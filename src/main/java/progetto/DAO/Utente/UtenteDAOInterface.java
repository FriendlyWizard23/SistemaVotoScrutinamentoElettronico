package progetto.DAO.Utente;

import java.util.List;

import progetto.DAO.DAOInterface;
import progetto.models.Utenti.ABSUtente;
import progetto.utils.UserBean;
import progetto.DAO.Utente.*;

public interface UtenteDAOInterface extends DAOInterface<ABSUtente> {
	public ABSUtente login(UserBean loguser); // login

	public boolean registraElettore(ABSUtente t, String pass);

	public int getId(ABSUtente u);

}
