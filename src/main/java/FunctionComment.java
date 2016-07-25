/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: FunctionComment
* @description :: A general function class that stores all
* attributes about a function. Also can transform a function
* into markdown
*/

import java.util.ArrayList;


public class FunctionComment {

  /**
  * @type :: VAR
  * @name :: name
  * @description :: The name of the function
  */
  private String name;

  /**
  * @type :: VAR
  * @name :: description
  * @description :: The description of the function
  */
  private String description;

  /**
  * @type :: VAR
  * @name :: author
  * @description :: The author of the function
  */
  private String author;

  /**
  * @type :: VAR
  * @name :: date
  * @description :: The date of the function
  */
  private String date;

  /**
  * @type :: VAR
  * @name :: params
  * @description :: The params of the function
  */
  private ArrayList<String> params = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: returnElement
  * @description :: What the function returns
  */
  private String returnElement;

  /**
  * @type :: VAR
  * @name :: exceptions
  * @description :: The exceptions of the function
  */
  private ArrayList<String> exceptions = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: thrown
  * @description :: What the function throws
  */
  private ArrayList<String> thrown = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: see
  * @description :: External documentation for the function
  */
  private ArrayList<String> see = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: notes
  * @description :: Notes of the function
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
  * @name :: FunctionComment
  * @description :: The constructor for the FunctionComment class
  * @param :: ArrayList<String> - the cleaned comment content
  */
  public FunctionComment(ArrayList<String> content) {
    raw = content;
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
          tag = tag.substring(0);
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
            case "author": {
              author = content;
              break;
            }
            case "date": {
              date = content;
              break;
            }
            case "param": {
              params.add(content);
              break;
            }
            case "return": {
              returnElement = content;
              break;
            }
            case "exception": {
              exceptions.add(content);
              break;
            }
            case "throw": {
              thrown.add(content);
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
  * @description :: Generates markdown for a function
  * @return :: String - the function in markdown
  * @note :: Might have to switch to a different data-structure as
  * a String might not be large enough
  */
  public String generateMarkdown() {
    String builder = "";
    // Name
    if(name != null) {
      builder += "### " + name + "\n";
    }
    // author
    if (author != null) {
      builder += "**" + date + "** *" + author + "*\n";
    }
    // Description
    if (description != null) {
      builder += "#### Description \n " + description;
    }
    // Params
    if (params.size() != 0) {
      builder += "#### Parameters \n ";
      for(int i = 0; i < params.size(); i++) {
        builder += "- " + params.get(i) + " \n";
      }
    }
    // Return Element
    if(returnElement != null) {
      builder += "**" + returnElement + "** \n";
    }
    // Exceptions
    if(exceptions.size() != 0) {
      builder += "#### Exceptions \n";
      for(int i = 0; i < exceptions.size(); i++) {
        builder += "- " + exceptions.get(i) + " \n";
      }
    }
    // Thrown
    if(thrown.size() != 0) {
      builder += "#### Thrown \n";
      for(int i = 0; i < thrown.size(); i++) {
        builder += "- " + thrown.get(i) + " \n";
      }
    }
    // See
    if(see.size() != 0) {
      builder += "#### See \n";
      for(int i = 0; i < see.size(); i++) {
        builder += "- " + see.get(i) + " \n";
      }
    }
    // Notes
    if(notes.size() != 0) {
      builder += "#### Notes \n";
      for(int i = 0; i < notes.size(); i++) {
        builder += "- " + notes.get(i) + " \n";
      }
    }
    return builder;
  }
}
