/**CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: CommentTags
* @description :: A container class to hold comment tags
*/

import java.util.ArrayList;

public abstract class CommentTags {
  /**
  * @name :: tag
  * @description :: The tag that the current comment has
  */
  private String tag;

  /**
  * @name :: raw
  * @description :: The raw contents of the comment
  */
  private ArrayList<String> raw = new ArrayList<String>();

  public CommentTags() {
    
  }
}
