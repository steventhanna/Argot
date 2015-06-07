/**
* @author Steven T Hanna
* @date 5/7/15
* @class Method
* @description Abstract class to base other method class's off of
* @child JavaMethod
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Method {

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
  * @desciption Description of the method in String array
  */
  private String[] description;

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
  * Get method name
  * @return String - method name
  */
  public String getName() {
    return name;
  }

  /**
  * Set method name
  * @param String name - method name
  */
  public void setName(String name) {
    this.name = name;
  }

  /**
  * Get method signature
  * @return String - method signature
  */
  public String getMethodSignature() {
    return methodSignature;
  }

  /**
  * Set method signature
  * @param MString methodSignature - method signature
  */
  public void setMethodSignature(String methodSignature) {
    this.methodSignature = methodSignature;
  }

  /**
  * Get parameters
  * @return String array - parameters of method
  */
  public String[] getParameters() {
    return parameters;
  }

  /**
  * Set parameters
  * @param String array params - parameters of method
  */
  public void setParameters(String[] params) {
    parameters = params;
  }

  /**
  * Get description
  * @return String array - multi-line description of method
  */
  public String[] getDescription() {
    return description;
  }

  /**
  * Set description
  * @param String array - multi-line description of method
  */
  public void setDescription(String[] description) {
    this.description = description;
  }

  /**
  * Get date
  * @return String - date of method
  */
  public String getDate() {
    return date;
  }

  /**
  * Set date
  * @param String - date of method
  */
  public void setDate(String date) {
    this.date = date;
  }

  /**
  * Get returned
  * @return String - what method returns
  */
  public String getReturned() {
    return returned;
  }

  /**
  * Set returned
  * @param String - set return
  */
  public void setReturned(String returned) {
    this.returned = returned;
  }

  /**
  * Get Thrown
  * @return String - what method throws
  */
  public String getThrown() {
    return thrown;
  }

  /**
  * Set thrown
  * @param String - set thrown
  */
  public void setThrown(String thrown) {
    this.thrown = thrown;
  }

  /**
  * Get Exception
  * @return String - what exceptions the method has
  */
  public String getException() {
    return exception;
  }

  /**
  * Set exception
  * @param String - set exception
  */
  public void setException(String exception) {
    this.exception = exception;
  }

  /**
  * Get See
  * @return String array - see for other methods in the header
  */
  public String[] getSee() {
    return see;
  }

  /**
  * Set see
  * @param String array - set see
  */
  public void setSee(String[] see) {
    this.see = see;
  }

  /**
  * Get notes
  * @return String array - notes for the method header
  */
  public String[] getNote() {
    return note;
  }

  /**
  * Set notes
  * @param String array - set notes
  */
  public void setNote(String[] note) {
    this.note = note;
  }

  /**
  * Extract the header from the method
  */
  public abstract void extractHeader();

  /**
  * Extract the method signature from the rest of the method
  * Use header to get the method... Should be the last line
  */
  public abstract void exstractSignature();

  /**
  * Extract the body from the method
  * Harvest everything after the method signature
  */
  public abstract void extractBody();

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
