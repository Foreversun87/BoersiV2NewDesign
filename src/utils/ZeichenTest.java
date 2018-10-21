package utils;
import services.PlausiException;
import javafx.scene.control.Control;

public class ZeichenTest
{
	public static void Umlaute(String pTest, Control pFeld) throws Throwable
	{
		if (pTest.indexOf("Ä") > -1)
		{
			pFeld.requestFocus();
			throw new PlausiException("Umlaute sind für diese\nFeld nicht erlaubt!\nBitte Ä durch Ae in ersetzen.", pFeld);
		}
		if (pTest.indexOf("ä") > -1)
		{
			pFeld.requestFocus();
			throw new PlausiException("Umlaute sind für diese\nFeld nicht erlaubt!\nBitte ä durch ae ersetzen.");
		}
		if (pTest.indexOf("Ö") > -1)
		{
			pFeld.requestFocus();
			throw new PlausiException("Umlaute sind für diese\nFeld nicht erlaubt!\nBitte Ö durch Oe ersetzen.");
		}
		if (pTest.indexOf("ö") > -1)
		{
			pFeld.requestFocus();
			throw new PlausiException("Umlaute sind für diese\nFeld nicht erlaubt!\nBitte ö durch oe ersetzen.");
		}
		if (pTest.indexOf("Ü") > -1)
		{
			pFeld.requestFocus();
			throw new PlausiException("Umlaute sind für diese\nFeld nicht erlaubt!\nBitte Ü durch Ue ersetzen.");
		}
		if (pTest.indexOf("ü") > -1)
		{
			pFeld.requestFocus();
			throw new PlausiException("Umlaute sind für diese\nFeld nicht erlaubt!\nBitte ü durch ue ersetzen.");
		}
    if (pTest.indexOf("ß") > -1)
		{
			pFeld.requestFocus();
			throw new PlausiException("Keine ß für dieses Feld erlaubt\nBitte durch ss ersetzen.");
		}
	}

  public static void Sonderzeichen(String pTest, javax.swing.JTextField pFeld) throws Throwable
	{
     for ( int i = 0; i < pFeld.getText().length(); i++){
      if ( !Character.isLetterOrDigit(pFeld.getText().charAt(i)))
        throw new PlausiException("Sonderzeichen sind für\ndiesen Feld nicht erlaubt!\n");
    }
  }
}
