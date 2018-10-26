package controls;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mainApps.DepotDelete_Update;
import model.Depot;
import mainApps.DepotApp;
import model.DbVerbindung;
import services.DepotSrv;
import services.MessageSrv;
import utils.WindowLoadAssistant;

public class MenuCtl implements Initializable {

    Stage stage;
    Stage tradingideeKonkret = new Stage();
    String test;

    private ImageView imageView;
    @FXML
    private AnchorPane paneTradingidee;
    @FXML
    private AnchorPane paneDepot;
    @FXML
    private AnchorPane paneTransaktion;
    @FXML
    private JFXButton btnTrading;
    @FXML
    private JFXButton btnTransaktion;
    @FXML
    private JFXButton btnDepot;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        System.out.println("MenuCtl setstage: " + stage.toString());
    }

    public Depot aktDepot = null;
    DepotSrv depotSrv = new DepotSrv(DbVerbindung.getEmf());

    public Depot getAktDepot() {
        return aktDepot;
    }

    public void setAktDepot(Depot aktDepot) {
        this.aktDepot = aktDepot;
    }

    void onAnlegen(ActionEvent event) throws Exception {
        System.out.println("Klick auf onAnlegen + Wert Benutzer:" + aktDepot);
        DepotApp depot = new DepotApp();
        depot.start(stage);
    }

    void onVerwalten(ActionEvent event) throws Exception {
        DepotDelete_Update ddu = new DepotDelete_Update();
        ddu.start(stage);

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

        if (event.getSource() == btnDepot) {
            paneDepot.toFront();
        } else if (event.getSource() == btnTrading) {
            
            
            Stage stage = (Stage) paneDepot.getScene().getWindow();
            //WindowLoadAssistant.loadWindow(getClass().getResource("/views/tradingIdee.fmxl"), null, null);
            

            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tradingIdee.fmxl"));

            try {
                aktDepot = depotSrv.find(aktDepot.getId());
                paneTradingidee = fxmlLoader.load();
                
                //TradingIdeeCtl tradingIdeeCtl = fxmlLoader.getController();
                //tradingIdeeCtl.setAktDepot(aktDepot);
                //tradingIdeeCtl.setMainStage(stage);
                //tradingIdeeCtl.setTest(test);
                //stage.setTitle("TradingIdee");
                stage.setScene(new Scene(paneTradingidee));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Throwable ex) {
                MessageSrv.handleException(ex);
            }
            
        } else if (event.getSource() == btnTransaktion) {
            paneTransaktion.toFront();
        }
            
    }

    /**
     * **************************************************************
     *
     * Teile für FactoryMethode
     *
     * private final HostServices hostServices;
     *
     *
     * public MenuCtl(HostServices hostServices) { this.hostServices =
     * hostServices; }
     * /***************************************************************
     */
    /*
    private void onTradingIdee(ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/tradingIdee.fxml"));
        System.out.println("Ich bin der Loader Trading von MenuCtl " + fxmlLoader.toString());
        //Teile für FactoryMethode
        //fxmlLoader.setControllerFactory(new HostServicesControllerFactory(hostServices));
        try {
            try {
                aktDepot = depotSrv.find(aktDepot.getId());
            } catch (Throwable ex) {
                MessageSrv.handleException(ex);
            }
            Parent root = (Parent) fxmlLoader.load();
            TradingIdeeCtl tradingIdeeCtl = fxmlLoader.getController();
            tradingIdeeCtl.setAktDepot(aktDepot);
            tradingIdeeCtl.setMainStage(stage);
            tradingIdeeCtl.setTest(test);
            stage.setScene(new Scene(root));
            stage.setTitle("TradingIdee");
            stage.show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     */
    private void onFile(ActionEvent event) {
        Platform.exit();
    }

    /*
    private void onTransaktion(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/TransaktionXML.fxml"));
            System.out.println("Ich bin der Loader Trading von TransaktionXML " + fxmlLoader.toString());
            Parent root = (Parent) fxmlLoader.load();
            TransaktionCtl transaktionCtl = fxmlLoader.getController();
            tradingideeKonkret.setScene(new Scene(root));
            tradingideeKonkret.setTitle("Transaktionen");
            tradingideeKonkret.show();
        } catch (IOException ex) {
            MessageSrv.handleException(ex);
        }

    }
     */
}
