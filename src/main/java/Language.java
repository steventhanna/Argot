/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/25/16
* @class :: Language
* @description :: An abstract class to make sure that future commenting languages
* all follow the same format.
* @end
*/

import java.util.ArrayList;


public class Language {

  /**
  * @type :: VAR
  * @name :: rawComments
  * @description :: String Representaion of the comments
  * @end
  */
  public ArrayList<String> rawComments = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: comments
  * @description :: Object representation of comments
  * @end
  */
  public ArrayList<Comment> comments = new ArrayList<Comment>();

  /**
  * @type :: VAR
  * @name :: functions
  * @description :: Oject representation of all the function comments. Used
  * as staging before Markdown creation.
  * @end
  */
  public ArrayList<FunctionComment> functions = new ArrayList<FunctionComment>();

  /**
  * @type :: VAR
  * @name :: variables
  * @description :: Object representation of all the variable comments.  Used
  * as a staging before Markdown creation.
  * @end
  */
  public ArrayList<VariableComment> variables = new ArrayList<VariableComment>();

  /**
  * @type :: VAR
  * @name :: classes
  * @description :: Object representation of all the class comments.  Used
  * as a staging before Markdown creation.
  * @end
  */
  public ArrayList<ClassComment> classes = new ArrayList<ClassComment>();

  /**
  * @type :: VAR
  * @name :: endpoints
  * @desription :: Object representation of all the endpoint comments.  Used as a
  * staging before Markdown creation
  * @end
  */
  public ArrayList<EndpointComment> endpoints = new ArrayList<EndpointComment>();
  /**
  * @type :: VAR
  * @name :: renderedMarkdown
  * @description :: The rendered markdown in the order that the comments appear in the target file.
  * @end
  */
  private ArrayList<String> renderedMarkdown = new ArrayList<String>();

  /**
  * @type :: FUNC
  * @name :: Language
  * @description :: The most general language constructor, takes the raw contents of the file and the commenting style
  * @param :: ArrayList<String> contents - the raw contents of the file to be parsed
  * @param :: String commentStyle - the comment style that is used to identify comments in the specific
  * file
  * @end
  */
  public Language(ArrayList<String> contents, String commentStyle) {
    extractComments(contents, commentStyle);
    delegateComments();
    renderMarkdown();
  }

  /**
  * @type :: FUNC
  * @name :: Language
  * @description :: A specialized language constructor. Specifies a begin and end comment style, like
  * traditional JavaDocs.
  * @param :: ArrayList<String> contents - the raw contents of the file to be parsed
  * @param :: String commentStyle - the comment style that is used to identify comments in the specific
  * file
  * @param :: String beginStyle - the beginning style of a comment. For Java it would be `/**`
  * @param :: String endStyle - the ending style of a comment. No demo bc it would mess up the comment
  * @end
  */
  public Language(ArrayList<String> contents, String commentStyle, String beginStyle, String endStyle) {
    extractComments(contents, commentStyle, beginStyle, endStyle);
    delegateComments();
    renderMarkdown();
  }

  /**
  * @type :: FUNC
  * @name :: Language
  * @description :: A specialized language constructor. Specifies a begin and end comment style, like
  * traditional JavaDocs, in addition to end of a comment for HTML like files.
  * @param :: ArrayList<String> contents - the raw contents of the file to be parsed
  * @param :: String commentStyle - the comment style that is used to identify comments in the specific
  * file
  * @param :: String beginStyle - the beginning style of a comment. For Java it would be `/**`
  * @param :: String endStyle - the ending style of a comment. No demo bc it would mess up the comment
  * @param ;: String commentEndStyle - the end of a comment style `<!-- test -->`. In that case it would be `-->`
  * @end
  */
  public Language(ArrayList<String> contents, String commentStyle, String beginStyle, String endStyle, String commentEndStyle) {
    extractComments(contents, commentStyle, beginStyle, endStyle, commentEndStyle);
    delegateComments();
    renderMarkdown();
  }

