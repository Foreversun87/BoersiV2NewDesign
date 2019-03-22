/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DbVerbindung;
import model.Depot;
import services.DepotSrv;
import services.MessageSrv;
import services.StageSingleton;

/**
 * FXML Controller class
 *
 * @author Ratte
 */
public class AnmeldenCtl implements Initializable {

    private Parent rootNode;
    private Stage anmeldenStage = StageSingleton.getInstance();
    private DepotSrv depotsrv = new DepotSrv(DbVerbindung.getEmf());
    public static Depot aktDepot;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
   

    private void anmeldung(String name, String passwort) throws Throwable {
        aktDepot = new Depot();
        aktDepot = depotsrv.findByUser(name);
        if (aktDepot.getPasswort().equals(passwort)) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/menu.fxml"));
            rootNode = fxmlLoader.load();
            anmeldenStage.setScene(new Scene(rootNode));
            rootNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Hello rootNode");
                }
            });
            anmeldenStage.show();
        }
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        try {
            anmeldung(username.getText(), password.getText());
        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
    }

}
