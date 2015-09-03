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
    extractMethods();
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
      // Transfer body including class declaration
      for(int i = firstInstance; i < contents.size(); i++) {
        body.add(contents.get(i));
      }
    }
  }
  /** @end */

  /**
  * Find the nearest matching bracket
  * @param int lineNumber - the line number of the opening bracket
  * @return
  */
  public int nextBracket(int openBracketLineNumber, int beginCounter, int endCounter) {
    int result = 0;
    // Find next line number
    for(int i = openBracketLineNumber; i < body.size(); i++) {
      // Check the last char for a bracket.  Comments not excluded
      String content = body.get(i);
      if(content.charAt(content.length()) == '{') {
        beginCounter++;
        return nextBracket(i, beginCounter, endCounter);
      } else if(content.charAt(content.length()) == '}') {
        endCounter++;
        result = i;
        if(endCounter == beginCounter) {
          return result;
        } else {
          System.out.println("There was an error identifying the next bracket.");
        }
      } else {
        return nextBracket(openBracketLineNumber++, beginCounter, endCounter);
      }
    }
    return 0;
  }

  /**
  * Recognize a method in a given block of code.
  * Methods will always have a opening an closing parenthese.
  * Additioanlly, methods should also have a public or a private.
  */
  public void extractMethods() {
    // Gather the starting and ending position of each method
    ArrayList<Integer> startingPosition = new ArrayList<Integer>();
    ArrayList<Integer> endingPosition = new ArrayList<Integer>();
    // Search the body to find lines that have both ( and ), and either public or private
    // Although, interfaces do not need a public or private...
    for(int i = 0; i < body.size(); i++) {
      String content = body.get(i);
      if(content.length() > 0) {
        if(content.contains("(") && content.contains(")")) {
          if(content.contains("public") || content.contains("private")) {
            startingPosition.add(i);
          }
        }
        // if(content.contains("@end")) {
          // endingPosition.add(i);
        // }
      }
    }

    // Here is where it gets interesting
    // We need to match the brackets {}

    for(int i = startingPosition.get(0); i > startingPosition.size(); i++) {
      nextBracket(i);
    }


    // Check that startin position and ending position ararylists are same size
    if(startingPosition.size() == endingPosition.size()) {
      System.out.println("Starting position and Ending Position size: " + startingPosition.size());
      for(int i = 0; i < startingPosition.size(); i++) {
        ArrayList<String> methodContent = new ArrayList<String>();
        int begin = startingPosition.get(i);
        int end = endingPosition.get(i);
        while(begin < end) {
          methodContent.add(body.get(begin));
          begin++;
        }
        methods.add(new JavaMethod(methodContent));
      }
    }
  }

  /*
  public void extractMethods() {
    ArrayList<Integer> startingPosition = new ArrayList<Integer>();
    ArrayList<Integer> endingPosition = new ArrayList<Integer>();
    // Search through body to find lines that have both ( and ), and public or private
    for(int i = 0; i < body.size(); i++) {
      if(body.get(i).length() > 0) {
        if(body.get(i).contains("(") && body.get(i).contains(")")) {
          if(body.get(i).contains("public") || body.get(i).contains("private")) {
            startingPosition.add(i);
          }
        }
      }
      if(body.get(i).contains("@end")) {
        endingPosition.add(i);
      }
    }
    System.out.println("StartingPosition size: " + startingPosition.size());
    System.out.println("EndingPosition size: " + endingPosition.size());
    if(startingPosition.size() > endingPosition.size()) {
      for(int i = 0; i < endingPosition.size(); i++) {
        ArrayList<String> content = new ArrayList<String>();
        int end = endingPosition.get(i);
        int begin = startingPosition.get(i);
        while(begin < end - 1) {
          content.add(body.get(begin));
          begin++;
        }
        methods.add(new JavaMethod(content));
      }
    } else {
      for(int i = 0; i < startingPosition.size(); i++) {
        ArrayList<String> content = new ArrayList<String>();
        int end = endingPosition.get(i);
        int begin = startingPosition.get(i);
        while(begin < end - 1) {
          content.add(body.get(begin));
          begin++;
        }
        methods.add(new JavaMethod(content));
      }
    }

  }
  */
  /** @end */
}
