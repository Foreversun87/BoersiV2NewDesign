package controls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DbVerbindung;
import model.Depot;
import services.DepotSrv;
import services.MessageSrv;

public class DepotCreateCtl {

    @FXML
    public TextField lbl_Kontostand;

    @FXML
    public TextField lbl_Risikogesamt;

    @FXML
    public TextField lbl_RisikoEinzel;

    @FXML
    private Button btn_Create;

    @FXML
    private Button btn_Exit;

    @FXML
    private Label lbl_meldung;

    private DepotSrv depotsrv = new DepotSrv(DbVerbindung.getEmf());
    private Depot aktDepot = null;

    private int modus = ProgModus.MODUS_ANLEGEN;
    @FXML
    private TextField lbl_Benutzer;
    @FXML
    private TextField lbl_Passwort;

    @FXML
    void onCreateDepot(ActionEvent event) {

        try {
            if (lbl_Kontostand.getText().isEmpty() || lbl_Risikogesamt.getText().isEmpty()
                    || lbl_RisikoEinzel.getText().isEmpty()) {
                lbl_meldung.setText("Bitte alle Felder ausfüllen");
            } else {
                aktDepot = new Depot();
                btn_Create.setDisable(true);
                aktDepot.setKapital((Double.valueOf(lbl_Kontostand.getText())));
                aktDepot.setRisikogesamt((Double.valueOf(lbl_Risikogesamt.getText())));
                aktDepot.setRiskikoeinzel((Double.valueOf(lbl_RisikoEinzel.getText())));
                aktDepot.setUser(lbl_Benutzer.getText());
                aktDepot.setPasswort(lbl_Passwort.getText());
                depotsrv.anlegen(aktDepot);
                lbl_meldung.setText("Depot " + aktDepot.getId() + " gespeichert!");
                maskeClear();
                btn_Create.setDisable(false);
            }

        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
            btn_Create.setDisable(false);
        }
    }

    public void maskeClear() throws Exception {
        lbl_Kontostand.setText("");
        lbl_Risikogesamt.setText("");
        lbl_RisikoEinzel.setText("");
        lbl_Benutzer.setText("");
        lbl_Passwort.setText("");
    }

    @FXML
    void onExitDepot(ActionEvent event) {
        Stage stage = (Stage) lbl_Benutzer.getScene().getWindow();
        stage.close();
    }

}
