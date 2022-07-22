package progetto.controllers.elettore;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import progetto.App;
import progetto.DAO.Sessione.SessioneDAOInterface;
import progetto.DAO.Sessione.SessioneJDBCDAO;
import progetto.DAO.Votazione.VotazioneDAOInterface;
import progetto.DAO.Votazione.VotazioneJDBCDAO;
import progetto.DAO.lista.ListaJDBCDAO;
import progetto.controllers.Controller;
import progetto.models.Sessioni.Candidato;
import progetto.models.Sessioni.ListaCandidati;
import progetto.models.Sessioni.Partito;
import progetto.models.Sessioni.SessioneVoto;
import progetto.models.Votazioni.SchedaBianca;
import progetto.models.Votazioni.VotoCategoricoCandidato;
import progetto.models.Votazioni.VotoCategoricoPartito;
import progetto.models.Votazioni.VotoCategoricoPreferenza;
import progetto.models.Votazioni.VotoGeneric;
import progetto.models.Votazioni.VotoOrdinaleCandidato;
import progetto.models.Votazioni.VotoOrdinalePartito;
import progetto.models.Votazioni.VotoReferendum;
import progetto.utils.Alerts;
import progetto.utils.CANDPOSPair;
import progetto.utils.PARTPOSPair;
import progetto.utils.SessioneInitObject;

public class votazioneController extends Controller {
	SessioneInitObject sio;
	private SessioneVoto ss;
	private SessioneJDBCDAO sessioneDAO;
	private VotazioneJDBCDAO votazione;
	private String IDUtente;
	private ListaJDBCDAO lisjdbc;
	private ToggleGroup tg;
	private Pair<Partito, List<CheckBox>> scelta;
	List<Pair<TextField, Partito>> ordinipartiti;
	List<Pair<TextField, Candidato>> ordinicandidati;
	@FXML
	private Label lblIDSessione;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private Button btnBianca;
	@FXML
	private Button btnAvanti;
	@FXML
	private Pane paneMain;

	@Override
	public void init(Object parameter) {
		App.getStage().setWidth(900);
		App.getStage().setHeight(500);
		progetto.App.getStage().setResizable(false);
		sio = (SessioneInitObject) parameter;
		lblIDSessione.setText("ID SESSIONE: " + sio.getIDSessione());
		IDUtente = sio.getUsr().getCodiceFiscale();
		SessioneJDBCDAO ssjdbc = new SessioneJDBCDAO();
		ss = ssjdbc.get(sio.getIDSessione());
		votazione(sio.getIDSessione());
	}

	public void indietro() {
		this.changeView("views/CodiceVotazioneView.fxml", sio);
	}

	public void votazione(String IDSessione) {
		SessioneJDBCDAO ssjdbc = new SessioneJDBCDAO();
		String tipo = ss.getTipologiaVoto();
		switch (tipo) {
		case "referendum":
			votazioneReferendum();
			break;
		case "ordinalepartito":
			votazioneOrdinalePartito();
			break;
		case "ordinalecandidato":
			votazioneOrdinaleCandidato();
			break;
		case "categoricoPartito":
			votazioneCategorica();
			break;
		case "categoricoCandidato":
			votazioneCategorica();
			break;
		case "categoricoPreferenza":
			votazioneCategoricaPreferenza();
			break;
		}
	}

	private void votazioneOrdinalePartito() {
		sessioneDAO = new SessioneJDBCDAO();
		GridPane g = new GridPane();
		g.setAlignment(Pos.CENTER);
		g.setGridLinesVisible(true);
		tg = new ToggleGroup();

		// ottengo la lista partiti di una sessione
		List<Partito> partiti = sessioneDAO.getListPartiti(ss);
		ordinipartiti = new ArrayList<Pair<TextField, Partito>>();
		int i = 0, j = 0;
		for (Partito p : partiti) {
			HBox boxLista = new HBox(10);
			boxLista.setAlignment(Pos.CENTER);

			VBox boxInfo = new VBox(10);
			boxInfo.setAlignment(Pos.CENTER);
			boxInfo.getChildren().add(new Label(p.getNome()));

			TextField t = new TextField();
			t.setPromptText("Ordine");
			boxLista.getChildren().add(t);
			boxLista.getChildren().add(boxInfo);

			ordinipartiti.add(new Pair<TextField, Partito>(t, p));

			GridPane.setMargin(boxLista, new Insets(20, 20, 20, 20));
			g.add(boxLista, j, i);
			i++;
		}
		scrollPane.setContent(g);
	}

