package progetto.controllers.gestore;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import progetto.App;
import progetto.DAO.Sessione.SessioneJDBCDAO;
import progetto.DAO.factory.JDBCDAOFactory;
import progetto.controllers.Controller;
import progetto.exceptions.DBSQLException;
import progetto.models.Sessioni.SessioneVoto;
import progetto.models.results.RisultatoGeneric;
import progetto.utils.Alerts;
import progetto.utils.SessioneWrappedGestore;

public class GestisciSessioneController extends Controller {
	private SessioneWrappedGestore swg;
	private SessioneJDBCDAO jjdbc = new SessioneJDBCDAO();
	@FXML
	private TextArea txtRisultato;
	@FXML
	private TextField txtSessione;
	@FXML
	private ChoiceBox<String> choiceSessione;

	@Override
	public void init(Object parameter) {
		App.getStage().setWidth(500);
		App.getStage().setHeight(500);
		if (parameter != null) {
			swg = (SessioneWrappedGestore) parameter;
		}
		List<SessioneVoto> sessioni = jjdbc.getAll();
		for (SessioneVoto sv : sessioni) {
			choiceSessione.getItems().add(sv.getIDSessione());
			choiceSessione.setValue(sv.getIDSessione());
		}

	}

	public void scrutinamento() {
		String sessione = choiceSessione.getValue().toString();
		if (jjdbc.status(sessione)) {
			Alerts.AlertERROR("IMPOSSIBILE SCRUTINARE, FERMARE PRIMA LA SESSIONE");
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION, "AVVIARE LO SCRUTINAMENTO ?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.NO) {
			return;
		}
		try {
			RisultatoGeneric rg = jjdbc.getRisultato(jjdbc.get(sessione));
			txtRisultato.setText(rg.getVincitore() + "\n" + rg.getRisultatiFull());
			jjdbc.disattivaSessione(sessione);
		} catch (DBSQLException e) {
			e.printStackTrace();
			Alerts.AlertERROR("ERRORE SCONOSCIUTO, ATTENDERE E RIPROVARE");
			return;
		}

	}

	public void stop() {
		String sessione = choiceSessione.getValue().toString();
		if (jjdbc.isNew(sessione)) {
			Alerts.AlertERROR("LA SESSIONE NON PUO' ESSERE ANCORA CHIUSA!");
			return;
		}
		if (!jjdbc.status(sessione)) {
			Alerts.AlertERROR("LA SESSIONE E' GIA CHIUSA");
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION, "FERMARE LA SESSIONE ?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) {
			return;
		}
		try {
			jjdbc.disattivaSessione(sessione);
			Alerts.AlertOK("SESSIONE DISATTIVATA!");
		} catch (DBSQLException e) {
			Alerts.AlertERROR("ERRORE SCONOSCIUTO, ATTENDERE E RIPROVARE");
			return;
		}
	}

	public void attiva() {
		String sessione = choiceSessione.getValue().toString();
		if (!jjdbc.isNew(sessione)) {
			Alerts.AlertERROR("LA SESSIONE NON PUO' ESSERE RIAPERTA!");
			return;
		}
		if (jjdbc.status(sessione)) {
			Alerts.AlertERROR("SESSIONE GIA' ATTIVA! ");
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION, "ATTIVARE LA SESSIONE?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.NO) {
			return;
		}
		try {
			jjdbc.attivaSessione(sessione);
			Alerts.AlertOK("SESSIONE ATTIVATA");
			jjdbc.setNotNew(sessione);
		} catch (DBSQLException e) {
			e.printStackTrace();
			Alerts.AlertERROR("IMPOSSIBILE ATTIVARE LA SESSIONE, ATTENDERE E RIPROVARE");
		}
	}

	private boolean checkSessione(String sessione) {
		SessioneJDBCDAO ss = (SessioneJDBCDAO) JDBCDAOFactory.getFactory().getSessioneDAOInstance();
		System.out.println(sessione);
		return !(sessione == null || sessione.length() != 12 || !ss.exists(sessione));
	}

	public void indietro() {
		this.changeView("views/MainGestore.fxml");
	}
}
