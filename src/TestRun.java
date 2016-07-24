public class TestRun {
  public static void main(String[] args) {
    ArgotFile file = new ArgotFile("ArgotFile.java");
    // Print out the contents of the file
    for(int i = 0; i < file.getContents().size(); i++) {
      System.out.println(file.getContents().get(i));
    }
  }
}
