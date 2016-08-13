/**
* @type :: CLASS
* @author :: Steven T Hanna
* @date :: 7/25/16
* @class :: Language
* @description :: An abstract class to make sure that future commenting languages
* all follow the same format.
*/

import java.util.ArrayList;


public abstract class Language {

  /**
  * @type :: VAR
  * @name :: rawComments
  * @description :: String Representaion of the comments
  */
  public ArrayList<String> rawComments = new ArrayList<String>();

  /**
  * @type :: VAR
  * @name :: comments
  * @description :: Object representation of comments
  */
  public ArrayList<SlashComment> comments = new ArrayList<SlashComment>();

  /**
  * @type :: VAR
  * @name :: functions
  * @description :: Oject representation of all the function comments. Used
  * as staging before Markdown creation.
  */
  public ArrayList<FunctionComment> functions = new ArrayList<FunctionComment>();

  /**
  * @type :: VAR
  * @name :: variables
  * @description :: Object representation of all the variable comments.  Used
  * as a staging before Markdown creation.
  */
  public ArrayList<VariableComment> variables = new ArrayList<VariableComment>();

  /**
  * @type :: VAR
  * @name :: classes
  * @description :: Object representation of all the class comments.  Used
  * as a staging before Markdown creation.
  */
  public ArrayList<ClassComment> classes = new ArrayList<ClassComment>();

  /**
  * @type :: VAR
  * @name :: renderedMarkdown
  * @description :: The rendered markdown in the order that the comments appear in the target file.
  */
  private ArrayList<String> renderedMarkdown = new ArrayList<String>();

  /**
  * @type :: FUNC
  * @name :: delegateComments
  * @description :: Based on the type of the comment, build the associated
  * object.
  */
  public abstract void delegateComments();

  /**
  * @type :: FUNC
  * @name :: extractComments
  * @description :: Extract the comment strings from the raw content.
  * For now, the extracted comments both get added to individual comment
  * objects as well as the raw ArrayList
  * @param :: ArrayList<String> - the contents of the file
  */
  public abstract void extractComments(ArrayList<String> contents);

  /**
  * @type :: FUNC
  * @name :: getRawComments
  * @description :: Getter for the rawComment ArrayList
  * @return :: ArrayList<String> - the comments to return
  */
  public ArrayList<String> getRawComments() {
    return rawComments;
  }

  /**
  * @type :: FUNC
  * @name :: renderMarkdown
  * @description :: Renders the markdown for the language
  */
  public void renderMarkdown() {
    if(classes.size() > 0) {
      for(int i = 0; i < classes.size(); i++) {
        renderedMarkdown.add(classes.get(i).generateMarkdown());
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
  */
  public ArrayList<String> getRenderedMarkdown() {
    return renderedMarkdown;
  }

}
