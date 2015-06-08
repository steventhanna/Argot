/**
* @author Steven T Hanna
* @date 5/7/15
* @class JavaMethod
* @description Specific class regarding Java methods
* @parent Method
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class JavaMethod extends Method {

  /**
  * Constructor for JavaMethod
  * @param ArrayList<String> c - entire contentes of the method
  */
  public JavaMethod(ArrayList<String> c) {
    super(c);
  }

  /**
  * @desciption Return type of method in type String as specified by method signature itself
  */
  private String returnType;

  /**
  * Extract the header from the method
  */
  public void extractHeader() {
    // Look for the first {
    int occurence = -1;
    for(int i = 0; i < contents.size(); i++) {
      if(contents.get(i).contains("{")) {
        occurence = i;
        break;
      }
    }
    // Error handling
    if(occurence == -1) {
      // Cannot locate the method... Error
      System.out.println("Method does not exist / Cannot be found");
    } else {
      setMethodSignature(contents.get(7));
      // Take into account styling of user...
      // If they put the bracket below the method
      if(contents.get(occurence).length() == 1) {
        // Shift occurence down by one to get signature
        occurence -= 1;
      }
      for(int i = 0; i <= occurence; i++) {
        header.add(contents.get(i));
      }
    }
  }

  /**
  * Extract the method signature from the rest of the method
  * Use header to get the method... Should be the last line
  */
  public void extractSignature() {
      setMethodSignature(header.get(header.size()));
  }

  /**
  * Extract the body from the method
  * Harvest everything after the method signature
  */
  public void extractBody() {

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

}
