/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/25/16
* @class :: Language
* @description :: An interface to make sure that future commenting languages
* all follow the same format.
*/

import java.util.ArrayList;


public interface Language {

  /**
  * @type :: FUNC
  * @name :: delegateComments
  * @description :: Based on the type of the comment, build the associated
  * object.
  */
  public void delegateComments();

  /**
  * @type :: FUNC
  * @name :: extractComments
  * @description :: Extract the comment strings from the raw content.
  * For now, the extracted comments both get added to individual comment
  * objects as well as the raw ArrayList
  * @param :: ArrayList<String> - the contents of the file
  */
  public void extractComments(ArrayList<String> contents);

  /**
  * @type :: FUNC
  * @name :: getRawComments
  * @description :: Getter for the rawComment ArrayList
  * @return :: ArrayList<String> - the comments to return
  */
  public ArrayList<String> getRawComments();

  /**
  * @type :: FUNC
  * @name :: renderMarkdown
  * @description :: Renders the markdown for the language
  */
  public void renderMarkdown();

}
