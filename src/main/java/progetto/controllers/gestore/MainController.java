package progetto.controllers.gestore;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import progetto.App;
import progetto.controllers.Controller;
import progetto.utils.SessioneInitObject;
import progetto.utils.SessioneWrappedGestore;

public class MainController extends Controller {
	SessioneWrappedGestore swg;
	SessioneInitObject sio;
	@FXML
	Label lblGestore;

	@FXML
	Button btnModifica;

	@FXML
	Button btnCrea;

	@Override
	public void init(Object parameter) {
		App.getStage().setWidth(380);
		App.getStage().setHeight(280);
		if (parameter != null) {
			sio = (SessioneInitObject) parameter;
			App.getStage().setResizable(false);
			if (!sio.isIserror()) {
				lblGestore.setText("");
				lblGestore.setText("GESTORE: " + sio.getUsr().getCodiceFiscale());
			}
		}

	}

	public void indietro() {
		this.changeView("views/LoginView.fxml", null);
	}

	public void modificaSessione() {
		this.changeView("views/ModificaSessioneView.fxml", swg);
	}

	public void creaSessione() {
		swg = new SessioneWrappedGestore(sio);
		this.changeView("views/nuovasessione/nuovasessione.fxml", swg);
	}

}
