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
import java.io.File;

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

  /**
  * @type :: VAR
  * @name :: recursive
  * @description :: Flag to recursively walk the filetree.
  * @note :: Defaults to false
  */
  private static boolean recursiveFlag = false;

  /**
  * @type :: VAR
  * @name :: logLevel
  * @description :: Sets the systmem wide log level. Other options include
  * verbose, and quiet.
  */
  private static String logLevel = "normal";

  public static void main(String[] args) {

    Options options = new Options();
    Option parse = new Option("p", "parse", false, "parse documentation for a file or files");
    parse.setArgs(1);
    Option destination = new Option("d", "destination", false, "the destination for where the documentation should go");
    destination.setArgs(1);
    Option help = new Option("h", "help", false, "print this message");
    Option version = new Option("v", "version", false, "print the version information");
    Option recursive = new Option("r", "recursive", false, "recursively parses docs through the given dir");
    Option verbose = new Option("vb", "verbose", false, "be extra verbose");
    Option quiet = new Option("q", "quiet", false, "be extra quiet");
    options.addOption(parse);
    options.addOption(destination);
    options.addOption(help);
    options.addOption(version);
    options.addOption(recursive);
    options.addOption(verbose);
    options.addOption(quiet);

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
          docSrc = docSrc.trim();
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
            docDest = docDest.trim();
          } else {
            // Just give the same location as the src
            docDest = docSrc;
          }
        }
      }
      if(line.hasOption("verbose")) {
        logLevel = "verbose";
      }
      if(line.hasOption("quiet")) {
        logLevel = "quiet";
      }
      if(line.hasOption("recursive")) {
        recursiveFlag = true;
      }
    } catch(ParseException e) {
      System.err.println("Parsing failed. Reason: " + e.getMessage());
    }
    System.out.println("DOCSRC: " + docSrc);
    File file = new File(docSrc);
    System.out.println(file.getAbsolutePath());
    // After the CommandLineParser has finished, send info to the delegate class
    Delegate delegate = new Delegate(docSrc, docDest, logLevel, recursiveFlag);

  }
}
