package utils;

/**
 *
 * @author matthias
 */
public enum Bestellstatus {
    OFFEN("Offen"), BESTAETIGT("Bestätigt"), WARENEINGANG("Ware eingegangen"), BEZAHLT("Bezahlt");

    private String text;

    private Bestellstatus(String pText) {
        this.text = pText;
    }

    public String getText() {
        return text;
    }

    public static Bestellstatus getStatus(String text) {
        Bestellstatus ret = null;
        for (Bestellstatus bs : Bestellstatus.values()) {
            if (bs.getText().equals(text)) {
                ret = bs;
            }
        }
        return ret;
    }

}
