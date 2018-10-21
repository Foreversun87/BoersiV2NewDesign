package utils;

import services.PlausiException;
import java.math.BigDecimal;
import javafx.scene.control.Control;

public class ZahlConverter {

    public static int ConvertToInt(String txtFeld, Control comp) throws Throwable {
        int return_wert = 0;

        try {
            if (txtFeld.length() == 0) {
                return_wert = 0;
            } else {
                return_wert = Integer.parseInt(txtFeld);
            }
        } catch (Throwable e) {
            throw new PlausiException(txtFeld + " muss eine Zahl sein.", comp);
        }
        return return_wert;
    }

    public static double ConvertToDouble(String txtFeld, Control comp) throws Throwable {
        double return_wert = 0;

        try {
            if (txtFeld.length() == 0) {
                return_wert = 0;
            } else {
                return_wert = Double.parseDouble(txtFeld);
            }
        } catch (Throwable e) {
            throw new PlausiException(txtFeld + " muss eine Zahl sein.", comp);
        }
        return return_wert;
    }

    public static double Round_Kaufm(double zuRundenderBetrag) {
        BigDecimal decimal = new BigDecimal(zuRundenderBetrag);
        return (decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
    }
}
