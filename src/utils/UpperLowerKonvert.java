package utils;

public class UpperLowerKonvert {

    public UpperLowerKonvert() {

    }

    public static String String_Konvert(String name) {

        String wert = "";
        if (name.trim().length() == 0) {
            name = "";
        } else {

            String erster = name.substring(0, 1).toUpperCase();
            String andere = name.substring(1, name.length()).toLowerCase();
            wert = erster.concat(andere);

        }

        return wert;
    }

}
