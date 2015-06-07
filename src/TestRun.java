import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestRun {

  /**
  * Reads the contents of a file
  * @return ArrayList<String> of file contents
  */
  public static ArrayList<String> read(String path) {
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

  public static void main(String[] args) {
      // JavaFile file = new JavaFile("/Users/steventhanna/Desktop/BiggestDir.java");
      // System.out.println(file.getExtension());
      // file.printInfo();
      JavaMethod method = new JavaMethod(read("/Users/steventhanna/Desktop/Programming/Java/Argot/tests/JavaMethodTest.java"));
      // method.extractHeader();
      for(int i = 0; i < method.header.size(); i++) {
        System.out.println(method.header.get(i));
      }

  }
}
