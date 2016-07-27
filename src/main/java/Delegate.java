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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
  * @type :: VAR
  * @name :: recursive
  * @description :: Flag for if Argot should recursively walk the file tree/
  */
  private boolean recursive = false;

  /**
  * @type :: FUNC
  * @name :: Delegate
  * @description :: The constructor for the delegate class.
  * @param :: String src - the src file to begin parsing
  * @param :: String dest - the destination file to place the rendered markdown
  * @param :: String logLevel - how verbose the logs should be to the user
  */
  public Delegate(String src, String dest, String logLevel, boolean recursive) {
    docSrc = new File(src);
    docDest = new File(dest);
    System.out.println("ABS: " + docSrc.getPath());
    System.out.println("ABS: " + docDest.getPath());
    this.logLevel = logLevel;
    this.recursive = recursive;
    parse();
  }

  /**
  * @type :: FUNC
  * @name :: parse
  * @description :: Contains the logic for parsing the file tree, or just the file
  */
  public void parse() {
    // Check to make sure that the files actually exists
    // if(!docSrc.exists()) {
    //   System.err.println("The file: " + docSrc.getPath() + " does not exsit.");
    //   return;
    // }
    // if(docDest == null) {
    //   // Set the docDest to the src
    //   docDest = docSrc;
    // }
    // if(!docDest.exists() && docDest.isFile()) {
    //   System.err.println("The file: " + docDest.getAbsolutePath() + " is a file.");
    //   return;
    // }
    // if(!docDest.exists() && docDest.isDirectory()) {
    //   // Create the folder since it does not exist, and it is a dir
    //   docDest.mkdir();
    // }
    // // If the given destination is a file, walk up the tree until there is a dir
    // while(!docDest.isDirectory()) {
    //   docDest = docDest.getParentFile();
    // }

    // @todo :: Compose threads here for handling the amount of files in the target
    if(recursive == false) {
      System.out.println("RUNNING NOT RECURSIVE");
      if(docSrc.isFile()) {
        System.out.println("RUNNING SINGLE");
        ArgotFile file = new ArgotFile(docSrc);
        writeToFile(new File(docDest + file.getFilename() + ".md"), file.getMarkdown());
      } else {
        System.out.println("RUNNING MULTIPLE");
        System.out.println("FILE: " + docSrc.getPath());
        File[] fileArray = docSrc.listFiles();
        for(int i = 0; i < fileArray.length; i++) {
          ArgotFile file = new ArgotFile(fileArray[i]);
          writeToFile(new File(docDest + file.getFilename() + ".md"), file.getMarkdown());
        }
      }
    } else {
      // Walk the file tree here
    }

  }

  /**
  * @type :: FUNC
  * @name :: writeToFile
  * @description :: Writes contents to a file
  * @param :: File dest - the destination file to write to
  * @param :: ArrayList<String> contents - the contents to be written to the file
  */
  public void writeToFile(File dest, ArrayList<String> contents) {
    try {
      if(!dest.exists()) {
        dest.createNewFile();
      }
      FileWriter fw = new FileWriter(dest.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      for(int i = 0; i < contents.size(); i++) {
        bw.write(contents.get(i));
      }
      bw.close();
    } catch(IOException e) {
      System.err.println("There was an error writing to the file. Error: " + e.getMessage());
    }
  }
}
