package progetto.controllers.gestore;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import progetto.App;
import progetto.DAO.Sessione.SessioneDAOInterface;
import progetto.DAO.Sessione.SessioneJDBCDAO;
import progetto.DAO.factory.JDBCDAOFactory;
import progetto.controllers.Controller;
import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;
import progetto.utils.Alerts;
import progetto.utils.SessioneWrappedGestore;

public class CategoricoPartitoController extends Controller {
	private SessioneWrappedGestore swg;
	private SessioneJDBCDAO sdi;
	private List<CheckBox> scelte = new ArrayList<CheckBox>();
	@FXML
	private RadioButton rdMaggioranza;
	@FXML
	private RadioButton rdMaggioranzaAssoluta;
	@FXML
	private TextField txtGestore;
	@FXML
	private TextField txtSessione;
	@FXML
	private ScrollPane scrollPartiti;

	@Override
	public void init(Object parameter) {
		sdi = (SessioneJDBCDAO) JDBCDAOFactory.getFactory().getSessioneDAOInstance();
		rdMaggioranza.setSelected(true);
		App.getStage().setWidth(420);
		App.getStage().setHeight(450);
		if (parameter != null) {
			swg = (SessioneWrappedGestore) parameter;
			txtGestore.setText(swg.getSio().getUsr().getCodiceFiscale());
			txtGestore.setEditable(false);
			txtSessione.setText(swg.getTocreate());
			txtSessione.setEditable(false);
			loadPartiti();
		}

	}

	private void loadPartiti() {
		List<Partito> lp = sdi.getListPartiti();
		VBox boxLista = new VBox(10);
		GridPane g = new GridPane();
		g.setAlignment(Pos.CENTER);
		g.setGridLinesVisible(true);
		int i = 0;
		for (Partito p : lp) {
			CheckBox part = new CheckBox();
			part.setUserData(p);
			part.setText(p.getNome());
			part.setGraphic(boxLista);
			GridPane.setMargin(part, new Insets(20, 20, 20, 20));
			g.add(part, 0, i);
			i++;
			scelte.add(part);
		}
		scrollPartiti.setContent(g);
	}

	public void indietro() {
		this.changeView("views/nuovasessione/nuovasessione.fxml", swg);
	}

	public void creaSessione() {
		List<Partito> lst = new ArrayList<Partito>();
		int count = 0;
		for (CheckBox x : scelte) {
			if (x.isSelected()) {
				lst.add((Partito) x.getUserData());
				count++;
			}
		}
		if (count == 0) {
			Alerts.AlertERROR("SELEZIONA ALMENO UN PARTITO!");
			return;
		}
		String strategia = "maggioranza";
		if (rdMaggioranzaAssoluta.isSelected()) {
			strategia = "maggioranzaassoluta";
		}
		SessioneVoto nuovaSessione = new SessioneVoto(swg.getTocreate(), "categoricoPartito", strategia, null, 0, 0,
				false, true);
		sdi.saveCategoricoPartito(lst, nuovaSessione);
		Alerts.AlertOK("SESSIONE CREATA CON SUCCESSO");
		this.changeView("views/MainGestore.fxml");
	}

}
