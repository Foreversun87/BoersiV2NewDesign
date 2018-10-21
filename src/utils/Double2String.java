package utils;

import java.math.BigDecimal;

public class Double2String {
    
	public static String konvert(double pDouble)
	{
            BigDecimal decimal = new BigDecimal(pDouble);		
            String string = decimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();// .toPlainString();

            string = string.replace(",", "x");
            string = string.replace(".", ",");
            string = string.replace("x", ".");

            return string;
	}

}
