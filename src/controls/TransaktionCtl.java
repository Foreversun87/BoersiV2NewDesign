/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Aktie;
import model.DbVerbindung;
import model.Tradingidee;
import model.Transaktion;
import model.TransaktionsTabellenModel;
import services.MessageSrv;
import services.TransaktionSrv;

/**
 * FXML Controller class
 *
 * @author Ratte
 */
public class TransaktionCtl implements Initializable {

    @FXML
    private TableView<TransaktionsTabellenModel> tblTransaktion;
    @FXML
    private TableColumn<TransaktionsTabellenModel, String> colAktie;
    @FXML
    private TableColumn<TransaktionsTabellenModel, String> colStueckzahl;
    @FXML
    private TableColumn<TransaktionsTabellenModel, String> colVerkaufKurs;
    @FXML
    private TableColumn<TransaktionsTabellenModel, String> colVerkaufDatum;
    @FXML
    private TableColumn<TransaktionsTabellenModel, String> colKaufKurs;
    @FXML
    private TableColumn<TransaktionsTabellenModel, String> colKaufDatum;
    @FXML
    private TextField txtStueckzahl;
    @FXML
    private TextField txtKursVerkauf;
    @FXML
    private TextField txtVerkaufDatum;
    private TextField txtKursKauf;
    @FXML
    private TextField txtKaufDatum;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnView;
    @FXML
    private Button btnExit;
    @FXML
    private Label lblMeldung;
    @FXML
    private TableColumn<TransaktionsTabellenModel, String> colOCODate;
    @FXML
    private TextField txtOCODate;

    private TransaktionSrv transaktionSrv = new TransaktionSrv(DbVerbindung.getEmf());

    private Transaktion aktTransaktion;
    @FXML
    private SplitPane stageTransaktions;

    public Transaktion getAktTransaktion() {
        return aktTransaktion;
    }

    public void setAktTransaktion(Transaktion aktTransaktion) {
        this.aktTransaktion = aktTransaktion;
    }

    private TransaktionsTabellenModel dt;

    String pattern = "yyyy-MM-dd";
    //DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
    SimpleDateFormat df = new SimpleDateFormat(pattern);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Depot-Model-Werte den Spalten zuordnen:
        colAktie.setCellValueFactory(new PropertyValueFactory<>("aktie"));
        colKaufDatum.setCellValueFactory(new PropertyValueFactory<>("kaufdatum"));
        colKaufKurs.setCellValueFactory(new PropertyValueFactory<>("kursKauf"));
        colOCODate.setCellValueFactory(new PropertyValueFactory<>("ocoDatum"));
        colStueckzahl.setCellValueFactory(new PropertyValueFactory<>("stueckzahl"));
        colVerkaufDatum.setCellValueFactory(new PropertyValueFactory<>("verkaufsdatum"));
        colVerkaufKurs.setCellValueFactory(new PropertyValueFactory<>("kursVerkauf"));

        try {
            fuellenTab();
        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }

