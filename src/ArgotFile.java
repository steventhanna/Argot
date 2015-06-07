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
  * @description Description of a file
  */
  private String[] description;

  /**
  * @description Parent of the file
  */
  private String parent;

  /**
  * @description Children of file
  */
  private String[] children;

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
  * Get Parent
  * @return String parent of class
  */
  public String getParent() {
    return parent;
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
  * Extract the child / children of the class
  */
  public void extractChild() {
    // Find all lines with child
    ArrayList<String> temp = new ArrayList<String>();
    for(int i = 0; i < header.size(); i++) {
      if(header.get(i).contains("@child")) {
        temp.add(removeTag("@child", header.get(i)));
      }
    }
    children = new String[temp.size()];
    for(int i = 0; i < children.length; i++) {
      children[i] = temp.get(i);
    }
  }

  /**
  * Extract the parent of the class
  */
  public void extractParent() {
    for(int i = 0; i < header.size(); i++) {
      if(header.get(i).contains("@parent")) {
        parent = removeTag("@parent", header.get(i));
      }
    }
  }

  /**
  * Check a specific line for the escape character in the header
  * @note Meant to be used recursively
  * @param Integer line - the line to be checked for the escape character
  * @return Integer - the line number that contains the escape character
  */
  public int checkLineForEscapeCharHeader(int line) {
    if(!(header.get(line).contains("@"))) {
      return checkLineForEscapeCharHeader(line + 1);
    } else {
      return line;
    }
  }

  /**
  * Extract the description of the class
  * I have to probably handle multi-line inputs... Crap
  * Using an array to handle multi-line inputs
  */
  public void extractDescription() {
    // Get total amount of description lines
    // find first instance in header
    int begin = -1;
    int end = -1;
    // Determine beginning of description
    for(int i = 0; i < header.size(); i++) {
      if(header.get(i).contains("@description")) {
        begin = i;
      }
    }
    end = checkLineForEscapeCharHeader(begin + 1);
    System.out.println("Begin: " + begin);
    System.out.println("End: " + end);

    // Add Description from header lines
    if(begin != -1 && end != -1) {
      description = new String[end - begin];
      // While loop might be better
      int arrayCounter = 0;
      int headerCounter = begin;
      while(arrayCounter < description.length && headerCounter <= end) {
        description[arrayCounter] = header.get(headerCounter);
        arrayCounter++;
        headerCounter++;
      }

      // Clean up data in array
      description[0] = removeTag("@description", description[0]);
      // For rest of the array, remove the *_
      // Java specific?
      for(int i = 1; i < description.length; i++) {
        description[i] = description[i].substring(2);
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
    extractDescription();
    extractChild();
    extractParent();
  }

  /**
  * Print all relevant information
  */
  public void printInfo() {
    System.out.println("Filename: " + filename);
    System.out.println("Path: " + path);
    System.out.println("Class: " + classname);
    System.out.println("Extension: " + extension);
    System.out.println("Description: ");
    for(int i = 0; i < description.length; i++) {
      System.out.println(description[i]);
    }
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

    // // Raw Data
    // System.out.println("RAW DATA");
    // System.out.println("HEADER");
    // System.out.println();
    // for(int i = 0; i < header.size(); i++) {
    //   System.out.println(header.get(i));
    // }
    // System.out.println("BODY");
    // System.out.println();
    // for(int i = 0; i < body.size(); i++) {
    //   System.out.println(body.get(i));
    // }
  }

  /**
  * Extract body
  */
  public abstract void extractBody();

  /**
  * Recognize a method in a given block of code
  */
  // TODO Make new Method class with common method shit
  public abstract void extractMethods();

}
