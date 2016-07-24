/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: Function
* @description :: A general function class that stores all
* attributes about a function. Also can transform a function
* into markdown
*/

import java.util.ArrayList;


public class Function {

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
  * @name :: Function
  * @description :: The constructor for the Function class
  * @param :: ArrayList<String> - the cleaned comment content
  */
  public Function(ArrayList<String> content) {
    raw = content;
  }

  /**
  * @type :: FUNC
  * @name :: extract
  * @description :: Extracts the individual tags from the cleaned
  * comments from the constructor
  */
  public void extract() {
    for(int i = 0; i < raw.size(); i++) {
      String tempComment = raw.get(i);
      // Split the comment at the ::
      String[] commentArr = tempComment.split("::");
      // Removme the white space and extract the tag
      String tag = Utility.removeWhitespace(commentArr[0]);
      
    }
  }


}
