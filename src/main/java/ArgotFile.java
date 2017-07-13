/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/16/16
* @class :: ArgotFile
* @description :: A general wrapper class for file operations.
* @note :: Multithreaded
* @implements :: Runnable
*/

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;

public class ArgotFile implements Runnable {

  /**
  * @type :: VAR
  * @name :: path
  * @description :: The path of the specific file
  */
  private String path;

  /**
  * @type :: VAR
  * @name :: file
  * @description :: The file representation of the input
  */
  private File file;

  private File destination;

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

  private Thread t;

  /**
  * @type :: FUNC
  * @name :: ArgotFile
  * @description :: Constructor for ArgotFile
  * @param :: String path - the path of the given file
  */
  public ArgotFile(File file, File destination) {
    this.file = file;
    this.destination = destination;
    System.out.println(destination.toString());
    this.path = file.getAbsolutePath();
    // extractContents();
    // delegateLanguages();
  }

  /**
  * @type :: FUNC
  * @name :: run
  * @description :: Main logic when the thrad is started.
  */
  public void run() {
    System.out.println("RUNNING");
    extractContents();
    System.out.println("EXTRACTED: " + filename);
    System.out.println(contents.size());
    delegateLanguages();
    System.out.println("DELEGATED: " + filename);
    if (markdown.size() > 0) {
      System.out.println(markdown.size());
      System.out.println("WRITE");
      writeToFile();
    }
  }

  public void start() {
    System.out.println("Starting Thread");
    if(t == null) {
      t = new Thread(this, file.toString());
      t.start();
    }
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
    extension = FilenameUtils.getExtension(filename);

    // Read the actual file
    try {
      BufferedReader in = new BufferedReader(new FileReader(new File(path)));
      while(in.ready()) {
        contents.add(in.readLine());
      }
      in.close();
    } catch(IOException e) {
      System.err.println("File could not be read: " + e);
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
  */
  public void delegateLanguages() {
    if(extension.equals("")) {
      return;
    }
    Language lang;
    switch(extension) {
      case "java": {
        lang = new Language(contents, "*", "/**", "*/");
        break;
      }
      case "c": {
        lang = new Language(contents, "*", "/**", "*/");
        break;
      }
      case "cpp": {
        lang = new Language(contents, "*", "/**", "*/");
        break;
      }
      case "js": {
        lang = new Language(contents, "*", "/**", "*/");
        break;
      }
      case "py": {
        lang = new Language(contents, "#");
        break;
      }
      case "go": {
        lang = new Language(contents, "*", "/**", "*/");
        break;
      }
      default: {
        // TODO :: Throw an exception here
        System.err.println("Extension " + extension + " is not supported yet.");
        return;
      }
    }
    markdown = lang.getRenderedMarkdown();
  }

  /**
  * @type :: FUNC
  * @name :: getFilename
  * @description :: Returns the filename of the current ArgotFile.
  * @note :: Returns the filename without the extension
  * @return :: String filename - the filename of the current ArgotFile sans
  * extension
  */
  public String getFilename() {
    return FilenameUtils.removeExtension(filename);
  }

  /**
  * @type :: FUNC
  * @name :: getMarkdown
  * @description :: Returns the rendered markdown
  * @return ::  ArrayList<String> - returns the redered markdown
  */
  public ArrayList<String> getMarkdown() {
    return markdown;
  }

  /**
  * @type :: FUNC
  * @name :: getExtension
  * @description :: Returns the extension of the current file
  * @return :: String - the extension of the file
  */
  public String getExtension() {
    return extension;
  }

  /**
  * @type :: FUNC
  * @name :: writeToFile
  * @description :: Writes contents to a file
  * @param :: File dest - the destination file to write to
  * @param :: ArrayList<String> contents - the contents to be written to the file
  */
  public void writeToFile() {
    if(markdown.size() <= 0) {
      return;
    }
    try {
      if(!destination.exists()) {
        destination.createNewFile();
      }
      FileWriter fw = new FileWriter(destination.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      for(int i = 0; i < markdown.size(); i++) {
        bw.write(markdown.get(i));
      }
      bw.close();
    } catch(IOException e) {
      System.err.println("There was an error writing to the file. Error: " + e.getMessage());
    }
  }
}
