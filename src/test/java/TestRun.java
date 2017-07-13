/**
* @type :: CLASS
* @class :: TestRun
* @author :: Steven Hanna
* @date :: 1/2/34
* @description :: The class to test the unit tests with
*/
public class TestRun {

  /**
  * @type :: VAR
  * @name :: testVar
  * @description :: Just a simple test variable
  */
  private int testVar;

  /**
  * @type :: FUNC
  * @name :: testFunc
  * @description :: Just a simple test function
  * @param :: int a - first integer input
  * @param :: int b - second integer input
  * @return :: int - return 0
  */
  public int testFunc(int a, int b) {
    return 0;
  }

  /**
  * @type :: REST
  * @route :: test.com/test
  * @crud :: CREATE
  * @param :: temp - this is a test param
  * @param :: temp - this is a test param
  * @sample :: `200` - all good
  * @sample :: `404` - not found
  * @sample :: `500` - shit
  */
}
