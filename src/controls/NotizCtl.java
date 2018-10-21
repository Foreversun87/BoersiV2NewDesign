/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.DbVerbindung;
import model.Transaktion;
import services.MessageSrv;
import services.TransaktionSrv;

/**
 * FXML Controller class
 *
 * @author Ratte
 */
public class NotizCtl implements Initializable {

    @FXML
    private TextArea areaText;

    private Transaktion aktTransaktion;

    private TransaktionSrv transaktionSrv = new TransaktionSrv(DbVerbindung.getEmf());
    ;
    @FXML
    private Label lblMeldung;

    public Transaktion getAktTransaktion() {
        return aktTransaktion;
    }

    public void setAktTransaktion(Transaktion aktTransaktion) {
        this.aktTransaktion = aktTransaktion;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onSave(ActionEvent event) {
        String completeString = "";
        try {
            String[] textArray = areaText.getText().split("\n");

            for (String string : textArray) {
                completeString = completeString + string + ",";
            }
            aktTransaktion.setNotiz(completeString);
            transaktionSrv.aendern(aktTransaktion);
            lblMeldung.setText("Notiz wurde erfolgreich geändert, sehr gut!");
        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }

    }

    @FXML
    private void onClose(ActionEvent event) {
        Stage stageNotiz = (Stage) areaText.getScene().getWindow();
        stageNotiz.close();
    }

    @FXML
    private void onAbout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("This is a simple text editor");
        alert.show();
    }

    public void load() {
        
        areaText.clear();
        if (aktTransaktion.getNotiz() != null) {
            String[] liste = aktTransaktion.getNotiz().split(",");
            for (String string : liste) {
                areaText.appendText(string + "\n");
            }
        }

    }

}
