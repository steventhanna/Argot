/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: Comment
* @description :: A container class to hold comment information for an entire file.
* All of the language specific commenting has been stripped
*/

import java.util.ArrayList;

public class Comment {

  /**
  * @type :: VAR
  * @name :: rawData
  * @description :: the raw strings directly from the constructor
  */
  private ArrayList<String> rawData = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: cleanedComments
  * @description :: the sanitized version of the comments
  */
  private ArrayList<String> cleanedComments = new ArrayList<String>();

  /**
  * @type :: FUNC
  * @name :: Comment
  * @description :: Constructor. Cleans and sanitizes comments before any operations begin.
  * @param :: ArrayList<String> - the data without the individual comments language from the
  * main set of classes
  */
  public Comment(ArrayList<String> data) {
    rawData = data;
    for(int i = 0; i < rawData.size(); i++) {
      // System.out.println("BEOFRE: " + rawData.get(i));
      String afterStrip = removeWhitespace(strip(rawData.get(i)));
      // System.out.println("AFTER: " + afterStrip);
      if(!afterStrip.equals("")) {
        cleanedComments.add(afterStrip);
      }
    }
    System.out.println("CLEANED: ");
    for(int i = 0; i < cleanedComments.size(); i++) {
      System.out.println(cleanedComments.get(i));
      // System.out.println("DIFF");
      // System.out.println(removeWhitespace(cleanedComments.get(i)));
    }
  }

  /**
  * @type :: FUNC
  * @name :: removeWhitespace
  * @description :: Removes whitespace and tabs from begin and end of a string
  * @param :: String - the string to strip whitespace from
  * @return :: String - cleanded string
  */
  public String removeWhitespace(String s) {
    // Strip the whitespace from the beginning
    if (s.length() > 0) {
      int counter = 0;
      char letter = s.charAt(counter);
      while(letter == ' ' || letter == '\t') {
        s = s.substring(1);
        counter++;
        letter = s.charAt(counter);
      }

      // Strip the whitespace from the end
      letter = s.charAt(s.length() - 1);
      while(letter == ' ') {
        s = s.substring(s.length() - 1);
        letter = s.charAt(s.length() - 1);
      }
      return s;
    } else {
      return "";
    }
  }

  /**
  * @type :: FUNC
  * @name :: strip
  * @description :: Strips whitespace, and other language specific comments
  * from the given String
  * @param :: String - the string to strip the LSC's from
  * @return :: String - The cleaned string
  */
  public String strip(String s) {
    // Strip the whitespace from the beginning
    s = removeWhitespace(s);

    // Specific conditions
    if(s.length() >= 3 && s.substring(0, 3).equals("/**")) {
      s = s.substring(3);
    } else if(s.length() >= 2 && s.substring(0, 2).equals("/*")) {
      s = s.substring(2);
    } else if (s.equals("*/")) {
      s = "";
    } else if (s.length() >= 1 && s.substring(0, 1).equals("*")) {
      s = s.substring(1);
    }

    // System.out.println("AFTER COMMENT: " + s);

    s = removeWhitespace(s);
    return s;
  }
}
