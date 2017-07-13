/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/25/16
* @class :: Comment
* @description :: An interface to make sure that future comment tags
* all follow the same format.
*/

import java.util.ArrayList;


public class Comment {

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

  public Comment(ArrayList<String> rawData, String commentStyle) {
    this.rawData = rawData;
    clean(commentStyle);
    extractType();
  }

  public Comment(ArrayList<String> rawData, String commentStyle, String beginStyle, String endStyle) {
    this.rawData = rawData;
    clean(commentStyle, beginStyle, endStyle);
    extractType();
  }

  public Comment(ArrayList<String> rawData, String commentStyle, String beginStyle, String endStyle, String commentEndStyle) {
    this.rawData = rawData;
    clean(commentStyle, beginStyle, endStyle, commentEndStyle);
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
  * @name :: strip
  * @description :: Strips whitespace, and other language specific comments
  * from the given String
  * @param :: String - the string to strip the LSC's from
  * @param :: String commentStyle - the commenting style to remove
  * @param :: String beginStyle - the specific beginning style of the comment to remove
  * @param :: String endStyle - the specific ending style of the comment to remove
  * @param :: String commentEndStyle - the specific comment ending to remove like in HTML
  * @return :: String - The cleaned string
  */
  public String strip(String s, String commentStyle, String beginStyle, String endStyle, String commentEndStyle) {
    // Strip the whitespace from the beginning
    s = s.trim();

    // Specific conditions
    if(beginStyle == null || endStyle == null) {
      if(s.length() >= commentStyle.length() && s.substring(0, commentStyle.length()).equals(commentStyle)) {
        s = s.substring(commentStyle.length());
      }
      s = s.trim();
    } else {
      if(beginStyle != null && endStyle != null) {
        if(s.length() >= beginStyle.length() && s.substring(0, beginStyle.length()).equals(beginStyle)) {
          s = "";
        } else if(s.equals(beginStyle)) {
          s = "";
        } else if(s.equals(endStyle)) {
          s = "";
        } else if(s.length() >= commentStyle.length() && s.substring(0, commentStyle.length()).equals(commentStyle)) {
          s = s.substring(commentStyle.length());
        }
      }
      if(commentEndStyle != null) {
        if(s.length() > commentEndStyle.length() && s.substring(s.length() - commentEndStyle.length()).equals(commentEndStyle)) {
          s = s.substring(0, s.length() - commentEndStyle.length());
        }
      }
    }
    s = s.trim();
    return s;
  }

  /**
  * @type :: FUNC
  * @name :: clean
  * @description :: removes commenting styles from the arraylist
  * @param :: String commentStyle - the comment style to remove from the string
  */
  public void clean(String commentStyle) {
    for(int i = 0; i < rawData.size(); i++) {
      String afterStrip = strip(rawData.get(i), commentStyle, null, null, null).trim();
      if(!afterStrip.equals("")) {
        cleanedComments.add(afterStrip);
      }
    }
    // For comments inbetween escape chars, combine them into one large comment
    if(cleanedComments.size() > 1) {
      for(int i = 0; i < cleanedComments.size(); i++) {
        // Check to see if there are any type notations
        if(!cleanedComments.get(i).contains("@") && !cleanedComments.get(i).contains("::") && cleanedComments.get(i).contains("")) {
          // Chances are its a multi-line comment
          cleanedComments.set(i - 1, cleanedComments.get(i - 1) + " " + cleanedComments.get(i));
          cleanedComments.remove(i);
          i--;
        }
      }
    }
  }

  /**
  * @type :: FUNC
  * @name :: clean
  * @description :: removes commenting styles from the arraylist
  * @param :: String commentStyle - the comment style to remove from the string
  * @param :: String beginStyle - the style that starts the comment set
  * @param :: String endStyle - the style that ends the comment set
  */
  public void clean(String commentStyle, String beginStyle, String endStyle) {
    for(int i = 0; i < rawData.size(); i++) {
      String afterStrip = strip(rawData.get(i), commentStyle, beginStyle, endStyle, null).trim();
      if(!afterStrip.equals("")) {
        cleanedComments.add(afterStrip);
      }
    }
    // For comments inbetween escape chars, combine them into one large comment
    if(cleanedComments.size() > 1) {
      for(int i = 0; i < cleanedComments.size(); i++) {
        // Check to see if there are any type notations
        cleanedComments.set(i, cleanedComments.get(i));
        if(!cleanedComments.get(i).contains("@") && !cleanedComments.get(i).contains("::") && !cleanedComments.get(i).equals("")) {
          // Chances are its a multi-line comment
          cleanedComments.set(i - 1, cleanedComments.get(i - 1) + " " + cleanedComments.get(i));
          cleanedComments.remove(i);
          i--;
        }
      }
    }
  }

  /**
  * @type :: FUNC
  * @name :: clean
  * @description :: removes commenting styles from the arraylist
  * @param :: String commentStyle - the comment style to remove from the string
  * @param :: String beginStyle - the style that starts the comment set
  * @param :: String endStyle - the style that ends the comment set
  * @param :: String commentEndStyle - the specific ending of a comment like in HTML
  */
  public void clean(String commentStyle, String beginStyle, String endStyle, String commentEndStyle) {
    for(int i = 0; i < rawData.size(); i++) {
      String afterStrip = strip(rawData.get(i), commentStyle, beginStyle, endStyle, commentEndStyle).trim();
      if(!afterStrip.equals("")) {
        cleanedComments.add(afterStrip);
      }
    }
    // For comments inbetween escape chars, combine them into one large comment
    if(cleanedComments.size() > 1) {
      for(int i = 0; i < cleanedComments.size(); i++) {
        // Check to see if there are any type notations
        if(!cleanedComments.get(i).contains("@") && !cleanedComments.get(i).contains("::") && !cleanedComments.get(i).equals("")) {
          // Chances are its a multi-line comment
          cleanedComments.set(i - 1, cleanedComments.get(i - 1) + " " + cleanedComments.get(i));
          cleanedComments.remove(i);
          i--;
        }
      }
    }
  }

  /**
  * @type :: FUNC
  * @name :: generateMarkdown
  * @description :: Generates markdown for a variable
  * @return :: String - the variable in markdown
  * @note :: Might have to switch to a different data-structure as
  * a String might not be large enough
  */
  public String generateMarkdown() {
    switch(type) {
      case "func": {
        FunctionComment com = new FunctionComment(cleanedComments);
        return com.generateMarkdown();
      }
      case "var": {
        VariableComment com = new VariableComment(cleanedComments);
        return com.generateMarkdown();
      }
      case "class": {
        ClassComment com = new ClassComment(cleanedComments);
        return com.generateMarkdown();
      }
      default: {
        System.out.println("That type is not yet supported.");
        return null;
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

}
