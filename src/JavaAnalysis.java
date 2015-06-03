/**
* @author Steven T Hanna
* @date 5/2/15
* @class JavaAnalysis
* @description Analyze syntax of Java files
*/

import java.util.ArrayList;

public class JavaAnalysis {

  /**
  * @description The filename of the file in a String
  */
  private String filename;

  /**
  * @description Contents of the file
  */
  private ArrayList<String> contents;

  /**
  * @description System specified classname
  */
  private String systemClass;

  /**
  * @description User specified classname
  */
  private String userClass;

  /**
  * @description User specified description of class.  Array in case multi-line
  */
  private String[] description;

  /**
  * @description Constructor for JSAnalysis
  * @param DPFIle file - Predetermined JS File
  */
  public JavaAnalysis(ArgotFile file) {
    contents = file.read();
    filename = file.getFilename();
    systemClass = file.getClassname();
  }

  public void extractHeader() {
    // Look for the class declartion
    int classDeclaration = -1;
    for(int i = 0; i < contents.size(); i++) {
      if(contents.get(i).indexOf(" class") > 0) {
        classDeclaration = i;
        break;
      }
    }
    if(classDeclaration == -1) {
      // Handle error here.  Header does not exist
      System.out.println("Header does not exist");
      return;
    } else {
      ArrayList<String> header = new ArrayList<String>();
      // Strip all blank space and transfer header
      for(int i = 0; i < classDeclaration; i++) {
        if(contents.get(i) != "") {
          // System.out.println(contents.get(i));
          // For now, I am going to assume that documentation does not end in a semicolon
          String temp = contents.get(i);
          if(temp.lastIndexOf(";") != temp.length() - 1) {
            header.add(contents.get(i));
          }
        }
      }
      // for(int i = 0; i < header.size(); i++) {
      //   System.out.println(header.get(i));
      // }
    }
  }




  public static void main(String[] args) {
    ArgotFile file = new ArgotFile("/Users/steventhanna/Desktop/BiggestDir.java");
    JavaAnalysis analyze = new JavaAnalysis(file);
    analyze.extractHeader();
  }
}
