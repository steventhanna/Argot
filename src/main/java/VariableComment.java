/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: VariableComment
* @implements :: Comment
* @description :: A general variable class that stores all
* attributes about a variable. Also can transform a variable
* into markdown
*/

import java.util.ArrayList;


public class VariableComment implements TypeComment {

  /**
  * @type :: VAR
  * @name :: name
  * @description :: The name of the variable
  */
  private String name;

  /**
  * @type :: VAR
  * @name :: description
  * @description :: The description of the variable
  */
  private String description;

  /**
  * @type :: VAR
  * @name :: see
  * @description :: External documentation for the variable
  */
  private ArrayList<String> see = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: notes
  * @description :: Notes of the variable
  */
  private ArrayList<String> notes = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: raw
  * @description :: The raw comment values passed in from the constructor
  */
  private ArrayList<String> raw = new ArrayList<String>();

  /**
  * @type :: FUNC
  * @name :: VariableComment
  * @description :: The constructor for the VariableComment class
  * @param :: ArrayList<String> - the cleaned comment content
  */
  public VariableComment(ArrayList<String> content) {
    raw = content;
    extract();
  }

  /**
  * @type :: FUNC
  * @name :: extract
  * @description :: Extracts the individual tags from the cleaned
  * comments from the constructor
  * @note :: Big ass switch statement here
  */
  public void extract() {
    for(int i = 0; i < raw.size(); i++) {
      String tempComment = raw.get(i);
      // Split the comment at the ::
      String[] commentArr = tempComment.split("::");
      // Check to make sure that the line is a valid comment
      if(commentArr.length == 2) {
        // Remove the white space and extract the tag
        String tag = Utility.removeWhitespace(commentArr[0]);
        String content = Utility.removeWhitespace(commentArr[1]);
        // Make sure that the escape actually exists
        if(tag.charAt(0) == '@') {
          tag = tag.substring(1);
          // For consistency, make sure all the tags are lowercase
          tag = tag.toLowerCase();
          switch(tag) {
            case "name": {
              name = content;
              break;
            }
            case "description": {
              description = content;
              break;
            }
            case "see": {
              see.add(content);
              break;
            }
            case "note": {
              notes.add(content);
              break;
            }
          }
        } else {
          // Not a comment, just ignore it
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
    String builder = "";
    // Name
    if(name != null) {
      builder += "\n### " + name + "\n";
    }
    // Description
    if (description != null) {
      builder += "\n#### Description \n " + description + " \n";
    }
    // See
    if(see.size() != 0) {
      builder += "\n#### See \n";
      builder += "\n";
      for(int i = 0; i < see.size(); i++) {
        builder += "- " + see.get(i) + " \n";
      }
    }
    // Notes
    if(notes.size() != 0) {
      builder += "\n#### Notes \n";
      builder += "\n";
      for(int i = 0; i < notes.size(); i++) {
        builder += "- " + notes.get(i) + " \n";
      }
    }
    return builder;
  }
}
