/**
* @type :: CLASS
* @author :: Steven Hanna
* @date :: 8/3/16
* @class :: SlashLanguageTest
* @description :: Test suite for SlashLanguage. Should test the stripping
* and comment handling of the SlashLanguage class
*/

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;

public class SlashLanguageTest {

  /**
  * @type :: VAR
  * @name :: file
  * @description :: The main ArgotFile to be used during testing
  */
  ArgotFile file;

  /**
  * @type :: VAR
  * @name :: contents
  * @description :: ArrayList of the manually entered contents to test against
  */
  ArrayList<String> contents;

  /**
  * @type :: VAR
  * @name :: simplyComments
  * @description :: Just the comments, none of the actual code
  */
  ArrayList<String> simplyComments;


  @Before public void beforeTest() {
    file = new ArgotFile(new File("TestRun.java"));
    contents = new ArrayList<String>();
    // Add the contents of the TestRun to the ArrayList
    contents.add("/**");
    contents.add("* @type :: CLASS");
    contents.add("* @class :: TestRun");
    contents.add("* @author :: Steven Hanna");
    contents.add("* @date :: 1/2/34");
    contents.add("* @description :: The class to test the unit tests with");
    contents.add("*/");
    contents.add("public class TestRun {");
    contents.add("");
    contents.add("  /**");
    contents.add("  * @type :: VAR");
    contents.add("  * @name :: testVar");
    contents.add("  * @description :: Just a simple test variable");
    contents.add("  */");
    contents.add("  private int testVar;");
    contents.add("");
    contents.add("  /**");
    contents.add("  * @type :: FUNC");
    contents.add("  * @name :: testFunc");
    contents.add("  * @description :: Just a simple test function");
    contents.add("  * @param :: int a - first integer input");
    contents.add("  * @param :: int b - second integer input");
    contents.add("  * @return :: int - return 0");
    contents.add("  */");
    contents.add("  public int testFunc(int a, int b) {");
    contents.add("    return 0;");
    contents.add("  }");
    contents.add("");
    contents.add("}");

    simplyComments.add("/**");
    simplyComments.add("* @type :: CLASS");
    simplyComments.add("* @class :: TestRun");
    simplyComments.add("* @author :: Steven Hanna");
    simplyComments.add("* @date :: 1/2/34");
    simplyComments.add("* @description :: The class to test the unit tests with");
    simplyComments.add("*/");
    simplyComments.add("/**");
    simplyComments.add("* @type :: VAR");
    simplyComments.add("* @name :: testVar");
    simplyComments.add("* @description :: Just a simple test variable");
    simplyComments.add("*/");
    simplyComments.add("/**");
    simplyComments.add("* @type :: FUNC");
    simplyComments.add("* @name :: testFunc");
    simplyComments.add("* @description :: Just a simple test function");
    simplyComments.add("* @param :: int a - first integer input");
    simplyComments.add("* @param :: int b - second integer input");
    simplyComments.add("* @return :: int - return 0");
    simplyComments.add("*/");

  }

  @Test public void testContents() {
    SlashLanguage lang = new SlashLanguage(file.getContents());
    boolean ok = true;
    ArrayList<String> contentToCheck = lang.getRawComments();
    for(int i = 0; i < contentToCheck.size(); i++) {
      if(contentToCheck.get(i).equals(contents.get(i)) == false) {
        ok = false;
        break;
      }
    }
    assertTrue(ok);
  }

  @Test public void testExtractComments() {
    SlashLanguage lang = new SlashLanguage(file.getContents());
    boolean ok = true;
    ArrayList<String> toCheck = lang.getCleanedComments();
  }

    @Test public void testGetFilename() {
      ArgotFile file = new ArgotFile(new File("/src/test/java/TestRun.java"));
      String expected = "TestRun";
      System.out.println(expected.equals(file.getFilename()));
      assertTrue(expected.equals(file.getFilename()));
    }

}
