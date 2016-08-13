/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: SlashLanguage
* @implements :: Language
* @description :: The specific worker class for Slash based commenting
* langauges. Basic extraction from the source file is done here, while
* stripping, whitespace removal, and type identification is done in SlashComment.
* The rendered markdown is gathered here however.
*/

import java.util.ArrayList;

public class SlashLanguage extends Language {

  /**
  * @type :: FUNC
  * @name :: SlashLanguage
  * @description :: The constructor for the class
  * @param :: ArrayList<String> - The raw contents of the file
  */
  public SlashLanguage(ArrayList<String> contents) {
    extractComments(contents);
    delegateComments();
    renderMarkdown();
  }

  /**
  * @type :: FUNC
  * @name :: extractComments
  * @description :: Extract the comment strings from the raw content.
  * For now, the extracted comments both get added to individual comment
  * objects as well as the raw ArrayList
  * @param :: ArrayList<String> - the contents of the file
  */
  public void extractComments(ArrayList<String> contents) {
    for(int i = 0; i < contents.size(); i++) {
      ArrayList<String> commentBuffer = new ArrayList<String>();
      if(contents.get(i).contains("/**")) {
        rawComments.add(contents.get(i));
        commentBuffer.add(contents.get(i));
        for(int j = i + 1; j < contents.size(); j++) {
          if(contents.get(j).contains("*")) {
            rawComments.add(contents.get(j));
            commentBuffer.add(contents.get(j));
          } else if(contents.get(j).contains("*/")) {
            rawComments.add(contents.get(j));
            commentBuffer.add(contents.get(j));
            i = j;
            break;
          } else {
            // System.out.println(contents.get(j));
            // System.out.println("Something probably went wrong.");
            comments.add(new SlashComment(commentBuffer));
            i = j;
            break;
          }
        }
      }
    }
  }
}
