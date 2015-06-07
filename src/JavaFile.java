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
  }

  /**
  * Extract the header from the java doc
  */
  public void extractHeader() {
    // Look for the first instance of the class name
    int firstInstance = -1;
    for(int i = 0; i < contents.size(); i++) {
      if(contents.get(i).indexOf(getClassname()) > 0) {
        firstInstance = i;
        break;
      }
    }
    // Error handling
    if(firstInstance == -1) {
      // Header does not exist
      System.out.println("Header does not exist");
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
}
