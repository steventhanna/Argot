/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: Utility
* @description :: Some helpful utilites to be used system wide
* @note :: This is a static class
*/

public class Utility {

  /**
  * @type :: FUNC
  * @name :: removeWhitespace
  * @description :: Removes whitespace and tabs from begin and end of a string
  * @param :: String - the string to strip whitespace from
  * @return :: String - cleanded string
  */
  public static String removeWhitespace(String s) {
    // Strip the whitespace from the beginning
    if (s.length() > 0) {
      int counter = 0;
      char letter = s.charAt(counter);
      while(letter == ' ' || letter == '\t') {
        s = s.substring(1);
        counter++;
        if(s.length() > 0) {
          letter = s.charAt(counter);
        } else {
          break;
        }
      }
    } else {
      return "";
    }
    // Strip the whitespace from the end
    if(s.length() > 0) {
      char letter = s.charAt(s.length() - 1);
      while(letter == ' ') {
        s = s.substring(s.length() - 1);
        letter = s.charAt(s.length() - 1);
      }
      return s;
    } else {
      return "";
    }
  }
}
