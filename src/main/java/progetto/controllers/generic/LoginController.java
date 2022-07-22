package progetto.controllers.generic;

import javafx.fxml.FXML;
import progetto.models.Utenti.ABSUtente;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import progetto.controllers.Controller;
import progetto.exceptions.UserNotFoundException;
import progetto.logger.Logger;
import progetto.DAO.Utente.UtenteDAOInterface;
import progetto.DAO.factory.ABSDAOFactory;
import progetto.utils.*;
import progetto.App;

public class LoginController extends Controller {
	private UtenteDAOInterface utenteDAO = ABSDAOFactory.getFactory().getUtenteDAOInstance();
	@FXML
	private Button btnLogin;

	@FXML
	private TextField txtUsername;

	@FXML
	private TextField txtPassword;

	@Override
	public void init(Object parameter) {
		App.getStage().setWidth(300);
		App.getStage().setHeight(300);
		App.stg.setResizable(false);
	}

	public void AuthUser() {
		UserBean newuser = new UserBean();
		txtUsername.setText(txtUsername.getText().toUpperCase());
		newuser.setCodiceFiscale(txtUsername.getText());
		newuser.setPassword(SHA256.hash(txtPassword.getText()));
		try {
			// se i dati sono validi si procede con il tutto
			if (checkData(newuser)) {
				ABSUtente tolog = utenteDAO.login(newuser);
				newuser.setRole(tolog.isGestore());
				SessioneInitObject sio = new SessioneInitObject();
				sio.setIserror(false);
				sio.setUsr(newuser);
				if (tolog.isElettore())
					this.changeView("views/CodiceVotazioneView.fxml", sio);
				else
					this.changeView("views/MainGestore.fxml", sio);
			}
			// se i dati inseriti non sono validi allora caccia un alert
			else if (!checkData(newuser)) {
				Alerts.AlertERROR("DATI INSERITI NON VALIDI!");
				Logger.getInstance().log("DATI INSERITI NON VALIDI");
			}
			// cattura un UNF exception e caccia un alert
		} catch (UserNotFoundException e) {
			Alerts.AlertERROR("ERRORE NON PRESENTE NEL SISTEMA / CREDENZIALI NON VALIDE!");
			Logger.getInstance().log("UTENTE NON PRESENTE");
		}

	}

	/**
	 * This method is used to check wether username and passworD INTEGRITY
	 * 
	 * @param usr, UserBean obj that contains username and password to check
	 * @return true if the usrname and password do not contain errors
	 */
	private boolean checkData(UserBean usr) {
		/**
		 * This method is used to check the integrity of the input data
		 */
		/*
		 * CodiceFiscale CF=new CodiceFiscale(usr.getCodiceFiscale());
		 * if(CF.validate()&&CheckPwdRules(usr.getPassword())){ return true; } return
		 * false;
		 */
		return true;
	}

	/**
	 * this method is used to check wether a password is well-structured or not.
	 * 
	 * @param pwd the password to rule-check
	 * @return true if the password follows the rules defined in this method
	 */
	private boolean CheckPwdRules(String pwd) {
		return pwd != null && pwd != "";
	}

}
