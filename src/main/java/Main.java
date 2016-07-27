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

  /**
  * @type :: VAR
  * @name :: docSrc
  * @description :: The src file / dir to be parsed
  */
  private static String docSrc;

  /**
  * @type :: VAR
  * @name :: docDest
  * @description :: The destination to place the parsed input files.
  */
  private static String docDest;

  public static void main(String[] args) {

    Options options = new Options();
    Option parse = new Option("p", "parse", false, "parse documentation for a file or files");
    parse.setArgs(1);
    Option destination = new Option("d", "destination", false, "the destination for where the documentation should go");
    destination.setArgs(1);
    Option help = new Option("h", "help", false, "print this message");
    Option version = new Option("v", "version", false, "print the version information");
    Option recursive = new Option("r", "recursive", false, "recursively parses docs through the given dir");
    options.addOption(parse);
    options.addOption(destination);
    options.addOption(help);
    options.addOption(version);
    options.addOption(recursive);

    // Create the parser
    CommandLineParser parser = new DefaultParser();
    try {
      // Parse the command line arguments
      CommandLine line = parser.parse(options, args);
      if(line.hasOption("help")) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Argot", options);
      }
      if(line.hasOption("parse")) {
        String[] filepath = line.getOptionValues("parse");
        // Just take the first path
        if(filepath.length > 0) {
          docSrc = filepath[0];
        } else {
          throw new ParseException("No SRC files given.");
        }

      }
      if(line.hasOption("destination")) {
        if(!line.hasOption("parse")) {
          throw new ParseException("No SRC files given.");
        } else {
          String[] dest = line.getOptionValues("destination");
          // Take the first destination
          if(dest.length > 0) {
            docDest = dest[0];
          }  
        }
      }
    } catch(ParseException e) {
      System.err.println("Parsing failed. Reason: " + e.getMessage());
    }
  }
}
