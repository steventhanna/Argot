/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/12/17
* @class :: EndpointComment
* @implements :: Comment
* @description :: A general endpoint class that stores all attributes about
* an endpoint.  Also can transform an endpoint into markdown
*/

import java.util.ArrayList;

public class EndpointComment implements TypeComment {

  /**
  * @type :: VAR
  * @name :: route
  * @description :: The URL of the endpoint
  */
  private String route;

  /**
  * @type :: VAR
  * @name :: crud
  * @description :: The type of url / POST, GET, PUT, DELETE, etc
  */
  private String crud;

  /**
  * @type :: VAR
  * @name :: description
  * @description :: The description of the endpoint
  */
  private String description;

  /**
  * @type :: VAR
  * @name :: params
  * @description :: Parameters that the endpoint expects
  */
  private ArrayList<String> params = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: sampleResponses
  * @description :: Some sample responses that could come back from the endpoint
  */
  private ArrayList<String> sampleResponses = new ArrayList<String>();

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
  * @name :: EndpointComment
  * @description :: The constructor for the EndpointComment class
  * @param :: ArrayList<String> - the cleaned comment content
  */
  public EndpointComment(ArrayList<String> content) {
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
        // Remove the whitespace and extract the tag
        String tag = Utility.removeWhitespace(commentArr[0]);
        String content = Utility.removeWhitespace(commentArr[1]);
        // Make sure that the escape character actually exists
        if(tag.charAt(0) == '@') {
          tag = tag.substring(1);
          // For consistency, make sure all of the tags are lowercase
          tag = tag.toLowerCase();
          System.out.println(tag);
          System.out.println(content);
          switch(tag) {
            case "route": {
              route = content;
              break;
            }
            case "description": {
              description = content;
              break;
            }
            case "crud": {
              crud = content;
              break;
            }
            case "param": {
              params.add(content);
              break;
            }
            case "sample": {
              sampleResponses.add(content);
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
        }
      }
      // Otherwise its not a comment
    }
  }

  /**
  * @type :: FUNC
  * @name :: generateMarkdown
  * @description :: Generates markdown for an endpoint
  * @return :: String - the endpoint in markdown
  * @note :: Might have to switch to a different data-structure as
  * a String might not be large enough
  * @note :: Using a StringBuffer here, will change the other's in other files
  * during refactoring
  */
  public String generateMarkdown() {
    StringBuffer buff = new StringBuffer();
    if (route != null) {
      if (crud != null) {
        buff.append("### " + crud.toUpperCase() + " " + route + "\n");
      } else {
        buff.append("### " + route + "\n");
      }
    }
    if (params.size() > 0) {
      buff.append("\n#### Parameters \n");
      buff.append("\n");
      for(int i = 0; i < params.size(); i++) {
        buff.append("- " + params.get(i) + "\n");
      }
      buff.append("\n");
    }
    if (sampleResponses.size() > 0) {
      buff.append("\n#### Sample Responses \n");
      buff.append("\n");
      for(int i = 0; i < sampleResponses.size(); i++) {
        buff.append("- " + sampleResponses.get(i) + "\n");
      }
      buff.append("\n");
    }
    if (notes.size() > 0) {
      buff.append("\n#### Notes \n");
      buff.append("\n");
      for(int i = 0; i < notes.size(); i++) {
        buff.append("- " + notes.get(i) + "\n");
      }
      buff.append("\n");
    }
    if (see.size() > 0) {
      buff.append("\n#### See \n");
      buff.append("\n");
      for(int i = 0; i < see.size(); i++) {
        buff.append("- " + see.get(i) + "\n");
      }
      buff.append("\n");
    }
    return buff.toString();
  }

}
