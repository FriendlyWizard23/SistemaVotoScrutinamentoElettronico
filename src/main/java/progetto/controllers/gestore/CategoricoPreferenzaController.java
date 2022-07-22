package progetto.controllers.gestore;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import progetto.App;
import progetto.DAO.Sessione.SessioneDAOInterface;
import progetto.DAO.Sessione.SessioneJDBCDAO;
import progetto.DAO.lista.ListaJDBCDAO;
import progetto.controllers.Controller;
import progetto.models.Sessioni.ListaCandidati;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;
import progetto.utils.Alerts;
import progetto.utils.SessioneWrappedGestore;

public class CategoricoPreferenzaController extends Controller {
	private SessioneWrappedGestore swg;
	private List<CheckBox> scelte = new ArrayList<CheckBox>();
	private SessioneJDBCDAO sdi;
	@FXML
	private RadioButton rdMaggioranza;
	@FXML
	private RadioButton rdMaggioranzaAssoluta;
	@FXML
	private TextField txtGestore;
	@FXML
	private TextField txtSessione;
	@FXML
	private ScrollPane scrollListe;

	public void init(Object parameter) {
		rdMaggioranza.setSelected(true);
		App.getStage().setWidth(800);
		App.getStage().setHeight(430);
		if (parameter != null) {
			swg = (SessioneWrappedGestore) parameter;
			txtGestore.setText(swg.getSio().getUsr().getCodiceFiscale());
			txtGestore.setEditable(false);
			txtSessione.setText(swg.getTocreate());
			txtSessione.setEditable(false);
			loadListe();
		}
	}

	public void indietro() {
		this.changeView("views/nuovasessione/nuovasessione.fxml", swg);
	}

	public void creaSessione() {
		List<ListaCandidati> lst = new ArrayList<ListaCandidati>();
		ListaJDBCDAO lis = new ListaJDBCDAO();
		int count = 0;
		for (CheckBox x : scelte) {
			if (x.isSelected()) {
				lst.add((ListaCandidati) x.getUserData());
				count++;
			}
		}
		if (count == 0) {
			Alerts.AlertERROR("SELEZIONA ALMENO UNA LISTA!");
			return;
		}
		String strategia = "maggioranza";
		if (rdMaggioranzaAssoluta.isSelected()) {
			strategia = "maggioranzaassoluta";
		}
		SessioneVoto nuovaSessione = new SessioneVoto(swg.getTocreate(), "categoricoPreferenza", strategia, null, 0, 0,
				false, true);
		sdi = new SessioneJDBCDAO();
		sdi.save(nuovaSessione);
		for (ListaCandidati lc : lst) {
			lis.saveListaInSessione(lc, nuovaSessione);
		}
		Alerts.AlertOK("SESSIONE CREATA CON SUCCESSO");
		this.changeView("views/MainGestore.fxml");
	}

	private void loadListe() {
		ListaJDBCDAO lis = new ListaJDBCDAO();
		List<ListaCandidati> llc = lis.getAll();
		VBox boxLista = new VBox(10);
		GridPane g = new GridPane();
		g.setAlignment(Pos.CENTER);
		g.setGridLinesVisible(true);
		int i = 0;
		for (ListaCandidati lc : llc) {
			CheckBox part = new CheckBox();
			part.setUserData(lc);
			part.setText(lc.toString());
			part.setGraphic(boxLista);
			GridPane.setMargin(part, new Insets(20, 20, 20, 20));
			g.add(part, 0, i);
			i++;
			scelte.add(part);
		}
		scrollListe.setContent(g);
	}
}
