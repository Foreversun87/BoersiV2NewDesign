package utils;

import java.sql.Time;

public class Zeitkonvert {

    public static Time erstelleAktuelleSqlZeit() throws Throwable {
        java.util.Date datum = new java.util.Date();
        java.sql.Time zeit = new java.sql.Time(datum.getTime());
        return zeit;
    }
}
