package utils;

/**
 * Konvertiert ein vorkommendes Komma in einen Punkt
 */
public class KommaKonvert
{
	public static String konvert(String pString)
	{
		int index = pString.indexOf(',');
		if (index > -1)
		{
			String anfang = pString.substring(0, index);
			String ende = pString.substring((index + 1), pString.length());
			String neu = anfang.concat(".").concat(ende);
			return neu;
		}
		return pString;
	}
}