	private void votazioneOrdinaleCandidato() {
		sessioneDAO = new SessioneJDBCDAO();
		GridPane g = new GridPane();
		g.setAlignment(Pos.CENTER);
		g.setGridLinesVisible(true);
		tg = new ToggleGroup();
		// ottengo la lista partiti di una sessione
		List<Candidato> candidati = sessioneDAO.getListCandidati(ss);
		ordinicandidati = new ArrayList<Pair<TextField, Candidato>>();
		int i = 0, j = 0;
		for (Candidato p : candidati) {
			HBox boxLista = new HBox(10);
			boxLista.setAlignment(Pos.CENTER);
			VBox boxInfo = new VBox(10);
			boxInfo.setAlignment(Pos.CENTER);
			boxInfo.getChildren().add(new Label(p.getNome() + " " + p.getCognome()));
			TextField t = new TextField();
			t.setPromptText("Ordine");
			boxLista.getChildren().add(t);
			boxLista.getChildren().add(boxInfo);
			ordinicandidati.add(new Pair<TextField, Candidato>(t, p)); // lista di candidati
			GridPane.setMargin(boxLista, new Insets(20, 20, 20, 20));
			g.add(boxLista, j, i);
			i++;
		}
		scrollPane.setContent(g);
	}

	private void votazioneReferendum() {
		VBox domandaETasti = new VBox(10);
		domandaETasti.setAlignment(Pos.CENTER);
		domandaETasti.getChildren().add(new Label(ss.getDomandaReferendum()));
		HBox tasti = new HBox(20);
		tg = new ToggleGroup();
		tasti.setAlignment(Pos.CENTER);

		RadioButton favorevole = new RadioButton("Favorevole");
		favorevole.setSelected(true);
		favorevole.setUserData(true);
		RadioButton nonFavorevole = new RadioButton("Non Favorevole");
		nonFavorevole.setUserData(false);

		favorevole.setToggleGroup(tg);
		nonFavorevole.setToggleGroup(tg);
		tasti.getChildren().addAll(favorevole, nonFavorevole);

		domandaETasti.getChildren().add(tasti);
		scrollPane.setContent(domandaETasti);
		scrollPane.autosize();
	}

	private void votazioneCategorica() {
		sessioneDAO = new SessioneJDBCDAO();
		GridPane g = new GridPane();
		g.setAlignment(Pos.CENTER);
		g.setGridLinesVisible(true);
		VBox boxLista = new VBox(10);
		boxLista.setAlignment(Pos.CENTER);
		int i = 0;
		if (ss.getTipologiaVoto().equals("categoricoPartito")) {
			// SE SI PARLA DI UNA SCELTA DI PARTITI ALLORA MOSTRA I PARTITI DELLA SESSIONE
			List<Partito> lp = sessioneDAO.getListPartiti(ss);
			tg = new ToggleGroup();
			for (Partito p : lp) {
				RadioButton lista = new RadioButton();
				lista.setToggleGroup(tg);
				lista.setUserData(p);
				lista.setText(p.getNome());
				lista.setGraphic(boxLista);
				if (i == 0)
					lista.setSelected(true);
				GridPane.setMargin(lista, new Insets(20, 20, 20, 20));
				g.add(lista, 0, i);
				i++;
			}
			scrollPane.setContent(g);

		} else {
			// SE SI PARLA DI UNA SCELTA DI CANDIDATI E BASTA ALLORA MOSTRA SOLO I CANDIDATI
			List<Candidato> lc = sessioneDAO.getListCandidati(ss);
			tg = new ToggleGroup();
			for (Candidato c : lc) {
				RadioButton lista = new RadioButton();
				lista.setToggleGroup(tg);
				lista.setUserData(c);
				lista.setText("CANDIDATO: " + c.getNome() + " " + c.getCognome() + " PARTITO: " + c.getPartito());
				lista.setGraphic(boxLista);
				GridPane.setMargin(lista, new Insets(20, 20, 20, 20));
				g.add(lista, 0, i);
				i++;
			}
			scrollPane.setContent(g);
		}
	}

