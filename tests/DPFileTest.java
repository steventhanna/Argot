public class DPFileTest {
  private DPFile file;

  public DPFileTest() {
    file = new DPFile("../README.md");
  }

  public boolean extensionTest() {
    if(file.getExtension() == "md") {
      return true;
    } else {
      return false;
    }
  }

  public static void main(String[] args) {
    DPFileTest test = new DPFileTest();
    System.out.println(test.extensionTest());
  }
}
