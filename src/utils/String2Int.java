/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import services.PlausiException;
/**
 *
 * @author Simon Brummer
 */
public class String2Int {

    // Integer String Reinwerfen, wenn string " " bekommt man 0 sonst den Passenden int
    public static int StringToInt(String str)throws Throwable{
        try{
            return Integer.valueOf(str.trim().equals("") ? "0":str);
        }catch(Throwable ex){
            throw new PlausiException("Konvertierungsfehler: Einer der Werte ist alphanumerisch");
        }
    }
    // Gleiches Spiel andersherum
    public static String IntToString(int i){
        return (i == 0 ? " " : String.valueOf(i));
    }
    public static int CboToInt(JComboBox cbo)throws Throwable{
        String str = cbo.getSelectedItem().toString();
        return String2Int.StringToInt(str);
    }
    //TabellenZelle zum int 
    public static int TblCellToInt(DefaultTableModel tbl , int posRow,int posCol)throws Throwable{
        String help = tbl.getValueAt(posRow, posCol).toString().trim() ;
        return String2Int.StringToInt(help);
    }
}
