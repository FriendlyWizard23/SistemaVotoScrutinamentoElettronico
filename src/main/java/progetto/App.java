package progetto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import progetto.controllers.generic.LoginController;
import progetto.utils.DBManager;

public class App extends Application {
	private static Scene primaryScene;
	public static Stage stg;
	private static LoginController loginController;

	public static void main(String args[]) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		DBManager.getInstance().openConnection();
		FXMLLoader loader = new FXMLLoader(App.class.getResource("views/LoginView.fxml"));
		Parent root = loader.load();
		Scene login = new Scene(root);
		loginController = loader.getController();
		primaryScene = login;
		stg = primaryStage;
		primaryStage.setScene(login);
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	@Override
	public void stop() {
		DBManager.getInstance().closeConnection();
		System.exit(0);
	}
	public static Scene getAppScene() {
		return primaryScene;
	}

	public static Stage getStage() {
		return stg;
	}

}