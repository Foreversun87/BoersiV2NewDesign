/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Aktie;
import model.DbVerbindung;
import model.Depot;
import model.Tradingidee;
import model.Transaktion;
import services.AktieSrv;
import services.MessageSrv;
import services.TradingIdeeSrv;
import services.TransaktionSrv;

/**
 * FXML Controller class
 *
 * @author Ratte
 */
public class TradingIdeeCtl implements Initializable {

    @FXML
    private TextField txtKurs_WKN;
    @FXML
    private Button btnKurs_WKN;
    @FXML
    private TextField txtKursZiel;
    @FXML
    private TextField txtStoppLoss;
    @FXML
    private TextField txtAktKurs_Links;
    @FXML
    private Label txtGuVVerhaeltnis;
    @FXML
    private Button btnGuV;
    @FXML
    private Button btnUebersicht;
    @FXML
    private LineChart<?, ?> graphKurs;
    @FXML
    private Label txtTagesHoch;
    @FXML
    private Label txtTagesTief;
    @FXML
    private Label txtAktKurs_Rechts;
    @FXML
    private Label txtAktuellerKontostand;
    @FXML
    private Label txtStueckzahl;
    @FXML
    private Button btnBroker;

    String test = null;
    @FXML
    private RadioButton rbKonkreter;
    @FXML
    private ToggleGroup RadioStar;
    @FXML
    private RadioButton rbVorschlag;
    @FXML
    private Button bntAbschicken;
    @FXML
    private Label lblMeldung;
    @FXML
    private DatePicker dp_oco;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    private Depot aktDepot;
    public Stage mainStage;
    private Stage tradingideeKonkret = new Stage();

    private TradingIdeeSrv tradingIdeeSrv = new TradingIdeeSrv(DbVerbindung.getEmf());
    private Tradingidee aktTradingIdee;

    private AktieSrv aktieSrv = new AktieSrv(DbVerbindung.getEmf());
    private Aktie aktAktie;

    private TransaktionSrv transaktionSrv = new TransaktionSrv(DbVerbindung.getEmf());
    private Transaktion aktTransaktion;

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public HostServices getHostService() {
        return hostService;
    }