  /**
  * @type :: FUNC
  * @name :: delegateComments
  * @description :: Based on the type of the comment, build the associated
  * object.
  * @end
  */
  public void delegateComments() {
    for(int i = 0; i < comments.size(); i++) {
      // Look for the tag
      String type = comments.get(i).getType();
      // Check to make sure the tag exists
      if(type != null) {
        // Lowercase for consistency
        type = type.toLowerCase();
        switch(type) {
          case "func": {
            FunctionComment function = new FunctionComment(comments.get(i).getCleanedComments());
            functions.add(function);
            break;
          }
          case "var": {
            VariableComment variable = new VariableComment(comments.get(i).getCleanedComments());
            variables.add(variable);
            break;
          }
          case "class": {
            ClassComment classComment = new ClassComment(comments.get(i).getCleanedComments());
            classes.add(classComment);
            break;
          }
          case "rest": {
            EndpointComment endpointComment = new EndpointComment(comments.get(i).getCleanedComments());
            endpoints.add(endpointComment);
            break;
          }
          default: {
            System.err.println("The Type " + type + " is not yet supported.");
          }
        }
      }
    }
  }

  /**
  * @type :: FUNC
  * @name :: extractComments
  * @description :: Extract the comment strings from the raw content.
  * For now, the extracted comments both get added to individual comment
  * objects as well as the raw ArrayList
  * @param :: ArrayList<String> - the contents of the file
  * @param :: String commentStyle - the identifier for the parser to take out
  * @end
  */
  public void extractComments(ArrayList<String> contents, String commentStyle) {
    for(int i = 0; i < contents.size(); i++) {
      ArrayList<String> commentBuffer = new ArrayList<String>();
      // Check if a valid comment
      if(contents.get(i).trim().length() >= commentStyle.length() && contents.get(i).trim().substring(0, commentStyle.length()).equals(commentStyle) && contents.get(i).contains("@type")) {
        // Continue adding comments until a end tag is found
        rawComments.add(contents.get(i));
        commentBuffer.add(contents.get(i));
        for(int j = i + 1; j < contents.size(); j++) {
          if(contents.get(j).contains(commentStyle) && contents.get(j).contains("@end") == false) {
            rawComments.add(contents.get(j));
            commentBuffer.add(contents.get(j));
          } else if(contents.get(j).contains(commentStyle) && contents.get(j).contains("@end")) {
            rawComments.add(contents.get(j));
            commentBuffer.add(contents.get(j));
            comments.add(new Comment(commentBuffer, commentStyle));
            i = j;
            break;
          } else {
            i = j;
            break;
          }
        }
      }
    }
  }

  /**
  * @type :: FUNC
  * @name :: extractComments
  * @description :: Extract the comment strings from the raw content.
  * For now, the extracted comments both get added to individual comment
  * objects as well as the raw ArrayList
  * @param :: ArrayList<String> - the contents of the file
  * @param :: String commentStyle - the identifier for a comment
  * @param :: String beginStyle - the identifier for a beginning comment set if the comments beginning, middle,
  * and end are different.
  * @param :: String endStyle - the identifier for a ending comment set if the comment beginning, middle, and end
  * are different.
  * @end
  */
  public void extractComments(ArrayList<String> contents, String commentStyle, String beginStyle, String endStyle) {
    for(int i = 0; i < contents.size(); i++) {
      ArrayList<String> commentBuffer = new ArrayList<String>();
      // Check if a valid comment
      // System.out.println(contents.get(i));
      // System.out.println(contents.get(i).trim().length() >= beginStyle.length());
      // System.out.println(contents.get(i).trim().substring(0, beginStyle.length()).equals(beginStyle));
      // System.out.println(contents.get(i).trim().substring(0, beginStyle.length()));
      if (contents.get(i).trim().length() >= beginStyle.length() && contents.get(i).trim().substring(0, beginStyle.length()).equals(beginStyle)) {
        // Continue adding comments until a end tag is found
        rawComments.add(contents.get(i));
        commentBuffer.add(contents.get(i));
        for(int j = i + 1; j < contents.size(); j++) {
          if(contents.get(j).contains(endStyle)) {
            rawComments.add(contents.get(j));
            commentBuffer.add(contents.get(j));
            comments.add(new Comment(commentBuffer, commentStyle, beginStyle, endStyle));
          } else if (contents.get(j).contains(commentStyle)) {
            rawComments.add(contents.get(j));
            commentBuffer.add(contents.get(j));
          } else {
            i = j;
            break;
          }
        }
      }
    }
  }

