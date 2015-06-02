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
