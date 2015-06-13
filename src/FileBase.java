/**
* @author Steven T Hanna
* @date 5/5/15
* @class FileBase
* @description Interface containing methods a File should contain
*/

import java.util.ArrayList;

public interface FileBase {

  /**
  * Get Filename
  * @return String name of file
  */
  public String getFilename();
  /** @end */

  /**
  * Get Complete File Path
  * @return String name of absolute path
  */
  public String getPath();
  /** @end */

  /**
  * Get extension
  * @return String name of extension of class
  */
  public String getExtension();
  /** @end */

  /**
  * Get Classname
  * @return String name of class
  */
  public String getClassname();
  /** @end */

  /**
  * Seperates the extension and the classname from the absolute path
  * @note Does not return anything, but stores in instance variables
  */
  public void seperateClassnameExtension();
  /** @end */

  /**
  * Reads the contents of a file
  * @return ArrayList<String> of file contents
  */
  public ArrayList<String> read();
  /** @end */

}
