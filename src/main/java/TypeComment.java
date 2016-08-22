/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 8/21/16
* @class :: TypeComment
* @description :: An interface to make sure that future comment tags
* all follow the same format.
*/


public interface TypeComment {

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
  * @description :: Generates markdown for a function
  * @return :: String - the function in markdown
  * @note :: Might have to switch to a different data-structure as
  * a String might not be large enough
  */
  public String generateMarkdown();

}