    public void setHostService(HostServices hostService) {
        this.hostService = hostService;
    }
    HostServices hostService;

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/TransaktionXML.fxml"));
            System.out.println("Ich bin der Loader Trading von TransaktionXML " + fxmlLoader.toString());
            Parent root = (Parent) fxmlLoader.load();
            TransaktionCtl transaktionCtl = fxmlLoader.getController();
            tradingideeKonkret.setScene(new Scene(root));
            tradingideeKonkret.setTitle("Transaktionen");
        } catch (IOException ex) {
            MessageSrv.handleException(ex);
        }

    }

    public void setAktDepot(Depot depot) {
        aktDepot = depot;
        txtAktuellerKontostand.setText(String.valueOf(aktDepot.getKapital()));
    }

    @FXML
    private void onKurs_WKN(ActionEvent event) {
        System.out.println("Kapital des aktDepots: " + aktDepot.getKapital());

    }

    @FXML
    private void onUebersicht(ActionEvent event) {
        tradingideeKonkret.show();
    }

    @FXML
    private void onBerechnen(ActionEvent event) {
        //Felder leeren
        lblMeldung.setText("");

        //Überprüfungen
        if (txtAktKurs_Links.getText().isEmpty()) {
            lblMeldung.setText("UPPS!");
        } else if (txtKursZiel.getText().isEmpty()) {
            lblMeldung.setText("UPPS!");
        } else if (txtStoppLoss.getText().isEmpty()) {
            lblMeldung.setText("UPPS!");
        } else {

            //Variablen
            double kapital = aktDepot.getKapital();
            double oGrenze = Double.valueOf(txtKursZiel.getText());
            double uGrenze = Double.valueOf(txtStoppLoss.getText());
            double kursIstWert = Double.valueOf(txtAktKurs_Links.getText());
            double risikoGesamtProzent = Double.valueOf(aktDepot.getRisikogesamt());
            double risikoEinzelProzent = Double.valueOf(aktDepot.getRiskikoeinzel());
            double risikoEinzelWert = kapital * risikoEinzelProzent / 100;
            double oGrenzeProzent = (oGrenze - kursIstWert) / kursIstWert * 100;
            double uGrenzeProzent = -(uGrenze - kursIstWert) / kursIstWert * 100;
            double position = risikoEinzelWert / uGrenzeProzent * 100;
            double guV = 0;

            //Berechnungen Gewinnverlust-Verhältnis und hinzufügen
            guV = (oGrenze - kursIstWert) / (kursIstWert - uGrenze);
            txtGuVVerhaeltnis.setText(String.valueOf(guV));

            //Berechnen Stückzahl und hinzufügen
            double stueckzahl = position / kursIstWert;

            txtStueckzahl.setText(String.valueOf(stueckzahl));
        }
    }

    public void startProgramm(String path) {
        String cmd = path;
        try {

            Thread.sleep(150);
            Process process = Runtime.getRuntime().exec(cmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBroker(ActionEvent event) {
        System.out.println(test);
        HostServices hostServices = (HostServices) mainStage.getProperties().get("hostServices");
        hostServices.showDocument("https://webtrading.onvista-bank.de/login");
    }


    public String bestimmungAktieId() throws Throwable {

        aktAktie = aktAktie();
        /*
         * Achtung bei Neuerstellen der Models, muss manuell hinzugefügt werden im Model Tradingidee:
         * , @NamedQuery(name = "Tradingidee.findByAktienId", query = "SELECT t FROM Tradingidee t WHERE t.aktienId = :aktienId")
         */
        List<Tradingidee> wert = tradingIdeeSrv.findByAktieId(aktAktie);
        int maxId = 0;
        if (wert.size() > 0) {
            for (Tradingidee tradingidee : wert) {
                int id = Integer.valueOf(tradingidee.getId().split("_")[1]);
                if (id > maxId) {
                    maxId = id;
                }
            }
            maxId++;
        }

        String bezAktie = aktAktie.getBez();
        String idAktie = bezAktie + "_" + maxId;

        if (idAktie.length() > 15) {
            while (idAktie.length() > 15) {
                bezAktie = bezAktie.substring(0, bezAktie.length() - 1);
                idAktie = bezAktie + "_" + maxId;
            }
        }
        return idAktie;
    }

    @FXML
    private void onAbschicken(ActionEvent event) {
        aktTradingIdee = new Tradingidee();

        if (txtKurs_WKN.getText().isEmpty()) {
            lblMeldung.setText("Bitte eine Aktie eingeben");
        } else {
            try {

                /*
                 *Erstellen der TradingIdee
                 */
                aktAktie = aktAktie();
                aktTradingIdee.setAktKurs((Double.valueOf(txtAktKurs_Links.getText())));
                aktTradingIdee.setAktienId(aktAktie);
                aktTradingIdee.setDepotID(aktDepot);
                aktTradingIdee.setId(bestimmungAktieId());
                aktTradingIdee.setKursStoppnegativ((Double.valueOf(txtStoppLoss.getText())));
                aktTradingIdee.setKursStopppositiv((Double.valueOf(txtKursZiel.getText())));
                tradingIdeeSrv.anlegen(aktTradingIdee);
                aktTradingIdee = tradingIdeeSrv.find(aktTradingIdee.getId());

                /*
                 *Erstellen der Transaktion
                 */
                aktTransaktion = new Transaktion();
                aktTransaktion.setTradingId(aktTradingIdee);
                aktTransaktion.setAktieId(aktAktie);

                //Ermittlung Stückzahl
                aktTransaktion.setStueckzahl(Double.valueOf(txtStueckzahl.getText()));
                //Generierung Notiz
                aktTransaktion.setNotiz(createNotiz(aktTradingIdee));

                if (rbKonkreter.isSelected()) {
                    aktTransaktion.setKaufdatum(new Date());
                    //Dieser boolean ermittelt ob die Tradingidee konkret oder eben nicht ist
                    ProgModus.IS_SELECTED_RB = true;
                    if (dp_oco.getValue() != null) {
                        aktTransaktion.setOcoDatum(java.sql.Date.valueOf(dp_oco.getValue()));
                    }
                } else {
                    //Tradingidee nicht konkret
                    ProgModus.IS_SELECTED_RB = false;
                }

                transaktionSrv.anlegen(aktTransaktion);
                maskeClear();

                //Weiterleitung an Transaktionsmaske!!
                tradingideeKonkret.show();

            } catch (Throwable ex) {
                MessageSrv.handleException(ex);
            }
        }
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

    private void maskeClear() {
        txtAktKurs_Links.setText("");
        txtKursZiel.setText("");
        txtStoppLoss.setText("");
        txtKurs_WKN.setText("");
        txtGuVVerhaeltnis.setText("");
        txtStueckzahl.setText("");
    }

    private Aktie aktAktie() {
        aktAktie = null;
        try {
            aktAktie = aktieSrv.findByBezeichnung(txtKurs_WKN.getText());
        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }
        return aktAktie;
    }
}


 /**
     * **************************************************************
     * // Teile für FactoryMethode private Hyperlink hyperlink;
     *
     * private final HostServices hostServices;
     *
     * public TradingIdeeCtl(HostServices hostServices) { this.hostServices =
     * hostServices; }
     *
     *
     * @FXML private void openURL() {
     * hostServices.showDocument(hyperlink.getText()); }
     */
    //****************************************************************

//*****************************************************************************************************************************************
// Hostservice einrichten:
// Hier zum Nachlesen: https://stackoverflow.com/questions/33094981/javafx-8-open-a-link-in-a-browser-without-reference-to-application?rq=1
// Methode funktioniert nicht
// getStage gehört auch noch dazu.
//*****************************************************************************************************************************************
/* Ist für die Methode startProgramm
String path = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
startProgramm(path);
     */