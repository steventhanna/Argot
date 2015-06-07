/**
* @author Steven T Hanna
* @date 5/5/15
* @class ArgotFile
* @description Abstract class for a basic ArgotFile
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public abstract class ArgotFile implements FileBase {

  public ArgotFile(String pathname) {
    path = pathname;
    seperateClassnameExtension();
    read();
  }

  /**
  * @description Absolute path of file in type String
  */
  private String path;

  /**
  * @description Filename in type String
  */
  private String filename;

  /**
  * @description Extension of the file in type String
  */
  private String extension;

  /**
  * @description classname of file in type String
  */
  private String classname;

  /**
  * @description System specified class
  */
  private String systemClass;

  /**
  * @description User specified class
  */
  private String userClass;

  /**
  * @description Authors in type String array in case more than one author
  */
  private String[] author;

  /**
  * @description See in type String array in case more than one see
  */
  private String[] see;

  /**
  * @description Version of file
  */
  private String version;

  /**
  * @description Date of file
  */
  private String date;

  /**
  * @description ArrayList of type String holding contents of header
  * @note Children can modify
  */
  public ArrayList<String> header = new ArrayList<String>();

  /**
  * @description ArrayList of type String holding contents of file
  * @note Children can modify
  */
  public ArrayList<String> contents = new ArrayList<String>();

  /**
  * @description ArrayList of type String holding contents of body
  * @note Children can modify
  */
  public ArrayList<String> body = new ArrayList<String>();



  /**
  * Get Filename
  * @return String name of file
  */
  public String getFilename() {
    return filename;
  }

  /**
  * Get Complete File Path
  * @return String name of absolute path
  */
  public String getPath() {
    return path;
  }

  /**
  * Get extension
  * @return String name of extension of class
  */
  public String getExtension() {
    return extension;
  }

  /**
  * Get Classname
  * @return String name of class
  */
  public String getClassname() {
    return classname;
  }

  /**
  * Seperates the extension and the classname from the absolute path
  * @note Does not return anything, but stores in instance variables
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
  * Reads the contents of a file
  * @return ArrayList<String> of file contents
  */
  public ArrayList<String> read() {
    try {
      BufferedReader in = new BufferedReader(new FileReader(new File(path)));
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
  * Remove the tag from a specified string.
  * The tag must be included within the string
  * @param String tag - the tag to be removed
  * @param String s - the String that the tag must be removed from
  * @return String - the string sans tag
  */
  public String removeTag(String tag, String s) {
    // There should be a white space after the tag... If not the user fucked up
    // Take into account the * space
    int tagLength = tag.length() + 3;
    return s.substring(tagLength);
  }

  /**
  * Strip all exterior comments out of the header, so all that remains are the
  * standard java docs.  Then I do not have to rewrite different shit all the time
  */
  public abstract void extractHeader();

  /**
  * Extact the authors of the class
  */
  public void extractAuthor() {
    ArrayList<String> authors = new ArrayList<String>();
    for(int i = 0; i < header.size(); i++) {
      // System.out.println(header.get(i).contains("@author"));
      if(header.get(i).contains("@author")) {
        authors.add(header.get(i));
        // System.out.println(header.get(i));
      }
    }
    author = new String[authors.size()];
    for(int i = 0; i < authors.size(); i++) {
      // Remove @author
      author[i] = removeTag("@author", authors.get(i));
      System.out.println(author[i]);
    }
  }

  /**
  * Extract the user class
  * Check user class against system class... Present error to user?
  */
  // TODO Check user class against system class... Present error to user?
  public void extractClass() {
    for(int i = 0; i < header.size(); i++) {
      if(header.get(i).contains("@class")) {
        userClass = removeTag("@class", header.get(i));
      }
    }
  }

  /**
  * Extract version
  */
  public void extractVersion() {
    for(int i = 0; i < header.size(); i++) {
      if(header.get(i).contains("@version")) {
        version = removeTag("@version", header.get(i));
      }
    }
  }

  /**
  * Extrat see
  */
  public void extractSee() {
    ArrayList<String> seeList = new ArrayList<String>();
    for(int i = 0; i < header.size(); i++) {
      if(header.get(i).contains("@see")) {
        seeList.add(header.get(i));
      }
    }
    see = new String[seeList.size()];
    for(int i = 0; i < see.length; i++) {
      see[i] = removeTag("@see", seeList.get(i));
    }
  }

  /**
  * Extract the date of the class
  * If multiple occurences of date found, take last occurence
  */
  public void extractDate() {
    for(int i = 0; i < header.size(); i++) {
        if(header.get(i).contains("@date")) {
          date = removeTag("@date", header.get(i));
        }
    }
  }

  /**
  * Combined all data extraction into one method
  */
  public void harvestHeaderData() {
    extractAuthor();
    extractClass();
    extractVersion();
    extractSee();
    extractDate();
  }

  /**
  * Print all relevant information
  */
  public void printInfo() {
    System.out.println("Filename: " + filename);
    System.out.println("Path: " + path);
    System.out.println("Class: " + classname);
    System.out.println("Extension: " + extension);
    System.out.print("Author: ");
    for(int i = 0; i < author.length; i++) {
      System.out.print(author[i] + ", ");
    }
    System.out.println();
    System.out.print("See: ");
    for(int i = 0; i < see.length; i++) {
      System.out.print(see[i] + ", ");
    }
    System.out.println();
    System.out.println("Version: " + version);
    System.out.println("Date: " + date);
  }

  /**
  * Extract body
  */
  public abstract void extractBody();

  /**
  * Recognize a method in a given block of code
  */
  // TODO Make new Method class with common method shit
  public abstract void recognizeMethods();

}
