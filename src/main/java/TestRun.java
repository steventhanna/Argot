public class TestRun {

  public static void main(String[] args) {
    ArgotFile file = new ArgotFile("ArgotFile.java");
    System.out.println();
    ArgotFile nfile = new ArgotFile("SlashComment.java");
    System.out.println();
    ArgotFile nbfile = new ArgotFile("SlashLanguage.java");
    System.out.println();
    ArgotFile cbfile = new ArgotFile("TestRun.java");
  }
}
