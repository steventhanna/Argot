/**
* @author Steven T Hanna
* @date 5/7/15
* @class JavaMethod
* @description Specific class regarding Java methods
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class JavaMethod {

  /**
  * @desciption Name of method as a String
  */
  private String name;

  /**
  * @description Method signature
  */
  private String methodSignature;

  /**
  * @desciption Parameters of method in String array
  */
  private String[] parameters;

  /**
  * @desciption Return type of method in type String as specified by method signature itself
  */
  private String returnType;

  /**
  * @desciption Description of the method in String array
  */
  private String[] desciption;

  /**
  * @description Date of method
  */
  private String date;

  /**
  * @description What is returned from method as a String
  */
  private String returned;

  /**
  * @description What method throws as a String
  */
  private String thrown;


  /**
  * @description Exceptions of the method
  */
  private String exception;

  /**
  * @description See related as a String array
  */
  private String[] see;

  /**
  * @description Notes as a String array
  */
  private String[] note;

  /**
  * @description Body of the method
  */
  public ArrayList<String> body = new ArrayList<String>();

  /**
  * @description Header of the Method
  */
  public ArrayList<String> header = new ArrayList<String>();

  /**
  * @description Entire contents of the method
  */
  public ArrayList<String> contents = new ArrayList<String>();

  /**
  * Constructor for JavaMethod
  * @param ArrayList<String> c - entire contentes of the method
  */
  public JavaMethod(ArrayList<String> c) {
    contents = c;
    extractHeader();
    System.out.println(methodSignature);
  }

  /**
  * Extract the header from the method
  */
  public void extractHeader() {
    // Look for the first {
    int occurence = -1;
    for(int i = 0; i < contents.size(); i++) {
      if(contents.get(i).contains("{")) {
        // System.out.println(contents.get(i).contains("{"));
        occurence = i;
        // System.out.println(occurence);
        break;
      }
    }
    // Error handling
    if(occurence == -1) {
      // Cannot locate the method... Error
      System.out.println("Method does not exist / Cannot be found");
    } else {
      methodSignature =  contents.get(7);
      // Take into account styling of user...
      // If they put the bracket below the method
      if(contents.get(occurence).length() == 1) {
        // Shift occurence down by one to get signature
        occurence -= 1;
      }
      for(int i = 0; i <= occurence; i++) {
        // System.out.println(contents.get(i));
        header.add(contents.get(i));
      }
    }
  }

  /**
  * Extract the method signature from the rest of the method
  */
  public void extractSignature() {

  }

  /**
  * Extract the body from the method
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
