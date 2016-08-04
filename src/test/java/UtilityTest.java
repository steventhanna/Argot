/**
* @type :: CLASS
* @author :: Steven Hanna
* @date :: 8/3/16
* @class :: UtilityTest
* @description :: Test suite for Utility.
*/

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;

public class UtilityTest {

  @Test public void testTrim() {
    String expected = "test";
    assertTrue(expected.equals(Utility.removeWhitespace("  test  ")));
  }
  
}
