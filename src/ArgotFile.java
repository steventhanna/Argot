/**
* @author :: Steven T Hanna
* @date :: 7/16/16
* @class ::  ArgotFile
* @description :: A general wrapper class for the file operations
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ArgotFile {

  /**
  * @description :: The path of the specific file
  */
  private String path;
  /**
  * @description :: The filename of the specific file
  */
  private String filename;
  /**
  * @description :: The extension of the file
  */
  private String extension;

  /**
  * @description :: The contents of the file
  */
  private ArrayList<String> contents = new ArrayList<String>();



  /**
  * @description :: The body of the file
  */
  private ArrayList<String> body = new ArrayList<String>();

  /**
  * Constructor for ArgotFile
  * @param :: String path - the path of the given file
  */
  public ArgotFile(String path) {
    this.path = path;
    extractContents();
  }

  /**
  * Extracts the contents from the filename
  */
  public void extractContents() {
    // Harvest filename
    String[] pathArray = path.split("/");
    filename = pathArray[pathArray.length - 1];
    String[] filenameArray = filename.split(".");
    extension = filenameArray[filenameArray.length - 1];

    // Read the actual file
    try {
      BufferedReader in = new BufferedReader(new FileReader(new File(path)));
      while(in.ready()) {
        contents.add(in.readLine());
      }
      in.close();
    } catch(IOException e) {
      System.out.println("File could not be read: " + e);
    }
  }
}
