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
import org.apache.commons.io.FilenameUtils;

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
    if(!docSrc.exists()) {
      System.err.println("The file: " + docSrc.getAbsolutePath() + " does not exsit.");
      return;
    }
    // Check read permissions for docSrc
    if(!docSrc.canRead()) {
      System.err.println("The file: " + docSrc.getAbsolutePath() + " cannot be read becuase Argot does not have access.");
      return;
    }
    if(docDest == null) {
      // Set the docDest to the src
      docDest = docSrc;
    }
    if(!docDest.exists() && docDest.isFile()) {
      System.err.println("The file: " + docDest.getAbsolutePath() + " is a file.");
      return;
    }
    // Check write permissions for docDest
    if(!docDest.canWrite()) {
      System.err.println("The file: " + docDest.getAbsolutePath() + " cannot be written to becuase Argot does not have access.");
      return;
    }
    if(!docDest.exists() && docDest.isDirectory()) {
      // Create the folder since it does not exist, and it is a dir
      docDest.mkdir();
    }
    // If the given destination is a file, walk up the tree until there is a dir
    while(!docDest.isDirectory()) {
      docDest = docDest.getParentFile();
    }

    // @todo :: Compose threads here for handling the amount of files in the target
    if(recursive == false) {
      if(docSrc.isFile()) {

        ArgotFile file = new ArgotFile(docSrc, new File(docDest + "/" + getFilename(docSrc.toString()) + ".md"));
        // writeToFile(new File(docDest + "/" + file.getFilename() + ".md"), file.getMarkdown());
      } else {
        File[] fileArray = docSrc.listFiles();
        for(int i = 0; i < fileArray.length; i++) {
          if(!fileArray[i].canRead()) {
            System.err.println("The file: " + fileArray[i].getAbsolutePath() + " cannot be read becuase Argot does not have access.");
          } else if(!fileArray[i].isDirectory()) {
            ArgotFile file = new ArgotFile(fileArray[i], new File(docDest + "/" + getFilename(fileArray[i].toString()) + ".md"));
            file.start();
            // if(file.getMarkdown().size() != 0) {
            //   writeToFile(new File(docDest + "/" + file.getFilename() + ".md"), file.getMarkdown());
            // }
          }
        }
      }
    } else {
      // Walk the file tree here
      recursiveWalk(docSrc);
    }
  }

  /**
  * @type :: FUNC
  * @name :: recursiveWalk
  * @description :: Recursively walk a file tree creating documentation.
  * @param :: File file - the file to recursively check
  */
  public void recursiveWalk(File file) {
    if(!file.canRead()) {
      System.err.println("The file: " + file.getAbsolutePath() + " cannot be read becuase Argot does not have access.");
      return;
    }
    if(file.isDirectory()) {
      File[] files = file.listFiles();
      for(int i = 0; i < files.length; i++) {
        recursiveWalk(files[i]);
      }
    } else {
      ArgotFile af = new ArgotFile(file, new File(docDest + "/" + getFilename(file.toString()) + ".md"));
      af.start();
      // if(af.getMarkdown().size() != 0) {
      //   writeToFile(new File(docDest + "/" + af.getFilename() + ".md"), af.getMarkdown());
      // }
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

  private String getFilename(String path) {
    // Harvest filename
    String filename;
    if(path.contains("/")) {
      String[] pathArray = path.split("/");
      filename = pathArray[pathArray.length - 1];
    } else {
      filename = path;
    }
    return FilenameUtils.removeExtension(filename);
  }
}
