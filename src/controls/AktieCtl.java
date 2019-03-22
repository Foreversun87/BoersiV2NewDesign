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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Aktie;
import model.DbVerbindung;
import services.AktieSrv;
import services.MessageSrv;

/**
 * FXML Controller class
 *
 * @author Ratte
 */
public class AktieCtl implements Initializable {

    @FXML
    private TextField lbl_WKN;
    @FXML
    private TextField lbl_Bezeichnung;
    private Button btn_Create;
    @FXML
    private Label lbl_meldung;

    private AktieSrv aktieSrv = new AktieSrv(DbVerbindung.getEmf());
    @FXML
    private Button btnCreate;
   
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onCreateDepot(ActionEvent event) {
        try {
            
                Aktie aktAktie = new Aktie();                
                aktAktie.setBez((lbl_Bezeichnung.getText()));
                aktAktie.setWkn((lbl_WKN.getText()));
                aktieSrv.anlegen(aktAktie);
                lbl_meldung.setText("Aktie " + aktAktie.getBez()+ " angelegt!");
                maskeClear();
                
            

        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
            btn_Create.setDisable(false);
            
        }
    }

    public void maskeClear() throws Exception {
        lbl_Bezeichnung.setText("");
        lbl_WKN.setText("");
        
    }

    

}