        btnView.setVisible(false);
    }

    public void fuellenTab() throws Throwable {
        tblTransaktion.getItems().clear();
        List<Transaktion> transaktionsList = transaktionSrv.findAlle();

        for (Transaktion t : transaktionsList) {

            TransaktionsTabellenModel tt = new TransaktionsTabellenModel();
            tt.setId(String.valueOf(t.getId()));
            tt.setAktie(t.getAktieId().getBez());
            tt.setStueckzahl(String.valueOf(t.getStueckzahl()));
            if (t.getOcoDatum() != null) {
                tt.setOcoDatum(df.format(t.getOcoDatum()));
            }
            tt.setKursVerkauf(String.valueOf(t.getKursVerkauf()));
            if (t.getVerkaufdatum() != null) {
                tt.setVerkaufsdatum(df.format(t.getVerkaufdatum()));
            }
            tt.setKursKauf(String.valueOf(t.getTradingId().getAktKurs()));
            if (t.getKaufdatum() != null) {
                tt.setKaufdatum(df.format(t.getKaufdatum()));
            }
            tblTransaktion.getItems().add(tt);
        }
    }

    private void clear() {
        txtKaufDatum.setText("");
        txtKursVerkauf.setText("");
        txtOCODate.setText("");
        txtStueckzahl.setText("");
        txtVerkaufDatum.setText("");
    }

    @FXML
    private void onAuswahl(MouseEvent event) {

        btnView.setVisible(true);
        dt = null;
        try {

            dt = (TransaktionsTabellenModel) tblTransaktion.getSelectionModel().getSelectedItem();
            if (dt == null) {
                btnView.setVisible(false);
                return;
            } else {
                // Transaktion in Datenbank unter Verwendung ID holen:
                aktTransaktion = new Transaktion();
                aktTransaktion = transaktionSrv.find(Integer.valueOf(dt.getId()));

                if (aktTransaktion.getKaufdatum() != null) {
                    txtKaufDatum.setText(df.format(aktTransaktion.getKaufdatum()));
                } else {
                    txtKaufDatum.setText("");
                }

                if (aktTransaktion.getKursVerkauf() != null) {
                    txtKursVerkauf.setText(String.valueOf(aktTransaktion.getKursVerkauf()));
                } else {
                    txtKursVerkauf.setText("");
                }

                if (aktTransaktion.getOcoDatum() != null) {
                    txtOCODate.setText(df.format(aktTransaktion.getOcoDatum()));
                } else {
                    txtOCODate.setText("");
                }

                if (aktTransaktion.getStueckzahl() != null) {
                    txtStueckzahl.setText(String.valueOf(aktTransaktion.getStueckzahl()));
                } else {
                    txtStueckzahl.setText("");
                }

                if (aktTransaktion.getVerkaufdatum() != null) {
                    txtVerkaufDatum.setText(df.format(aktTransaktion.getVerkaufdatum()));
                } else {
                    txtVerkaufDatum.setText("");
                }

                System.out.println(dt.getId());
            }

        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }

    }

    @FXML
    private void onDeleteDepot(ActionEvent event) {
        try {
            if (dt == null) {
                return;
            } else {
                boolean loeschen = MessageSrv.handleLoeschen(aktTransaktion.getId());
                if (loeschen) {
                    transaktionSrv.loeschen(aktTransaktion.getId());
                    System.out.println("Transaktion gelöscht");
                    // lbl.setText("Lager " + aktDepot.getId() + " gelöscht!");
                    fuellenTab();
                    clear();
                } else {
                    System.out.println("Transaktion nicht gelöscht");
                    // txtAusgabe.setText("Depot " + aktDepot.getId() + " nicht gelöscht!");
                }
            }
        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }
    }

    @FXML
    private void onUpdateDepot(ActionEvent event) {

        try {
            if (dt == null) {
                return;
            } else {

                if (!txtKaufDatum.getText().equals("")) {
                    aktTransaktion.setKaufdatum(df.parse(txtKaufDatum.getText()));
                } else {
                    aktTransaktion.setKaufdatum(null);
                }
                if (!txtKursVerkauf.getText().equals("")) {
                    aktTransaktion.setKursVerkauf(Double.valueOf(txtKursVerkauf.getText()));

                } else {
                    aktTransaktion.setKursVerkauf(null);
                }
                if (!txtOCODate.getText().equals("")) {
                    aktTransaktion.setOcoDatum(df.parse(txtOCODate.getText()));
                } else {
                    aktTransaktion.setOcoDatum(null);
                }

                if (!txtStueckzahl.getText().equals("")) {
                    aktTransaktion.setStueckzahl(Double.valueOf(txtStueckzahl.getText()));
                } else {
                    aktTransaktion.setStueckzahl(null);
                }

                if (!txtVerkaufDatum.getText().equals("")) {
                    aktTransaktion.setVerkaufdatum(df.parse(txtVerkaufDatum.getText()));

                } else {
                    aktTransaktion.setVerkaufdatum(null);
                }

                if (!txtVerkaufDatum.getText().equals("") || !txtKursVerkauf.getText().equals("")) {
                    ProgModus.IS_COMPLETED = true;
                } else {
                    ProgModus.IS_COMPLETED = false;
                }
                aktTransaktion = transaktionSrv.aendern(aktTransaktion);
                // txtAusgabe.setText("Depot " + aktDepot.getId() + " geändert!");
                fuellenTab();
                clear();
            }
        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }

    }

    @FXML
    private void onView(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/NotizXML.fxml"));
            System.out.println("Ich bin der Loader Trading von NotizXML " + fxmlLoader.toString());
            Parent root = (Parent) fxmlLoader.load();
            NotizCtl notizCtl = fxmlLoader.getController();
            notizCtl.setAktTransaktion(aktTransaktion);
            notizCtl.load();
            Scene sceneNotiz = new Scene(root);
            Stage stageNotiz = new Stage();
            stageNotiz.setScene(sceneNotiz);
            stageNotiz.setTitle("Notiz verwalten");
            stageNotiz.show();
        } catch (IOException ex) {
            MessageSrv.handleException(ex);
        }
    }

    @FXML
    private void onExitDepot(ActionEvent event) {
        Stage stage = (Stage) lblMeldung.getScene().getWindow();
        stage.close();

    }

    public void createTransaktion(Tradingidee aktTradingIdee, Aktie aktAktie, DatePicker dp_oco, Double stueckzahl) throws Throwable {

        //Erstellen der Transaktion
        aktTransaktion = new Transaktion();
        aktTransaktion.setTradingId(aktTradingIdee);
        aktTransaktion.setAktieId(aktAktie);

        //Ermittlung Stückzahl
        aktTransaktion.setStueckzahl(stueckzahl);
        //Generierung Notiz
        aktTransaktion.setNotiz(createNotiz(aktTradingIdee));

        if (!aktTradingIdee.isIsVorschlag()) {
            aktTransaktion.setKaufdatum(new Date());
            if (dp_oco.getValue() != null) {
                aktTransaktion.setOcoDatum(java.sql.Date.valueOf(dp_oco.getValue()));
            }
        }
        transaktionSrv.anlegen(aktTransaktion);

    }

    /*
    Erstellung der Notiz    
     */
    private String createNotiz(Tradingidee aktTradingidee) {
        String notiz = "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *" + ","
                + "* Depot-Eigentümer: " + aktTradingidee.getDepotID().getUser()
                + " Depot-Wert: " + aktTradingidee.getDepotID().getKapital() + " Risiko pro Trade: " + aktTradingidee.getDepotID().getRiskikoeinzel() + "*" + ","
                + "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *" + ","
                + "* Gehandelte Aktie: " + aktTradingidee.getAktienId().getBez() + "                                                                      *" + ","
                + "* Geplanter Zielkurs: " + aktTradingidee.getKursStopppositiv() + " geplanter Stopplosskurs: " + aktTradingidee.getKursStoppnegativ() + "                         *" + ","
                + "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *" + ",";

        return notiz;
    }

}
