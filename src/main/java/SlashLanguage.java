/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: SlashLanguage
* @description :: The specific worker class for Slash based commenting
* langauges. All extraction, and analysis is performed here, while
* markdown generation is perfomed elsewhere.
*/

import java.util.ArrayList;

public class SlashLanguage {

  /**
  * @type :: VAR
  * @name :: rawComments
  * @description :: String Representaion of the comments
  */
  private ArrayList<String> rawComments = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: comments
  * @description :: Object representation of comments
  */
  private ArrayList<SlashComment> comments = new ArrayList<SlashComment>();

  /**
  * @type :: VAR
  * @name :: functions
  * @description :: Oject representation of all the function comments. Used
  * as staging before Markdown creation.
  */
  private ArrayList<FunctionComment> functions = new ArrayList<FunctionComment>();

  /**
  * @type :: VAR
  * @name :: variables
  * @description :: Object representation of all the variable comments.  Used
  * as a staging before Markdown creation.
  */
  private ArrayList<VariableComment> variables = new ArrayList<VariableComment>();

  /**
  * @type :: VAR
  * @name :: classes
  * @description :: Object representation of all the class comments.  Used
  * as a staging before Markdown creation.
  */
  private ArrayList<ClassComment> classes = new ArrayList<ClassComment>();

  /**
  * @type :: FUNC
  * @name :: SlashLanguage
  * @description :: The constructor for the class
  * @param :: ArrayList<String> - The raw contents of the file
  */
  public SlashLanguage(ArrayList<String> contents) {
    extractComments(contents);
    delegateComments();
  }

  /**
  * @type :: FUNC
  * @name :: delegateComments
  * @description :: Based on the type of the comment, build the associated
  * object.
  */
  public void delegateComments() {
    for(int i = 0; i < comments.size(); i++) {
      // Look for the tag
      String type = comments.get(i).getType();
      switch(type) {
        case "FUNC": {
          FunctionComment function = new FunctionComment(comments.get(i).getCleanedComments());
          functions.add(function);
          break;
        }
        case "VAR": {
          VariableComment variable = new VariableComment(comments.get(i).getCleanedComments());
          variables.add(variable);
          break;
        }
        case "CLASS": {
          ClassComment classComment = new ClassComment(comments.get(i).getCleanedComments());
          classes.add(classComment);
        }
      }
    }
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

  /**
  * @type :: FUNC
  * @name :: getRawComments
  * @description :: Getter for the rawComment ArrayList
  * @return :: ArrayList<String> - the comments to return
  */
  public ArrayList<String> getRawComments() {
    return rawComments;
  }
}
