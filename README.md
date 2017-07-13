# Argot
![Status](https://travis-ci.org/steventhanna/Argot.svg?branch=master)

Parse documentation from codebases into markdown for easy doc creation. Argot is inspired by the traditional JavaDoc system.

## Argot in Action
Check out [Argot's Wiki](https://github.com/steventhanna/Argot/wiki) where Argot is run against this repo.

## Installation (for stable use) (manually)
- Download the latest [release](https://github.com/steventhanna/Argot/releases)
- Move the jar to `/usr/local`
- Add argot to your path. An example:

```
export PATH="/usr/local/argot"
```

- Then add an alias

```
alias argot="java -jar /usr/local/argot.jar"
```

**NOTE:** On MacOS, all these changes took place in my `.profile`

- Then reload your .profile in the terminal
```
$ . ~/.profile  
```



## Installation (for development) and Building
- Clone the Repo
- Make sure Gradle is installed on your system
- In the command line ...

```shell
$ cd $path_to_argot$
$ gradle run
```

### Build a JAR
Simply run the following:

```shell
$ gradle clean
$ gradle fatJar
```

Then check under the `/build/libs/` directory.

Run the jar using

```shell
$ java -jar $jar_name$
```

### Different CLI Arguments During Testing
Parameters and all other options are handled through Gradle.

In the `build.gradle` file, there should be a snippet that looks like this:

```
// Arguments to pass to the application
args '-help'
```

Simply add whichever argument you need, followed by its parameter.

```
args '-p $path_to_src$
args '-d $path_to_dest$
```

## All Supported Languages
The system is being designed to adjust parsing based on the supplied commenting style. Whether it be for Slash based languages like Java of C, to other commenting systems used in Python or Haskell. Instead of designing a specific language class and filling in the holes provided by the abstract class, new languages would be supplied through a simple constructor.

All tags must begin with a type tag, and end with a end tag.

**Java**
```java
/**
* @type :: example
* @name :: test
* @end
*/
```

**Python**
```py
# @type :: example
# @name :: test
# @end
```

**HTML**
```html
<!-- @type :: example -->
<!-- @name :: test -->
<!-- end -->
```

## The Markup

### Escape Character
For this system, the character that will be used to denote specific information to be extracted is `@`.  To keep this system operating correctly, `@` cannot be used in any documentation.  If `@` is used intermittently in other comment blocks, the validity of the outputs can not be assured.

If your given language supports multi-line comments, then write all Argot documentation within those. If not, write single comments.

**IMPORTANT:** for languages that do not support multi-line comments like Java or C, the last tag in each set must have a `@end`.

Tags should be given in the form `@tagName :: `. Some examples will straighten this all out.

** NOTE:**  Markdown can be included within the documentation itself, and it will be rendered on the final page.

### Types
- `FUNC` - Tag for a function or method
- `VAR` - Tag for a variable
- `CLASS` - Tag for a class

### Beginning the file
When beginning the file, you can use any of the following in any combination at the start of your class, before any declarations.

`@class :: ` - The classname for the file

`@description :: ` - Description of what the file does in relation to the project as a whole

`@date :: ` - Date file was last modified

`@version :: ` - Current version of file

`@author :: ` - the author of the file. Multiple authors should be separated by a comma

`@see :: ` - provides a link to another file in reference

`@child :: ` - indicate the child of the class.  Multiple children should have their own `@child` tag

`@parent :: ` indicate the parent of the class

`@note :: ` - notes any important information relevant to the file

**Java**
```java
/**
* @type :: CLASS
* @class :: Example File
* @author :: Steven Hanna, Other People
* @date :: 7/25/16
* @version :: 0.1.0
*/
```

**Python**
```py
# @type :: CLASS
# @class :: Example File
# @author :: Steven Hanna, Other People
# @date :: 7/25/16
# @version :: 0.1.0
# @end
```

### Methods
Documentation for methods must begin before the method starts.
**Note:** The actual body of the method is not analyzed, just the commented documentation.

`@name :: ` - the name of the method. **Note:** If no name is given, the system will attempt to extract the name from method signature.

`@description :: ` - description of the method in relation to the rest of the class. The description can span multiple lines

`@author :: ` - author of the method

`@date :: ` - date method was last updated

`@param :: ` - one parameter the method takes.  For each parameter, an new `@param` is used.

`@return :: ` - what the method returns. If void, omit this doc.

`@exception :: ` - an exception that might be thrown from this method

`@thrown :: ` - an error that might be thrown from this method

`@see :: ` - provides a link to external documentation

`@note :: ` - notes any important information relevant to the method


```java
/**
* @type :: FUNC
* @name :: sampleMethod
* @description :: Provides a sample method for this example.
* Overflow text can continue here, but cannot go
* underneath `@`
* @param :: String text - text to be returned
* @return :: String text - text that is returned
*/
public String sampleMethod(String text) {
  ...
  return text;
}
```

### Variables / Instance Variables
Like methods, documentation for variables must begin before the variable is declared.  Variables should only be documented upon declaration.

`@name` - the name of the instance variable **Note:** If no name is given, the system will attempt to extract the name from variable signature.

`@description` - description of the variable in relation to rest of the class

`@see` - provides a link to external documentation


```java
/**
* @type :: VAR
* @name :: exampleInt
* @type :: the type of the variable
* @description :: example integer variable
*/
private int exampleInt;
```

### REST API's
Document routes, endpoints, and other REST API segments.

`@route` - the URL this documentation is about

`@crud` - the type of CRUD operation (PUT, DELETE, etc)

`@param` - parameters that this route might take

`@sample` - sample responses that the route might send back

```java
/**
   * @type :: REST
   * @route :: test.com/test
   * @crud :: POST
   * @param :: temp - this is a test param
   * @param :: temp - this is a test param
   * @sample :: `200` - all good
   * @sample :: `404` - not found
   * @sample :: `500` - shit
   */
```

## Eventually
- [ ] Add TODO support
