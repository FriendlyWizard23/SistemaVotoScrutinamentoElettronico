package progetto.controllers.gestore;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import progetto.App;
import progetto.DAO.Sessione.SessioneJDBCDAO;
import progetto.DAO.factory.JDBCDAOFactory;
import progetto.controllers.Controller;
import progetto.utils.Alerts;
import progetto.utils.SessioneWrappedGestore;

public class NuovaSessione extends Controller {
	private SessioneWrappedGestore swg;

	@FXML
	private TextField txtIDSessione;
	@FXML
	private RadioButton rdReferendum;
	@FXML
	private RadioButton rdCategoricoPartito;
	@FXML
	private RadioButton rdCategoricoCandidato;
	@FXML
	private RadioButton rdCategoricoPreferenza;
	@FXML
	private RadioButton rdOrdinalePartito;
	@FXML
	private RadioButton rdOrdinaleCandidato;

	@Override
	public void init(Object parameter) {
		App.getStage().setWidth(320);
		App.getStage().setHeight(350);
		rdReferendum.setSelected(true);
		if (parameter != null) {
			swg = (SessioneWrappedGestore) parameter;
		}
	}

	// TODO CONTINUARE DA QUA!
	public void avanti() {
		if (checkSessione(txtIDSessione.getText())) {
			swg.setTocreate(txtIDSessione.getText());
			if (rdReferendum.isSelected()) {
				this.changeView("views/nuovasessione/creaReferendumView.fxml", swg);
			} else if (rdCategoricoPartito.isSelected()) {
				this.changeView("views/nuovasessione/creaCategoricoPartitoView.fxml", swg);
			} else if (rdCategoricoCandidato.isSelected()) {
				this.changeView("views/nuovasessione/creaCategoricoCandidatoView.fxml", swg);
			} else if (rdCategoricoPreferenza.isSelected()) {
				this.changeView("views/nuovasessione/creaCategoricoPreferenzaView.fxml", swg);
			} else if (rdOrdinalePartito.isSelected()) {
				this.changeView("views/nuovasessione/creaOrdinalePartitoView.fxml", swg);
			} else {
				this.changeView("views/nuovasessione/creaOrdinaleCandidatoView.fxml", swg);
			}
		} else {
			Alerts.AlertERROR("ID SESSIONE NON VALIDO");
		}

	}

	public void indietro() {
		this.changeView("../views/MainGestore.fxml");
	}

	/**
	 * metodo per controllare che la nuova sessione sia valida
	 * 
	 * @param sessione sessione da controllare
	 * @return true se la sessione inserita e' valida, false altrimenti
	 */
	private boolean checkSessione(String sessione) {
		SessioneJDBCDAO ss = (SessioneJDBCDAO) JDBCDAOFactory.getFactory().getSessioneDAOInstance();
		System.out.println(sessione);
		return !(sessione == null || sessione.length() != 12 || ss.exists(sessione));
	}
}
