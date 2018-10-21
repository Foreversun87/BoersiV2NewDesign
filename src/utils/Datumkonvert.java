package utils;

import services.PlausiException;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Datumkonvert {
    
    public static LocalDate konvertUtilDateToLocalDate(java.util.Date datum) throws Throwable { 
        if (datum == null) {
            return null;
        }
        try {            
            //LocalDate rueck = datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate rueck = Instant.ofEpochMilli(datum.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            return rueck;
        } catch (Exception e) {
            throw new PlausiException("Das Datum ist ungültig!");
        }
    }
    
    public static java.util.Date konvertLocalDateToUtilDate(LocalDate datum) throws Throwable {
        if (datum == null) {
            return null;
        }
        try {
            java.util.Date rueck = Date.from(datum.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return rueck;
        } catch (Exception e) {
            throw new PlausiException("Das Datum ist ungültig!");
        }
    }
    public static java.sql.Date konvertLocalDateToSqlDate(LocalDate datum) throws Throwable {
        if (datum == null) {
            return null;
        }
        try {
            java.util.Date util = Date.from(datum.atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date rueck = new java.sql.Date(util.getTime());
            return rueck;
        } catch (Exception e) {
            throw new PlausiException("Das Datum ist ungültig!");
        }
    }
    
     public static LocalDate konvertSqlDateToLocalDate(java.sql.Date datum) throws Throwable {        
        if (datum == null) {
            return null;
        }
        try {
            //LocalDate rueck = datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate rueck = ((java.sql.Date) datum).toLocalDate();
            return rueck;
        } catch (Exception e) {
            throw new PlausiException("Das Datum ist ungültig!");
        }
    }
    

    public static java.sql.Date erstelleSqlDatum(java.util.Date datum) throws Throwable {
        if (datum == null) {
            return null;
        }
        try {
            java.sql.Date rueck = new java.sql.Date(datum.getTime());
            return rueck;
        } catch (Exception e) {
            throw new PlausiException("Das Datum ist ungültig!");
        }
    }

    public static java.sql.Date erstelleSqlDatum(String datum) throws Throwable {
        try {
            DateFormat df = DateFormat.getDateInstance();
            df.setLenient(false);
            Date d = df.parse(datum);
            java.sql.Date rueck = new java.sql.Date(d.getTime());
            return rueck;
        } catch (Exception e) {
            throw new PlausiException("Das Datum ist ungültig!");
        }
    }

    public static java.util.Date erstelleUtilDatum(java.sql.Date datum) throws Throwable {
        if (datum == null) {
            return null;
        }
        try {
            java.util.Date rueck = new java.util.Date(datum.getTime());
            return rueck;
        } catch (Exception e) {
            throw new PlausiException("Das Datum ist ungültig!");
        }
    }

    public static Date erstelleDateDatum(String datum) throws Throwable {
        try {
            DateFormat df = DateFormat.getDateInstance();
            df.setLenient(false);
            Date rueck = df.parse(datum);
            return rueck;
        } catch (Exception e) {
            throw new PlausiException("Das Datum ist ungültig!");
        }
    }

    public static String erstelleStringDatum(Date datum) throws Throwable {
        try {
            DateFormat df = DateFormat.getDateInstance();
            df.setLenient(false);
            String rueck = df.format(datum);
            return rueck;
        } catch (Exception e) {
            throw new PlausiException("Das Datum ist ungültig!");
        }
    }

    public static String erstelleStringSqlDatum(java.sql.Date datum) throws Throwable {
        try {
            DateFormat df = DateFormat.getDateInstance();
            df.setLenient(false);
            Date d = new Date(datum.getTime());
            String rueck = df.format(d);
            return rueck;
        } catch (Exception e) {
            throw new PlausiException("Das Datum ist ungültig!");
        }
    }
}
