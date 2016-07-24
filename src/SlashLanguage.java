/**CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: SlashLanguage
* @description :: The specific worker class for Slash based commenting
* langauges. All extraction, and analysis is performed here, while
* markdown generation is perfomed elsewhere.
*/

import java.util.ArrayList;

public class SlashLanguage {

  /**VAR
  * @name :: comments
  * @description :: Representaion of the comments
  */
  private ArrayList<String> comments = new ArrayList<String>();

  /**FUNC
  * @name :: SlashLanguage
  * @description :: The constructor for the class
  * @param :: ArrayList<String> - The raw contents of the file
  */
  public SlashLanguage(ArrayList<String> contents) {
    extractComments(contents);
    stripComments();
  }

  /**FUNC
  * @name :: extractComments
  * @description :: Extract the comment strings from the raw content
  * @param :: ArrayList<String> - the contents of the file
  */
  public void extractComments(ArrayList<String> contents) {
    for(int i = 0; i < contents.size(); i++) {
      if(contents.get(i).contains("/**")) {
        comments.add(contents.get(i));
        for(int j = i; j < contents.size(); j++) {
          if(contents.get(j).contains("/*")) {
            comments.add(contents.get(j));
          } else if(contents.get(j).contains("*/")) {
            comments.add(contents.get(j));
            i = j;
            break;
          } else {
            System.out.println("Something probably went wrong.");
            i = j;
            break;
          }
        }
      }
    }
  }

  /**FUNC
  * @name :: stripComments
  * @description :: Strip the language specific comment langauge out of the comments.
  * Also removes blank comments
  */
  public void stripComments() {
    int counter = 0;
    while(counter < comments.size()) {
      String afterStrip = strip(comments.get(counter));
      if(!afterStrip.equals("")) {
        comments.remove(counter);
      } else {
        comments.set(counter, afterStrip);
        counter++;
      }
    }
  }

  /**FUNC
  * @name :: strip
  * @description :: Strips whitespace, and other language specific comments
  * from the given String
  * @param :: String - the string to strip the LSC's from
  * @return :: String - The cleaned string
  */
  public String strip(String s) {
    // Strip the whitespace from the beginning
    int counter = 0;
    char letter = s.charAt(counter);
    while(letter == ' ') {
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

    // Remove whitespace again
    if(s.length() > 0) {
      counter = 0;
      letter = s.charAt(counter);
      while(letter == ' ') {
        s = s.substring(1);
        counter++;
        letter = s.charAt(counter);
      }
    }
    if (s.length() > 0) {
      // Strip the whitespace from the end
      letter = s.charAt(s.length() - 1);
      while(letter == ' ') {
        s = s.substring(s.length() - 1);
        letter = s.charAt(s.length() - 1);
      }
    }
    return s;
  }

  /**
  * @name :: getComments
  * @description :: Getter for the comment ArrayList
  * @return :: ArrayList<String> - the comments to return
  */
  public ArrayList<String> getComments() {
    return comments;
  }
}
