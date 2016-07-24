/**CLASS
* @author :: Steven T Hanna
* @date :: 7/24/16
* @class :: Comment
* @description :: A container class to hold comment information for an entire file.
* All of the language specific commenting has been stripped
*/

import java.util.ArrayList;

public class Comment {

  /**VAR
  * @name :: rawData
  * @description :: the raw strings directly from the constructor
  */
  private ArrayList<String> rawData = new ArrayList<String>();

  /**VAR
  * @name :: extractedComments
  * @description :: object representations of the comments
  */
  private ArrayList<CommentTags> extractedComments = new ArrayList<CommentTags>();

  /**FUNC
  * @name :: Comment
  * @description :: Constructor
  * @param :: ArrayList<String> - the data without the individual comments language from the
  * main set of classes
  */
  public Comment(ArrayList<String> data) {
    rawData = data;
  }
}
