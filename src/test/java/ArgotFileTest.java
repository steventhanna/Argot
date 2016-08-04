/**
* @type :: CLASS
* @author :: Steven Hanna
* @date :: 8/3/16
* @class :: ArgotFileTest
* @description :: Test suite for ArgotFile. Really not that much testing
* is done here, a majority of it will be done in the other sub-divisions.
*/

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;

public class ArgotFileTest {

    @Test public void testGetFilename() {
      ArgotFile file = new ArgotFile(new File("TestRun.java"));
      String expected = "TestRun";
      assertTrue(expected.equals(file.getFilename()));
    }

}
