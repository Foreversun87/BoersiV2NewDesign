/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private DepotSrv depotsrv = new DepotSrv(DbVerbindung.getEmf());
    public static Depot aktDepot = null;
    public Stage stage = null;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Depot getAktDepot() {
        return aktDepot;
    }

    public void setAktDepot(Depot aktDepot) {
        this.aktDepot = aktDepot;
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

    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        anmeldung(username.getText(), password.getText());
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

}
