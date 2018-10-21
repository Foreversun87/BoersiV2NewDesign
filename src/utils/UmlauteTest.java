package utils;

import services.PlausiException;
import javafx.scene.control.Control;

public class UmlauteTest {

    public static void Tester(String pTest, Control pFeld) throws Throwable {
        if (pTest.indexOf("Ä") > -1) {
            pFeld.requestFocus();
            throw new PlausiException("Bitte Ä durch Ae ersetzen");
        }
        if (pTest.indexOf("ä") > -1) {
            pFeld.requestFocus();
            throw new PlausiException("Bitte ä durch ae ersetzen");
        }
        if (pTest.indexOf("Ö") > -1) {
            pFeld.requestFocus();
            throw new PlausiException("Bitte ö durch Oe ersetzen");
        }
        if (pTest.indexOf("ö") > -1) {
            pFeld.requestFocus();
            throw new PlausiException("Bitte ö durch oe ersetzen");
        }
        if (pTest.indexOf("Ü") > -1) {
            pFeld.requestFocus();
            throw new PlausiException("Bitte Ü durch Ue ersetzen");
        }
        if (pTest.indexOf("ü") > -1) {
            pFeld.requestFocus();
            throw new PlausiException("Bitte ü durch ue ersetzen");
        }
    }
}
