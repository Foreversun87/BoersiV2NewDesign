package services;

import java.io.PrintWriter;
import java.io.StringWriter;
import services.PlausiException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class MessageSrv {

    public static void handleException(java.lang.Throwable exception) {

        String fehler = "";
        String fehlerArt = "";

        if (exception instanceof PlausiException) {
            PlausiException ple = (PlausiException) exception;
            fehlerArt = "Anwenderfehler";
            if (ple.getMessages() != null) {
                ArrayList<String> meldungen = (ArrayList<String>) ple.getMessages();

                for (int i = 0; i < meldungen.size(); i++) {
                    fehler += meldungen.get(i) + "\n";
                }

            } else {
                fehler = ple.getMessage();
            }

            Control con = ple.getControl();
            if (con != null) {
                con.requestFocus();
            }
        } else {
            fehler = exception.getMessage();
            fehlerArt = "Datenbankfehler";
        }

        // Anzeigen Fehler:
        Alert alert = new Alert(Alert.AlertType.ERROR);
        //alert.initOwner(dialogStage);
        alert.setTitle(fehlerArt);
        if (fehlerArt.equals("Anwenderfehler")) {
            alert.setHeaderText("Folgende Fehler bitte korrigieren:");
        } else {
            alert.setHeaderText("Datenbank- bzw. Systemfehler:");
        }
        alert.setContentText(fehler);
        alert.getDialogPane().setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Exception-Stacktrace:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
	   
        alert.showAndWait();
    }
    
    public static boolean handleLoeschen(Object obj) {
        boolean loeschen = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Objekt löeschen");
        alert.setHeaderText("Objekt der Klasse: " + obj.getClass().getName());
        alert.setContentText("Möchten Sie das Objekt wirklich löschen?");
        alert.getDialogPane().setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            loeschen = true;
        } 
        alert.close();
        return  loeschen;
    }
}
