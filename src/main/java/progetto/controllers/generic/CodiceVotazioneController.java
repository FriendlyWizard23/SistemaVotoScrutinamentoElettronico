package progetto.controllers.generic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import progetto.App;
import progetto.DAO.Sessione.SessioneJDBCDAO;
import progetto.DAO.Votazione.VotazioneDAOInterface;
import progetto.DAO.Votazione.VotazioneJDBCDAO;
import progetto.controllers.Controller;
import progetto.utils.*;

public class CodiceVotazioneController extends Controller {
	SessioneInitObject sio;
	private UserBean usr;
	@FXML
	private Button btnInvio;

	@FXML
	private TextField txtCodice;

	@FXML
	private Label lblUsername;

	@Override
	public void init(Object parameter) {
		// TODO Auto-generated method stub
		App.getStage().setWidth(300);
		App.getStage().setHeight(300);
		App.stg.setResizable(false);
		sio = (SessioneInitObject) parameter;
		this.usr = sio.getUsr();
		lblUsername.setText("");
		lblUsername.setText("UTENTE: " + sio.getUsr().getCodiceFiscale());

	}

	public void indietro() {
		this.changeView("views/loginview.fxml", null);
	}

	public void avviaPaginaVoto() {
		String idses = txtCodice.getText();
		SessioneInitObject sio = new SessioneInitObject();
		SessioneJDBCDAO ss = new SessioneJDBCDAO();
		VotazioneJDBCDAO votazione = new VotazioneJDBCDAO();
		if (!checkValidID(idses)) {
			Alerts.AlertERROR("ERRORE: CODICE SESSIONE NON VALIDO");
		} else if (votazione.partecipatoSessione(usr.getCodiceFiscale(), idses)) {
			Alerts.AlertERROR("ERRORE: HAI GIA VOTATO QUESTA SESSIONE");
		} else if (!ss.get(idses).getStatus()) {
			Alerts.AlertERROR("ERRORE: LA SESSIONE DI VOTO E' TERMINATA");
		} else {
			sio.setIserror(false);
			sio.setIDSessione(idses);
			sio.setUsr(this.usr);
			this.changeView("views/votaview.fxml", sio);
		}
	}

	private boolean checkValidID(String id) {
		SessioneJDBCDAO ss = new SessioneJDBCDAO();
		return (id != null && !id.isEmpty() && !id.isBlank() && ss.exists(id));
	}

}
