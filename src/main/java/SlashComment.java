/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: SlashComment
* @description :: A container class to hold comment information for an entire file.
* All of the language specific commenting has been stripped
*/

import java.util.ArrayList;

public class SlashComment implements Comment {

  /**
  * @type :: VAR
  * @name :: type
  * @description :: The type of the specific comment
  */
  private String type;

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
  public SlashComment(ArrayList<String> data) {
    rawData = data;
    clean();
    extractType();
  }

  /**
  * @type :: FUNC
  * @name :: extractType
  * @description :: Extracts the type of the function.
  */
  public void extractType() {
    String gettingTag = "";
    for(int i = 0; i < cleanedComments.size(); i++) {
      if(cleanedComments.get(i).toLowerCase().contains("@type")) {
        gettingTag = cleanedComments.get(i).toLowerCase();
        break;
      }
    }
    if(!gettingTag.equals("")) {
      String[] tagArr = gettingTag.split("::");
      if(tagArr.length == 2) {
        type = Utility.removeWhitespace(tagArr[1].toLowerCase());
      }
    }
  }

  /**
  * @type :: FUNC
  * @name :: getType
  * @description :: Getter for the type
  * @return :: String - returns the type of the comment
  */
  public String getType() {
    return type;
  }

  /**
  * @type :: FUNC
  * @name :: getCleanedComments
  * @description :: Returns the cleaned comments
  * @return :: Arraylist<String> - the cleaned comments
  */
  public ArrayList<String> getCleanedComments() {
    return cleanedComments;
  }

  /**
  * @type :: FUNC
  * @name :: clean
  * @description :: Cleans the comments, removes whitespace, tabs, and
  */
  public void clean() {
    for(int i = 0; i < rawData.size(); i++) {
      // System.out.println("BEOFRE: " + rawData.get(i));
      String afterStrip = removeWhitespace(strip(rawData.get(i)));
      // System.out.println("AFTER: " + afterStrip);
      if(!afterStrip.equals("") && !afterStrip.contains("/**") && !afterStrip.contains("*/")) {
        cleanedComments.add(afterStrip);
      }
    }
    // For comments inbetween escape chars, combine them into one
    if (cleanedComments.size() > 1) {
      for(int i = 1; i < cleanedComments.size(); i++) {
        // Chances are it is a mutli-line comment, so combine them
        if(!cleanedComments.get(i).contains("@")) {
          cleanedComments.set(i - 1, cleanedComments.get(i - 1) + " " + cleanedComments.get(i));
          cleanedComments.remove(i);
          i--;
        }
      }
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
    return s.trim();
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
      s = " ";
    } else if(s.length() >= 2 && s.substring(0, 2).equals("/*")) {
      s = s.substring(2);
    } else if (s.equals("*/")) {
      s = " ";
    } else if (s.length() >= 1 && s.substring(0, 1).equals("*")) {
      s = s.substring(1);
    }
    s = removeWhitespace(s);
    return s;
  }
}
