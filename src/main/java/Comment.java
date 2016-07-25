/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/25/16
* @class :: Comment
* @description :: An interface to make sure that future comment tags
* all follow the same format.
*/

import java.util.ArrayList;


public interface Comment {

  /**
  * @type :: FUNC
  * @name :: extract
  * @description :: Extracts the individual tags from the cleaned
  * comments from the constructor
  * @note :: Big ass switch statement here
  */
  public void extract();

  /**
  * @type :: FUNC
  * @name :: generateMarkdown
  * @description :: Generates markdown for a variable
  * @return :: String - the variable in markdown
  * @note :: Might have to switch to a different data-structure as
  * a String might not be large enough
  */
  public String generateMarkdown();

}
