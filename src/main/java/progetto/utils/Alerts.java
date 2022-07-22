package progetto.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	public static void AlertOK(String a) {
		Alert alert = new Alert(AlertType.INFORMATION, a);
		alert.showAndWait();
	}

	public static void AlertERROR(String a) {
		Alert alert = new Alert(AlertType.ERROR, a);
		alert.showAndWait();
	}
}
