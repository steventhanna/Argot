/**
* @author Steven T Hanna
* @date 5/2/15
* @class JSAnalysis
* @description Analyze syntax of JavaScript files
*/

public class JSAnalysis {

  /**
  * The filename of the file in a String
  */
  String filename;

  /**
  * Constructor for JSAnalysis
  * @param DPFIle file - Predetermined JS File
  */
  public JSAnalysis(DPFile file) {
    filename = file.getFilename();
  }
}
