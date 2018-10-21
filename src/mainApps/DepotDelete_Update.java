package mainApps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DepotDelete_Update extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("../views/DepotDelete.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Depotverwaltung");
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("../views/ProjektCss.css").toExternalForm());
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
