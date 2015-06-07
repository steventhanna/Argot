/**
* @author Steven T Hanna
* @date 5/2/15
* @class ArgotFile
* @description Main file class to be called by other classes to create files with documenation
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class ArgotFile {

  /**
  * @description Path in type String
  */
  private String path;
  /**
  * @description Filename in type String
  */
  private String filename;
  /**
  * @description Filetype extension
  */
  private String extension;

  /**
  * @description Classname of file
  */
  private String classname;

  /**
  * @description Date of file
  */
  private String date;

  /**
  * @description Authors in String array in case more than one
  */
  private String[] author;

  /**
  * @description ArrayList of String holding contents of header
  */
  private ArrayList<String> header = new ArrayList<String>();

  /**
  * Get filename
  * @return filename
  */
  public String getFilename() {
    return filename;
  }

  /**
  * Get path
  * @return path
  */
  public String getPath() {
    return path;
  }

  /**
  * Get extension
  * @return extension
  */
  public String getExension() {
    return extension;
  }

  /**
  * Get classname
  * @return classname
  */
  public String getClassname() {
    return classname;
  }

  /**
  * Harvest classname and extension of file
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
  * Reads the contents of a DPFile
  * @return ArrayList<String> contents - contents of file in ArrayList
  */
  public ArrayList<String> read() {
    ArrayList<String> contents = new ArrayList<String>();
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

  public abstract void extractHeader();

  public abstract String removeTag(String tag, String s);

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






  // public static void main(String[] args) {
  //   ArgotFile file = new ArgotFile("/Users/steventhanna/Desktop/BiggestDir.java");
  //
  //   ArrayList<String> contents = new ArrayList<String>();
  //   contents = file.read();
  //   for(int i = 0; i < contents.size(); i++) {
  //     System.out.println(contents.get(i));
  //   }
  // }

}
