package ap.autoalarm.com.myapplication.Util;

/**
 * Created by harshikesh.kumar on 27/01/16.
 */
public class Utility {

  public static String getDecimalFromBinary(int binary)
  {
    return Integer.toString(binary,10);
  }

  public static String getBinaryFromDecimal(int decimal)
  {
    return Integer.toString(decimal, 2);
  }


}
