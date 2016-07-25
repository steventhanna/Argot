/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: ClassComment
* @implements :: Comment
* @description :: A general class class that stores all
* attributes about a class. Also can transform a class
* into markdown
*/

import java.util.ArrayList;


public class ClassComment implements Comment {

  /**
  * @type :: VAR
  * @name :: className
  * @description :: The name of the class
  */
  private String className;

  /**
  * @type :: VAR
  * @name :: description
  * @description :: The description of the class
  */
  private String description;

  /**
  * @type :: VAR
  * @name :: author
  * @description :: The author of the class
  */
  private String author;

  /**
  * @type :: VAR
  * @name :: date
  * @description :: The date of the class
  */
  private String date;

  /**
  * @type :: VAR
  * @name :: see
  * @description :: External documentation for the class
  */
  private ArrayList<String> see = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: notes
  * @description :: Notes of the class
  */
  private ArrayList<String> notes = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: parents
  * @description :: The parent files for a specific class
  */
  private ArrayList<String> parents = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: children
  * @description :: The child files for a specific class
  */
  private ArrayList<String> children = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: raw
  * @description :: The raw comment values passed in from the constructor
  */
  private ArrayList<String> raw = new ArrayList<String>();

  /**
  * @type :: FUNC
  * @name :: ClassComment
  * @description :: The constructor for the ClassComment class
  * @param :: ArrayList<String> - the cleaned comment content
  */
  public ClassComment(ArrayList<String> content) {
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
            case "class": {
              className = content;
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
            case "see": {
              see.add(content);
              break;
            }
            case "note": {
              notes.add(content);
              break;
            }
            case "parent": {
              parents.add(content);
              break;
            }
            case "child": {
              children.add(content);
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
  * @description :: Generates markdown for a class
  * @return :: String - the class in markdown
  * @note :: Might have to switch to a different data-structure as
  * a String might not be large enough
  */
  public String generateMarkdown() {
    String builder = "";
    // Name
    if(className != null) {
      builder += "# " + className + "\n";
    }
    // author
    if (author != null && date != null) {
      builder += "**" + date + " -- ** *" + author + "*\n";
    } else if (author != null) {
      builder += "**" + author +"**\n";
    } else {
      builder += date + "\n";
    }
    // Description
    if (description != null) {
      builder += "### Description \n " + description;
    }
    // Parents
    if(parents.size() != 0) {
      builder += "#### Parents \n ";
      for(int i = 0; i < parents.size(); i++) {
        builder += "- " + parents.get(i) + " \n";
      }
    }
    // Children
    if(children.size() != 0) {
      builder += "#### Children \n ";
      for(int i = 0; i < children.size(); i++) {
        builder += "- " + children.get(i) + " \n";
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
