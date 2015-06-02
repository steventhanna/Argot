/**
* @author Steven T Hanna
* @date 5/2/15
* @class DPFile
* @description Main file class to be called by other classes to create files with documenation
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DPFile {

  /**
  * Filename in type string
  */
  String filename;
  /**
  * Filetype extension
  */
  String extension;

  /**
  * Classname of file
  */
  String classname;

  /**
  * Default constructer
  * @param String filename - the filename of the DPFile
  */
  public DPFile(String filename) {
    this.filename = filename;
  }

  /**
  * Gets extension of file
  * @return String extension - extension of file
  */
  public String getExtension() {
    int length = filename.length();
    // Look for period
    int periodPosition = filename.lastIndexOf(".");
    extension = filename.substring(periodPosition + 1);
    return extension;
  }

  /**
  * Gets classname of file
  * @return String classname - classname of file
  */
  public String getClassname() {
    int length = filename.length();
    // Look for last occuring period
    int periodPosition = filename.lastIndexOf(".");
    classname = filename.subString(0, periodPosition);
    return classname;
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

  /**
  * Retrieve filename
  * @return filename
  */
  public String getFilename() {
    return filename;
  }



}
