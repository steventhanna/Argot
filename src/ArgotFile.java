/**
* @author Steven T Hanna
* @date 5/2/15
* @class ArgotFile
* @description Main file class to be called by other classes to create files with documenation
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ArgotFile {

  /**
  * @description Path in type String
  */
  String path;
  /**
  * @description Filename in type String
  */
  String filename;
  /**
  * @description Filetype extension
  */
  String extension;

  /**
  * @description Classname of file
  */
  String classname;

  /**
  * Default constructer
  * @param String pathName - the complete path of the ArgotFile
  */
  public ArgotFile(String pathName) {
    path = pathName;
  }

  /**
  * Get filename
  * @return filename
  */
  public String getFilename() {
    return filename;
  }

  /**
  * Get path
  * @return path
  */
  public String getPath() {
    return path;
  }

  /**
  * Get extension
  * @return extension
  */
  public String getExension() {
    return extension;
  }

  /**
  * Get classname
  * @return classname
  */
  public String getClassname() {
    return classname;
  }

  /**
  * Harvest classname and extension of file
  */
  public void seperateClassnameExtension() {
    // Split path at /
    String[] pathSplit = path.split("/");
    String complete = pathSplit[pathSplit.length - 1];
    // Get period position to seperate extension
    int periodPosition = complete.lastIndexOf(".");
    extension = complete.substring(periodPosition + 1);
    classname = complete.substring(0, periodPosition);
  }

  /**
  * Reads the contents of a DPFile
  * @return ArrayList<String> contents - contents of file in ArrayList
  */
  public ArrayList<String> read() {
    ArrayList<String> contents = new ArrayList<String>();
    try {
      BufferedReader in = new BufferedReader(new FileReader(filename));
      while(in.ready()) {
        contents.add(in.readLine());
      }
      in.close();
      return contents;
    } catch(IOException e) {
      System.out.println("File could not be read: " + e);
    }
    return null;
  }

}
