# Argot
Parse documentation from codebases into markdown for easy doc creation.  With the exception of markup languages and stylesheets, languages with / notation will follow a modified version of the Java documentation system.

## Argot in Action
Check out [Argot's Wiki](https://github.com/steventhanna/Argot/wiki) where Argot is run against its own codebase.

## Installation and Building
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

## / Comment Languages

### Escape Character
For this system, the character that will be used to denote specific information to be extracted is `@`.  To keep this system operating correctly, `@` cannot be used in any documentation.  If `@` is used intermittently in other comment blocks, the validity of the outputs can not be assured.

Comment blocks for this system will begin with `/**`.  Each preceding line will being with `*`, and the finishing line will be end with `*/`.  With each piece of documentation, a tag should be given in the form `@tag :: ` so that the system can distinguish between methods, vars, etc.

** NOTE: ** Markdown can be parsed from within the documentation, simply include ` ` ` tags within the description.

### Tags
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

```java
/**
* @tag :: CLASS
* @class :: Example File
* @author :: Steven Hanna, Other People
* @date :: 7/25/16
* @version :: 0.1.0
*/
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
* @tag :: FUNC
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
* @tag :: VAR
* @name :: exampleInt
* @type :: the type of the variable
* @description :: example integer variable
*/
private int exampleInt;
```

## Eventually
- [ ] Add TODO support
