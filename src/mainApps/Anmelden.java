package mainApps;

import controls.AnmeldenCtl;
import static controls.AnmeldenCtl.aktDepot;
import controls.MenuCtl;
import controls.TradingIdeeCtl;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import services.HostServicesControllerFactory;

public class Anmelden extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        //Pointer auf HostServices --> siehe TradingIdeeCtl.java Methode onBroker
        stage.getProperties().put("hostServices", this.getHostServices());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/anmelden.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AnmeldenCtl anmeldenCtl = fxmlLoader.getController();
        anmeldenCtl.setStage(stage);

        //FactoryMethode
        //fxmlLoader.setControllerFactory(new HostServicesControllerFactory(getHostServices()));
        Scene scene = new Scene(root);
        stage.setTitle("Depot anlegen");
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../views/ProjektCss.css").toExternalForm());
        stage.show();

    }

    public void hostService() {

    }

    private void playMusic(String title) {
        String musicFile = title;
        URL fileUrl = getClass().getResource(musicFile);

        Media media = new Media(fileUrl.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    @Override
    public void init() throws Exception {
        String musicFile = "../musik/Freddie Mercury-s LAST VIDEO 1991!!! RO SUB.mp3";
        playMusic(musicFile);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
