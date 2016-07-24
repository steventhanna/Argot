/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/16/16
* @class :: ArgotFile
* @description :: A general wrapper class for file operations.
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ArgotFile {

  /**
  * @type :: VAR
  * @name :: path
  * @description :: The path of the specific file
  */
  private String path;

  /**VAR
  * @type :: VAR
  * @name :: filename
  * @description :: The filename of the specific file
  */
  private String filename;

  /**
  * @type :: VAR
  * @name :: extension
  * @description :: The extension of the file
  */
  private String extension;

  /**
  * @type :: VAR
  * @name :: contents
  * @description :: The contents of the file
  */
  private ArrayList<String> contents = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: markdown
  * @description :: the raw Markdown representation of the file
  */
  private ArrayList<String> markdown = new ArrayList<String>();

  /**
  * @type :: FUNC
  * @name :: ArgotFile
  * @description :: Constructor for ArgotFile
  * @param :: String path - the path of the given file
  */
  public ArgotFile(String path) {
    this.path = path;
    extractContents();
    delegateLanguages();
  }

  /**
  * @type :: FUNC
  * @name :: extractContents
  * @description :: Extracts the contents from the given filename.
  * This is where the actual file is read
  */
  public void extractContents() {
    // Harvest filename
    if(path.contains("/")) {
      String[] pathArray = path.split("/");
      filename = pathArray[pathArray.length - 1];
    } else {
      filename = path;
    }
    // Harvest extension
    System.out.println("FILENAME: " + filename);
    int periodLoc = filename.indexOf(".");
    extension = filename.substring(periodLoc + 1);

    // Read the actual file
    try {
      BufferedReader in = new BufferedReader(new FileReader(new File(path)));
      while(in.ready()) {
        contents.add(in.readLine());
      }
      in.close();
    } catch(IOException e) {
      System.out.println("File could not be read: " + e);
    }
  }

  /**
  * @type :: FUNC
  * @name :: getContents
  * @description :: Getter method for the raw contents of the file
  * @return :: ArrayList<String> - the raw content of the file
  */
  public ArrayList<String> getContents() {
    return contents;
  }

  /**
  * @type :: FUNC
  * @name :: delegateLanguages
  * @description :: Determines through a switch statement the type of
  * commenting language used in the langauge.  From there, the
  * appropriate worker can be started
  * @return :: ArrayList<String> - the Markdown representation of the class
  */
  public ArrayList<String> delegateLanguages() {
    switch(extension) {
      case "java": {
        SlashLanguage slash = new SlashLanguage(contents);
        // return slash.generateMarkdown();
        break;
      }
      case "c": {
        SlashLanguage slash = new SlashLanguage(contents);
        // return slash.generateMarkdown();
        break;
      }
      case "cpp": {
        SlashLanguage slash = new SlashLanguage(contents);
        // return slash.generateMarkdown();
        break;
      }
      case "js": {
        SlashLanguage slash = new SlashLanguage(contents);
        // return slash.generateMarkdown();
        break;
      }
      default: {
        // TODO :: Throw an exception here
        System.out.println("Extension " + extension + " is not supported yet.");
        return null;
      }
    }
    return null;
  }
}
