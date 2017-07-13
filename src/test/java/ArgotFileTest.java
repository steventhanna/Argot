/**
* @type :: CLASS
* @author :: Steven Hanna
* @date :: 8/3/16
* @class :: ArgotFileTest
* @description :: Test suite for ArgotFile. Really not that much testing
* is done here, a majority of it will be done in the other sub-divisions.
* @end
*/

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;

public class ArgotFileTest {

  ArgotFile file = new ArgotFile(new File("TestRun.java"), new File("TestRun.md"));

  // @Test public void testGetFilename() {
  //   String expected = "TestRun";
  //   assertTrue(expected.equals(file.getFilename()));
  // }
  //
  // @Test public void testGetExtension() {
  //   String expected = "java";
  //   assertTrue(expected.equals(file.getExtension()));
  // }

  @Test public void testGetContents() {
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("/**");
    expected.add("* @type :: CLASS");
    expected.add("* @class :: TestRun");
    expected.add("* @author :: Steven Hanna");
    expected.add("* @date :: 1/2/34");
    expected.add("* @description :: The class to test the unit tests with");
    expected.add("*/");
    expected.add("public class TestRun {");
    expected.add("");
    expected.add("/**");
    expected.add("* @type :: VAR");
    expected.add("* @name :: testVar");
    expected.add("* @description :: Just a simple test variable");
    expected.add("*/");
    expected.add("private int testVar;");
    expected.add("");
    expected.add("/**");
    expected.add("* @type :: FUNC");
    expected.add("* @name :: testFunc");
    expected.add("* @description :: Just a simple test function");
    expected.add("* @param :: int a - first integer input");
    expected.add("* @param :: int b - second integer input");
    expected.add("* @return :: int - return 0");
    expected.add("*/");
    expected.add("public int testFunc(int a, int b) {");
    expected.add("return 0;");
    expected.add("}");
    expected.add("/**");
    expected.add("* @type :: REST");
    expected.add("* @route :: test.com/test");
    expected.add("* @description :: This is just a test");
    expected.add("* @parameter :: temp - this is a test parameter");
    expected.add("* @parameter :: temp - this is a test parameter");
    expected.add("* @sample :: `200` - all good");
    expected.add("* @sample :: `404` - not found");
    expected.add("* @sample :: `500` - shit");
    expected.add("*/");
    expected.add("}");
    ArrayList<String> actual = file.getContents();
    boolean error = true;
    for(int i = 0; i < actual.size(); i++) {
      if(!actual.get(i).equals(expected.get(i))) {
        error = false;
        break;
      }
    }
    assertTrue(error == true);
  }

  @Test public void getMarkdown() {
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("# TestRun");
    expected.add("**1/2/34 --** *Steven Hanna*");
    expected.add("");
    expected.add("### Description ");
    expected.add(" The class to test the unit tests with ");
    expected.add("");
    expected.add("## Variables ");
    expected.add("");
    expected.add("### testVar");
    expected.add("");
    expected.add("#### Description ");
    expected.add(" Just a simple test variable ");
    expected.add("");
    expected.add("## Functions ");
    expected.add("### testFunc");
    expected.add("");
    expected.add("#### Description ");
    expected.add(" Just a simple test function ");
    expected.add("");
    expected.add("");
    expected.add("#### Parameters ");
    expected.add(" ");
    expected.add("- int a - first integer input ");
    expected.add("- int b - second integer input ");
    expected.add("");
    expected.add("");
    expected.add("#### Returns ");
    expected.add("*int - return 0* ");
    ArrayList<String> actual = file.getMarkdown();
    boolean error = true;
    for(int i = 0; i < actual.size(); i++) {
      if(!actual.get(i).equals(expected.get(i))) {
        error = false;
        assertTrue(false);
        return;
      }
    }
    assertTrue(true);
  }
}