  /**
  * @type :: FUNC
  * @name :: extractComments
  * @description :: Extract the comment strings from the raw content.
  * For now, the extracted comments both get added to individual comment
  * objects as well as the raw ArrayList
  * @param :: ArrayList<String> - the contents of the file
  * @param :: String commentStyle - the identifier for a comment
  * @param :: String beginStyle - the identifier for a beginning comment set if the comments beginning, middle,
  * and end are different.
  * @param :: String endStyle - the identifier for a ending comment set if the comment beginning, middle, and end
  * are different.
  * @param :: String commentEndStyle - the identifier for a comment ending such as the `-->` in HTML
  * @end
  */
  public void extractComments(ArrayList<String> contents, String commentStyle, String beginStyle, String endStyle, String commentEndStyle) {
    for(int i = 0; i < contents.size(); i++) {
      ArrayList<String> commentBuffer = new ArrayList<String>();
      // Check if a valid comment
      if (contents.get(i).trim().length() >= beginStyle.length() && contents.get(i).trim().substring(0, beginStyle.length()).equals(beginStyle)) {
        // Continue adding comments until a end tag is found
        rawComments.add(contents.get(i));
        commentBuffer.add(contents.get(i));
        for(int j = i + 1; j < contents.size(); j++) {
          if (contents.get(j).contains(endStyle)) {
            rawComments.add(contents.get(j));
            commentBuffer.add(contents.get(j));
            comments.add(new Comment(commentBuffer, commentStyle, beginStyle, endStyle, commentEndStyle));
            i = j;
            break;
          } else if (contents.get(j).contains(commentStyle)) {
            rawComments.add(contents.get(j));
            commentBuffer.add(contents.get(j));
          } else {
            i = j;
            break;
          }
        }
      }
    }
  }

  /**
  * @type :: FUNC
  * @name :: getRawComments
  * @description :: Getter for the rawComment ArrayList
  * @return :: ArrayList<String> - the comments to return
  * @end
  */
  public ArrayList<String> getRawComments() {
    return rawComments;
  }

  /**
  * @type :: FUNC
  * @name :: renderMarkdown
  * @description :: Renders the markdown for the language
  * @end
  */
  public void renderMarkdown() {
    if(classes.size() > 0) {
      for(int i = 0; i < classes.size(); i++) {
        renderedMarkdown.add(classes.get(i).generateMarkdown());
      }
      renderedMarkdown.add("\n");
    }
    if (endpoints.size() > 0) {
      renderedMarkdown.add("## REST Endpoints \n");
      for(int i = 0; i < endpoints.size(); i++) {
        renderedMarkdown.add(endpoints.get(i).generateMarkdown());
      }
      renderedMarkdown.add("\n");
    }
    if(variables.size() > 0) {
      renderedMarkdown.add("## Variables \n");
      for(int i = 0; i < variables.size(); i++) {
        renderedMarkdown.add(variables.get(i).generateMarkdown());
      }
      renderedMarkdown.add("\n");
    }
    if(functions.size() > 0) {
      renderedMarkdown.add("## Functions \n");
      for(int i = 0; i < functions.size(); i++) {
        renderedMarkdown.add(functions.get(i).generateMarkdown());
      }
    }
  }

  /**
  * @type :: FUNC
  * @name :: getRenderedMarkdown
  * @description :: Returns the rendered markdown
  * @return ;: ArrayList<String> - the rendered markdown of the file
  * @end
  */
  public ArrayList<String> getRenderedMarkdown() {
    for(int i = 0; i < renderedMarkdown.size(); i++) {
      System.out.println(renderedMarkdown.get(i));
    }
    return renderedMarkdown;
  }

}
