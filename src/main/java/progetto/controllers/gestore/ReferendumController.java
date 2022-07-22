package progetto.controllers.gestore;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import progetto.App;
import progetto.DAO.Sessione.SessioneDAOInterface;
import progetto.DAO.factory.JDBCDAOFactory;
import progetto.controllers.Controller;
import progetto.models.Sessioni.SessioneVoto;
import progetto.utils.Alerts;
import progetto.utils.SessioneWrappedGestore;

public class ReferendumController extends Controller {
	private SessioneWrappedGestore swg;
	SessioneDAOInterface ss;
	@FXML
	TextField txtGestore;
	@FXML
	TextField txtSessione;
	@FXML
	TextArea txtDomanda;
	@FXML
	RadioButton rdQuorum;
	@FXML
	RadioButton rdSenzaQuorum;

	@Override
	public void init(Object parameter) {
		rdQuorum.setSelected(true);
		App.getStage().setWidth(800);
		App.getStage().setHeight(430);
		if (parameter != null) {
			swg = (SessioneWrappedGestore) parameter;
			txtGestore.setText(swg.getSio().getUsr().getCodiceFiscale());
			txtGestore.setEditable(false);
			txtSessione.setText(swg.getTocreate());
			txtSessione.setEditable(false);
		}
	}

	public void indietro() {
		this.changeView("views/nuovasessione/nuovasessione.fxml", swg);
	}

	public void creaSessione() {
		ss = JDBCDAOFactory.getFactory().getSessioneDAOInstance();
		if (txtDomanda.getText() == null || txtDomanda.getText().isEmpty() || txtDomanda.getText().isBlank()) {
			Alerts.AlertERROR("ERRORE: CAMPI MANCANTI");
		} else {
			String strategia = "senzaquorum";
			if (rdQuorum.isSelected()) {
				strategia = "conquorum";
			}
			SessioneVoto sv = new SessioneVoto(swg.getTocreate(), "referendum", strategia, txtDomanda.getText(), 0, 0,
					false, true);
			ss.save(sv);
			Alerts.AlertOK("SESSIONE CREATA CON SUCCESSO");
			this.changeView("views/MainGestore.fxml");
		}

	}

}
