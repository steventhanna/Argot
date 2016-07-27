/**
* @type :: class
* @class :: Delegate
* @author :: Steven Hanna
* @date :: 7/27/16
* @description :: Takes a SRC, a DEST, and a Log Level, and completes
* all of the Argot operations here.
* @note :: This class should be multithreaded.
*/

import java.util.ArrayList;
import java.io.File;

public class Delegate {

  /**
  * @type :: VAR
  * @name :: logLevel
  * @description :: Sets the systmem wide log level. Other options include
  * verbose, and quiet.
  */
  private String logLevel;

  /**
  * @type :: VAR
  * @name :: docSrc
  * @description :: The src file / dir to be parsed
  * @note :: This is a file, unline in Main where it is a String
  */
  private File docSrc;

  /**
  * @type :: VAR
  * @name :: docDest
  * @description :: The destination to place the parsed input files.
  * @note :: This is a file, unline in Main where it is a String
  */
  private File docDest;

  /**
  * @type :: FUNC
  * @name :: Delegate
  * @description :: The constructor for the delegate class.
  * @param :: String src - the src file to begin parsing
  * @param :: String dest - the destination file to place the rendered markdown
  * @param :: String logLevel - how verbose the logs should be to the user
  */
  public Delegate(String src, String dest, String logLevel) {
    docSrc = new File(src);
    docDest = new File(dest);
    this.logLevel = logLevel;
  }

  /**
  * @type :: FUNC
  * @name :: parse
  * @description :: Contains the logic for parsing the file tree, or just the file
  */
  public void parse() {
    // Check to make sure that the files actually exists
    if(!docSrc.exists()) {
      System.err.println("The file: " + docSrc.getAbsolutePath() + " does not exsit.");
      return;
    }
    if(!docDest.exists() && docDest.isFile()) {
      System.err.println("The file: " + docDest.getAbsolutePath() + " does not exist, and it is a file.");
      return;
    }
    if(!docDest.exists() && docDest.isDir()) {
      // Create the folder since it does not exist, and it is a dir
      docDest.mkdir();
    }
    // If the given destination is a file, walk up the tree until there is a dir
    if(docDest.isDir())
  }
}
