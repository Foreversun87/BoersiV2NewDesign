/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import static controls.AnmeldenCtl.aktDepot;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import model.Aktie;
import model.DbVerbindung;
import model.Tradingidee;
import services.AktieSrv;
import services.MessageSrv;
import services.TradingIdeeSrv;

import utils.Datumkonvert;

/**
 * FXML Controller class
 *
 * @author Ratte
 */
public class TradingIdeeCtl implements Initializable {

    @FXML
    private JFXTextField txtKurs_WKN;
    @FXML
    private JFXTextField txtAktKurs_Links;
    @FXML
    private JFXTextField txtKursZiel;
    @FXML
    private JFXTextField txtStoppLoss;
    @FXML
    private Button btnGuV;
    @FXML
    private JFXTextField txtStueckzahl;
    @FXML
    private JFXTextField txtGuVVerhaeltnis;
    @FXML
    private JFXDatePicker dp_oco;
    
    @FXML
    private ToggleGroup RadioStar;
    @FXML
    private Button bntAbschicken;

    private double stueckzahl;
    private double guV;

    private TradingIdeeSrv tradingIdeeSrv = new TradingIdeeSrv(DbVerbindung.getEmf());
    private AktieSrv aktieSrv = new AktieSrv(DbVerbindung.getEmf());
    private Aktie aktAktie;
    private Tradingidee aktTradingIdee;
    private TransaktionCtl transaktionCtl = new TransaktionCtl();
    @FXML
    private JFXRadioButton rbKonkreterVorschlag;
    @FXML
    private JFXRadioButton rbVorschlag;
    @FXML
    private Label lblMeldung;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("Initial TradingIdee");

    }

    @FXML
    private void onBerechnen(ActionEvent event) throws Throwable {
        System.out.println("In der Methode onBerechnen!");
        

        //Variablen
        double kapital = AnmeldenCtl.aktDepot.getKapital();
        double oGrenze = Double.valueOf(txtKursZiel.getText());
        double uGrenze = Double.valueOf(txtStoppLoss.getText());
        double kursIstWert = Double.valueOf(txtAktKurs_Links.getText());
        double risikoGesamtProzent = Double.valueOf(AnmeldenCtl.aktDepot.getRisikogesamt());
        double risikoEinzelProzent = Double.valueOf(AnmeldenCtl.aktDepot.getRiskikoeinzel());
        double risikoEinzelWert = kapital * risikoEinzelProzent / 100;
        double oGrenzeProzent = (oGrenze - kursIstWert) / kursIstWert * 100;
        double uGrenzeProzent = -(uGrenze - kursIstWert) / kursIstWert * 100;
        double position = risikoEinzelWert / uGrenzeProzent * 100;

        //Berechnungen Gewinnverlust-Verhältnis und hinzufügen
        guV = (oGrenze - kursIstWert) / (kursIstWert - uGrenze);
        txtGuVVerhaeltnis.setText(String.valueOf(guV));

        //Berechnen Stückzahl und hinzufügen
        stueckzahl = position / kursIstWert;

        txtStueckzahl.setText(String.valueOf(stueckzahl));

    }

    @FXML
    private void onAbschicken(ActionEvent event) {
        if (txtKurs_WKN.getText().isEmpty()) {
            lblMeldung.setText("Bitte eine Aktie eingeben");

        } else if (txtAktKurs_Links.getText().isEmpty()) {
            lblMeldung.setText("Bitte einen Aktienkurs eingeben");
        } else if (txtStoppLoss.getText().isEmpty()) {
            lblMeldung.setText("Bitte ein StoppLos eingeben");
        } else if (txtKursZiel.getText().isEmpty()) {
            lblMeldung.setText("Bitte ein KursZiel eingeben");
        } else {
            try {
                /*
                 *Erstellen der TradingIdee, durch ermittlung ob Vorschlag oder Konkrete Tradingidee
                 */

                if (rbKonkreterVorschlag.isSelected()) {
                    Date dp_date = Datumkonvert.konvertLocalDateToUtilDate(dp_oco.getValue());

                    if (stueckzahl <= 0) {
                        lblMeldung.setText("Bitte auf den Button Berechnen klicken und prüfen ob Werte sinnvoll sind!");
                    } else if (dp_oco.getValue() == null) {
                        lblMeldung.setText("Bitte ein OCO-Datum eingeben!");
                    } else if (dp_date.before(new Date())) {
                        lblMeldung.setText("Bitte ein OCO-Datum das nach dem aktuellen Datum ist!");
                    } else {
                        /*
                         *Weil TradingIdee konkret, erstellen der zugehörigen Transaktion
                         */
                        Double stueckzahl = Double.valueOf(txtStueckzahl.getText());
                        aktTradingIdee = createTradingIdee();
                        transaktionCtl.createTransaktion(aktTradingIdee, aktAktie, dp_oco, stueckzahl);
                        transaktionCtl.fuellenTab();                       
                        lblMeldung.setText("Tradingidee:" + " " + aktTradingIdee.getId() + " angelegt!");
                        maskeClear();
                    }
                } else {
                    //Tradingidee als Vorschlag
                    ProgModus.IS_ANGELEGT = false;
                    ProgModus.IS_COMPLETED = false;
                    aktTradingIdee = createTradingIdee();
                    lblMeldung.setText("Tradingidee:" + " " + aktTradingIdee.getId() + " angelegt!");
                    maskeClear();
                }

            } catch (Throwable ex) {
                MessageSrv.handleException(ex);
            }
        }
    }

    private Tradingidee createTradingIdee() throws Throwable {
        aktTradingIdee = new Tradingidee();
        aktAktie = aktieSrv.findByBezeichnung(txtKurs_WKN.getText());
        aktTradingIdee.setAktKurs((Double.valueOf(txtAktKurs_Links.getText())));
        aktTradingIdee.setAktienId(aktAktie);
        aktTradingIdee.setDepotID(aktDepot);
        aktTradingIdee.setId(bestimmungAktieId());
        aktTradingIdee.setKursStoppnegativ((Double.valueOf(txtStoppLoss.getText())));
        aktTradingIdee.setKursStopppositiv((Double.valueOf(txtKursZiel.getText())));
        aktTradingIdee.setErstellungsDatum(new Date());
        if (rbKonkreterVorschlag.isSelected()) {
            aktTradingIdee.setIsVorschlag(false);
        } else {
            aktTradingIdee.setIsVorschlag(true);
        }
        tradingIdeeSrv.anlegen(aktTradingIdee);
        return aktTradingIdee;
    }

    public String bestimmungAktieId() throws Throwable {

        aktAktie = aktieSrv.findByBezeichnung(txtKurs_WKN.getText());
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
    
    private void maskeClear() {
        txtAktKurs_Links.setText("");
        txtKursZiel.setText("");
        txtStoppLoss.setText("");
        txtKurs_WKN.setText("");
        txtGuVVerhaeltnis.setText("");
        txtStueckzahl.setText("");
        dp_oco.setValue(null);
        stueckzahl = 0;
        guV = 0;

    }

}
