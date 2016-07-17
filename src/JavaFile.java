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
    System.out.println("Starting Extract Header");
    extractHeader();
    System.out.println("Finished Extract Header");
    System.out.println("Starting Harvest Header Data");
    harvestHeaderData();
    System.out.println("Finished Harvest Header Data");
    System.out.println("Started Extract Body");
    extractBody();
    System.out.println("Finished Extract Body");
    System.out.println("Starting Extract Methods");
    extractMethods();
    System.out.println("Finished Extract Methods");
    for(int i = 0; i < methods.size(); i++) {
      methods.get(i).print();
    }
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
      // Transfer the body without the class declaration
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
  public void extractMethods() {
    // Gather the starting and ending positions of each method
    ArrayList<Integer> startingPosition = new ArrayList<Integer>();
    ArrayList<Integer> endingPosition = new ArrayList<Integer>();
    // Search the body for an open bracket. That should be the beggining of a method
    for(int i = 0; i < body.size(); i++) {
      String content = body.get(i);
      // Assuming that the given line contains the {, and has one more char then that
      // then it is probably a method
      if (content.length() > 1 && content.contains("{")) {
        startingPosition.add(i);
      }
    }
    System.out.println("Got Starting Positions");

    // For each item in the starting position, iterate through and look for the final open bracket.
    // The idea is that for each {, increase, and for each } decrease. The end of the method should
    // give 0, providing the user does not make any mistakes
    for(int i = 0; i < startingPosition.size(); i++) {
      int bracketCounter = 0;
      boolean changed = false;
      for(int j = startingPosition.get(i); j < body.size(); j++) {
        System.out.println(body.get(j));
        if(body.get(j).contains("{")) {
          System.out.println("HAS BRACKET");
          bracketCounter++;
          changed = true;
        }
        if(body.get(j).contains("}")) {
          System.out.println("DOES NOT HAVE BRACKET");
          bracketCounter--;
          changed = true;
        }
        if(changed == true && bracketCounter == 0) {
          System.out.println("DONE");
          endingPosition.add(j);
          break;
        }
      }
    }
    System.out.println("Got ending positions");

    // Harvest all of the methods based off the start and end positions
    // Check to make sure the end and the start have the same length
    if(startingPosition.size() != endingPosition.size()) {
      System.out.println("There was an error getting starting and ending positions.");
      System.out.println("startingPosition Size: " + startingPosition.size());
      System.out.println("endingPosition Size: " + endingPosition.size());
    } else {
      for(int i = 0; i < startingPosition.size(); i++) {
        ArrayList<String> methodContents = new ArrayList<String>();
        for(int j = startingPosition.get(i); j < endingPosition.get(i); j++) {
          methodContents.add(body.get(j));
        }
        JavaMethod method = new JavaMethod(methodContents);
        methods.add(method);
      }
    }
  }
}
