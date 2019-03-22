package mainApps;

import controls.AnmeldenCtl;
import controls.TransaktionCtl;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import services.StageSingleton;

public class Anmelden extends Application {

    private Parent rootNode;
    private AnmeldenCtl anmeldenCtl = new AnmeldenCtl();
    

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        String musicFile = "../musik/Freddie Mercury-s LAST VIDEO 1991!!! RO SUB.mp3";
        playMusic(musicFile);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/anmelden.fxml"));        
        rootNode = fxmlLoader.load();        
        stage = StageSingleton.getInstance();
        stage.setScene(new Scene(rootNode));
        stage.getScene().getStylesheets().add(getClass().getResource("../views/ProjektCss.css").toExternalForm());        
        stage.show();
    }

    private void playMusic(String title) {
        String musicFile = title;
        URL fileUrl = getClass().getResource(musicFile);
        Media media = new Media(fileUrl.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }
}
