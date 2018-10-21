package utils;

public class PunktKonvert
{
	public static String konvert(String pString)
	{
		int index = pString.indexOf('.');
		if (index > -1)
		{
			String anfang = pString.substring(0, index);
			String ende = pString.substring((index + 1), pString.length());
			String neu = anfang.concat(",").concat(ende);
			return neu;
		}
		return pString;
	} 
        
  public static String konvertToWaehrung(String pString)
	{
		int index = pString.indexOf('.');
		if (index > -1)
		{
			String anfang = pString.substring(0, index);
			String ende = pString.substring((index + 1), pString.length());
            String ende_neu = "";
                        
                        if ( ende.length() == 0)
                            ende_neu = ende.concat("00 €");
                        if ( ende.length() == 1 )
                            ende_neu = ende.concat("0 €");
                        if ( ende.length() >= 2 )
                            ende_neu = ende.concat(" €");
                        
			String neu = anfang.concat(",").concat(ende_neu);
			return neu;
		}
		return pString;
	}
        
  public static String removeEuro(String pString)
	{
		int index = pString.indexOf('€');
		if (index > -1)
		{
			String anfang = pString.substring(0, index);
			return anfang;
		}
		return pString;
	}
        
}
