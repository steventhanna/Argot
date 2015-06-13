/**
* @author Steven T Hanna
* @date 5/7/15
* @class JavaFile
* @description Implementation of abstract methods for Java files.
* Includes extraction of body, header, methods, and instance variables
* @parent ArgotFile
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JavaFile extends ArgotFile {

  /**
  * Constructor for JavaFile
  * @param String path - pathname
  */
  public JavaFile(String path) {
    super(path);
    extractHeader();
    harvestHeaderData();
    extractBody();
  }
  /** @end */

  /**
  * Extract the header from the java doc
  */
  public void extractHeader() {
    // Look for the first instance of the class name
    int firstInstance = -1;
    for(int i = 0; i < contents.size(); i++) {
      // Check if line has classname, and does not contain @
      if(contents.get(i).indexOf(getClassname()) > 0 && !(contents.get(i).contains("@"))) {
        firstInstance = i;
        break;
      }
    }
    // Error handling
    if(firstInstance == -1) {
      // Class declaration does not exist... Is this a complete Java File?
      System.out.println("Class declaration does not exist.");
      return;
    } else {
      // Strip all blank space and transfer header
      for(int i = 0; i < firstInstance; i++) {
        if(contents.get(i) != "") {
          // For now, I am going to assume that documentation does not end in a semicolon
          String temp = contents.get(i);
          if(temp.lastIndexOf(";") != temp.length() - 1) {
            header.add(contents.get(i));
          }
        }
      }
    }
  }
  /** @end */

  /**
  * Extract body
  */
  public void extractBody() {
    // Extract everything else from the file excluding the header
    // Look for first instance of class name
    int firstInstance = -1;
    for(int i = 0; i < contents.size(); i++) {
      if(contents.get(i).indexOf(getClassname()) > 0 && !(contents.get(i).contains("@"))) {
        firstInstance = i;
        break;
      }
    }
    if(firstInstance == -1) {
      // Class declaration does not exist... Is this a complete Java File?
      System.out.println("Class declaration does not exist.");
      return;
    } else {
      // Transfer body including class declaration
      for(int i = firstInstance; i < contents.size(); i++) {
        body.add(contents.get(i));
      }
    }
  }
  /** @end */

  /**
  * Recognize a method in a given block of code.
  * Methods will always have a opening an closing parenthese.
  * Additioanlly, methods should also have a public or a private.
  */
  // TODO Make new Method class with common method shit
  public void extractMethods() {
    ArrayList<Integer> startingPosition = new ArrayList<Integer>();
    ArrayList<Integer> lines = new ArrayList<Integer>();
    // Search through body to find lines that have both ( and ), and public or private
    for(int i = 0; i < body.size(); i++) {
      if(body.get(i).equals("}")) {
        lines.add(i);
      }
      if(body.get(i).length() > 0) {
        if(body.get(i).contains("(") && body.get(i).contains(")")) {
          if(body.get(i).contains("public") || body.get(i).contains("private")) {
            startingPosition.add(i);
          }
        }
      }
    }


  }
  /** @end */
}
