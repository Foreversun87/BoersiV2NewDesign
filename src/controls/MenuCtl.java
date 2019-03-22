package controls;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;


public class MenuCtl implements Initializable {

    @FXML
    private BorderPane tradingIdeePane;
    @FXML
    private SplitPane transaktionPane;
    
    @FXML
    private SplitPane aktiePane;    

    @FXML
    private JFXButton btnTrading;
    @FXML
    private JFXButton btnTransaktion;
    @FXML
    private JFXButton btnDepot;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXButton btnAktie;
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Throwable {
        
        
        if (event.getSource() == btnDepot) {
            transaktionPane.setVisible(false);
            tradingIdeePane.setVisible(false);
            aktiePane.setVisible(false);
        } else if (event.getSource() == btnTrading) {
            tradingIdeePane.setVisible(true);
            transaktionPane.setVisible(false);
            aktiePane.setVisible(false);
        } else if (event.getSource() == btnTransaktion) {
            transaktionPane.setVisible(true);
            tradingIdeePane.setVisible(false);
            aktiePane.setVisible(false);
            
        } else if (event.getSource() == btnAktie) {
            aktiePane.setVisible(true);
            transaktionPane.setVisible(false);
            tradingIdeePane.setVisible(false);
            
            
        }

    }
    
  

}
