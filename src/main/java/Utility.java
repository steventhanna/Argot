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
    return s.trim();
  }
}
