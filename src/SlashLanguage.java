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
  * @description :: Strip the language specific comment langauge out of the comments
  */
  public void stripComments() {
    for(int i = 0; i < comments.size(); i++) {
      comments.set(i, strip(comments.get(i)));
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
    
  }
}
