/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.DbVerbindung;
import model.Depot;
import services.DepotSrv;
import services.MessageSrv;
/**
 * FXML Controller class
 *
 * @author Ratte
 */
public class AnmeldenCtl implements Initializable {

    @FXML
    private TextField lbl_Benutzer;
    @FXML
    private TextField lbl_Passwort;

    private DepotSrv depotsrv = new DepotSrv(DbVerbindung.getEmf());
    public static Depot aktDepot = null;
    public Stage stage = null;
    @FXML
    private SplitPane mainSplit;
    @FXML
    private ImageView imageView;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void anmeldung(String name, String passwort) {

        try {
            aktDepot = depotsrv.findByUser(name);
            if (aktDepot.getPasswort().equals(passwort)) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../views/menu.fxml"));

                FXMLLoader fxmlLoaderTrading = new FXMLLoader(getClass().getResource("../views/tradingIdee.fxml"));

                System.out.println("Ich bin der Loader Trading von AnmeldenCtl " + fxmlLoaderTrading.toString());
                try {
                    fxmlLoader.load();
                    fxmlLoaderTrading.load();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                MenuCtl menuCtl = fxmlLoader.getController();

                menuCtl.setAktDepot(aktDepot);
                menuCtl.setStage(stage);
                menuCtl.setTest("Hallo");

                /*
                TradingIdeeCtl tradingCtl = fxmlLoaderTrading.getController();
                tradingCtl.setTest("Hallo");
                tradingCtl.setMainStage(stage);   
                 */
                System.out.println(menuCtl.getAktDepot());

                Parent root = fxmlLoader.getRoot();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Fehler bei Anmeldung");
            }

        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Image image = new Image(getClass().getResourceAsStream("../bilder/GuentherJauch.png"));
        //imageView.setImage(image);
    }

    @FXML
    private void onEnteredBenutzer(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            anmeldung(lbl_Benutzer.getText(), lbl_Passwort.getText());
        } else {
            System.out.println("Kein Enter gedrückt!");
        }
    }

    public Depot getAktDepot() {
        return aktDepot;
    }

    public void setAktDepot(Depot aktDepot) {
        this.aktDepot = aktDepot;
    }

    @FXML
    private void onEnteredPasswort(KeyEvent event) {
        System.out.println("Passwort");
    }

}