	private void votazioneCategoricaPreferenza() {
		tg = new ToggleGroup();
		VBox divisione = new VBox(10);
		divisione.setAlignment(Pos.CENTER);
		HBox partiti = new HBox(10);
		partiti.setAlignment(Pos.CENTER);
		VBox checkboxPane = new VBox(10);
		checkboxPane.setAlignment(Pos.CENTER);
		divisione.getChildren().addAll(partiti, checkboxPane);
		SessioneJDBCDAO sessioneDAO = new SessioneJDBCDAO();
		List<Partito> lp = sessioneDAO.getListPartiti(ss);
		int i = 0;
		for (Partito l : lp) {
			RadioButton partito = new RadioButton(l.getNome());
			partito.setToggleGroup(tg);
			partito.setUserData(l);
			partiti.getChildren().add(partito);
			i++;
		}
		tg.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> obs, Toggle oldT, Toggle newT) -> {
			if (tg.getSelectedToggle() != null) {
				checkboxPane.getChildren().clear();
				Partito partitoscelto = (Partito) tg.getSelectedToggle().getUserData();

				scelta = new Pair<Partito, List<CheckBox>>(partitoscelto, new ArrayList<CheckBox>());
				// List<Candidato> can=sessioneDAO.getListaCandidatiFromPartito(partitoscelto);

				/**
				 * La votazione categorica con preferenza si basa su una LISTA di candidati
				 * associati ad un PARTITO se dovessimo parlare in ambito insiemistico, una
				 * lista è un sottoinsieme dei candidati associati ad un partito.
				 */

				List<Candidato> can = sessioneDAO.getListaCandidati(ss.getIDSessione(), partitoscelto).getCandidati();
				for (Candidato c : can) {
					CheckBox check = new CheckBox(c.getNome() + " " + c.getCognome());
					check.setUserData(c);
					checkboxPane.getChildren().add(check);
					scelta.getValue().add(check);
				}
			}
		});
		scrollPane.setContent(divisione);

	}

	public void prosegui() {
		Alert alert = new Alert(AlertType.CONFIRMATION, "CONFERMI QUESTO VOTO?");
		alert.showAndWait();
		votazione = new VotazioneJDBCDAO();
		if (alert.getResult() == ButtonType.OK) {
			if (ss.getTipologiaVoto().equals("referendum")) {
				boolean risposta = (Boolean) ((RadioButton) tg.getSelectedToggle()).getUserData();
				VotoGeneric v = new VotoReferendum(risposta, ss);
				votazione.save(v);
				confermatoEdEsci();
			} else if (ss.getTipologiaVoto().equals("categoricoPartito")) {
				Partito selezionato = (Partito) ((RadioButton) tg.getSelectedToggle()).getUserData();
				String nomepartito = selezionato.getNome();
				VotoGeneric v = new VotoCategoricoPartito(nomepartito, ss);
				votazione.save(v);
				confermatoEdEsci();
			} else if (ss.getTipologiaVoto().equals("categoricoCandidato")) {
				Candidato selezionato = (Candidato) ((RadioButton) tg.getSelectedToggle()).getUserData();
				int IDCandidato = selezionato.getIDCandidato();
				VotoGeneric v = new VotoCategoricoCandidato(IDCandidato, ss);
				votazione.save(v);
				confermatoEdEsci();
			} else if (ss.getTipologiaVoto().equals("categoricoPreferenza")) {
				List<Candidato> lst = new ArrayList<Candidato>();
				int count = 0;
				for (CheckBox x : scelta.getValue()) {
					if (x.isSelected()) {
						lst.add((Candidato) x.getUserData());
						count++;
					}
				}
				if (count == 0) {
					Alerts.AlertERROR("SELEZIONA ALMENO UN CANDIDATO!");
				}
				VotoCategoricoPreferenza v = new VotoCategoricoPreferenza(scelta.getKey(), lst, ss);
				votazione.save(v);
				confermatoEdEsci();
			}
			// se si tratta di ordinale partito

			/**
			 * SI SUPPONGA CHE L'UTENTE INSERISCA GLI ORDINI CORRETTAMENTE
			 */

			else if (ss.getTipologiaVoto().equals("ordinalepartito")) {
				List<PARTPOSPair> partitiposizioni = new ArrayList<PARTPOSPair>();
				for (Pair<TextField, Partito> p : ordinipartiti) {
					if (p.getKey().getText() == null || p.getKey().getText().isEmpty()) {
						Alerts.AlertERROR("INSERIRE TUTTI GLI ORDINI!");
						return;
					}
					partitiposizioni.add(new PARTPOSPair(p.getValue(), Integer.valueOf(p.getKey().getText())));
				}
				VotoOrdinalePartito vop = new VotoOrdinalePartito(ss, partitiposizioni);
				votazione.save(vop);
				confermatoEdEsci();
			}

			/**
			 * SI SUPPONGA CHE L'UTENTE INSERISCA GLI ORDINI CORRETTAMENTE
			 * 
			 */

			else if (ss.getTipologiaVoto().equals("ordinalecandidato")) {
				List<CANDPOSPair> candidatiposizioni = new ArrayList<CANDPOSPair>();
				for (Pair<TextField, Candidato> p : ordinicandidati) {
					if (p.getKey().getText() == null || p.getKey().getText().isEmpty()) {
						Alerts.AlertERROR("INSERIRE TUTTI GLI ORDINI!");
						return;
					}
					candidatiposizioni.add(new CANDPOSPair(p.getValue(), Integer.valueOf(p.getKey().getText())));
				}
				VotoOrdinaleCandidato voc = new VotoOrdinaleCandidato(ss, candidatiposizioni);
				votazione.save(voc);
				confermatoEdEsci();
			}
		}
	}

	public void schedabianca() {
		votazione = new VotazioneJDBCDAO();
		Alert alert = new Alert(AlertType.CONFIRMATION, "CONFERMI INVIO DELLA SCHEDA BIANCA?");
		SchedaBianca sb = new SchedaBianca(ss);
		votazione.save(sb);
		votazione.confermaVotazione(IDUtente, ss.getIDSessione());
		alert.showAndWait();
		this.changeView("views/LoginView.fxml");
	}

	public void confermatoEdEsci() {
		Alert conferma = new Alert(AlertType.INFORMATION, "VOTO CONFERMATO");
		conferma.showAndWait();
		votazione.confermaVotazione(IDUtente, ss.getIDSessione());
		this.changeView("views/LoginView.fxml");
	}

}
