package mainApps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DepotApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("../views/DepotXml.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Depot anlegen");
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("../views/ProjektCss.css").toExternalForm());
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
