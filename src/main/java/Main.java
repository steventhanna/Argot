/**
* @type :: CLASS
* @class :: Main
* @author :: Steven Hanna
* @date :: 7/26/16
* @description :: The main class that houses the CLI, implements threading and
* other workers to manage all of the File IO logic and whatnot.
*/

import java.util.ArrayList;
import org.apache.commons.cli.*;

public class Main {
  public static void main(String[] args) {

    Options options = new Options();
    Option help = new Option("help", "print this message");
    Option version = new Option("version", "printn the version information");
    Option recursive = new Option("recursive", "recursively parses docs through the given dir");
    options.addOption(help);
    options.addOption(version);
    options.addOption(recursive);

    // Create the parser
    CommandLineParser parser = new DefaultParser();
    try {
      // Parse the command line arguments
      CommandLine line = parser.parse(options, args);
    } catch(ParseException e) {
      System.err.println("Parsing failed. Reason: " + e.getMessage()));
    }

  }
}
