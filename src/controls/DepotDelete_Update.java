package controls;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DbVerbindung;
import model.Depot;
import services.DepotSrv;
import services.MessageSrv;

public class DepotDelete_Update implements Initializable {

    @FXML
    private TableView<Depot> tbl_Depot = new TableView<Depot>();

    @FXML
    private TableColumn<Depot, Double> colKapital;

    @FXML
    private TableColumn<Depot, Double> colGesamt;

    @FXML
    private TableColumn<Depot, Depot> colTrade;

    @FXML
    private TextField txt_kapital;

    @FXML
    private TextField txt_risikoGesamt;

    @FXML
    private TextField txt_risikoTrade;

    @FXML
    private Label lblKapital;

    @FXML
    private Label lblRisikoGesamt;

    @FXML
    private Label lblRisikoTrade;

    private DepotSrv depotSrv = new DepotSrv(DbVerbindung.getEmf());
    private Depot aktDepot = null;
    private Depot dt = null;

    private int modus = ProgModus.MODUS_ANLEGEN;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Hallo");
        // Depot-Model-Werte den Spalten zuordnen:
        colKapital.setCellValueFactory(new PropertyValueFactory<>("kapital"));
        colGesamt.setCellValueFactory(new PropertyValueFactory<>("risikogesamt"));
        colTrade.setCellValueFactory(new PropertyValueFactory<>("riskikoeinzel"));
        try {
            fuellenTab();
        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }
    }

    public void fuellenTab() throws Throwable {
        tbl_Depot.getItems().clear();
        clear();
        List<Depot> depot = depotSrv.findAlle();
        for (Depot dt : depot) {
            tbl_Depot.getItems().add(dt);
        }
    }

    private void clear() {
        txt_kapital.setText("");
        txt_risikoGesamt.setText("");
        txt_risikoTrade.setText("");
    }

    @FXML
    void onDeleteDepot(ActionEvent event) {
        try {
            if (dt == null) {
                return;
            } else {
                boolean loeschen = MessageSrv.handleLoeschen(aktDepot.getId());
                if (loeschen) {
                    depotSrv.loeschen(aktDepot.getId());
                    System.out.println("Depot gelöscht");
                    // lbl.setText("Lager " + aktDepot.getId() + " gelöscht!");
                    this.fuellenTab();
                } else {
                    System.out.println("Depot nicht nicht gelöscht");
                    // txtAusgabe.setText("Depot " + aktDepot.getId() + " nicht gelöscht!");
                }
            }
        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }
    }

    @FXML
    void onAuswahl(MouseEvent event) {
        dt = null;
        try {
            dt = (Depot) tbl_Depot.getSelectionModel().getSelectedItem();
            if (dt == null) {
                System.out.println("Depot ist null");
                return;
            } else {

                // ID holen:
                aktDepot = new Depot();
                aktDepot = depotSrv.find(dt.getId());
                txt_kapital.setText(String.valueOf(aktDepot.getKapital()));
                txt_risikoGesamt.setText(String.valueOf(aktDepot.getRisikogesamt()));
                txt_risikoTrade.setText(String.valueOf(aktDepot.getRiskikoeinzel()));
                System.out.println(dt.getId());
            }

        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }
    }

    @FXML
    void onExitDepot(ActionEvent event) {
        Stage stage = (Stage) tbl_Depot.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onUpdateDepot(ActionEvent event) {

        try {

            if (dt == null) {
                return;
            } else {
                aktDepot.setKapital(Double.valueOf(txt_kapital.getText()));
                aktDepot.setRisikogesamt(Double.valueOf(txt_risikoGesamt.getText()));
                aktDepot.setRiskikoeinzel(Double.valueOf(txt_risikoTrade.getText()));

                aktDepot = depotSrv.aendern(aktDepot);
                // txtAusgabe.setText("Depot " + aktDepot.getId() + " geändert!");

                fuellenTab();
            }
        } catch (Throwable ex) {
            MessageSrv.handleException(ex);
        }
    }
}
