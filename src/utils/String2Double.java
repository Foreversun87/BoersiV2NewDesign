package utils;
import services.PlausiException;

public class String2Double {

    // Integer String Reinwerfen, wenn string " " bekommt man 0 sonst den Passenden int
    public static double StringToDouble(String str)throws Throwable{
        try{
            return Double.valueOf(str.trim().equals("") ? "0":str);
        }catch(Throwable ex){
            throw new PlausiException("Konvertierungsfehler: Einer der Kommawerte ist alphanumerisch");
        }
    }
    
    // Gleiches Spiel andersherum
    public static String DoubleToString(double i)throws Throwable{
        return (i == 0 ? " " : String.valueOf(i));
    }
}
