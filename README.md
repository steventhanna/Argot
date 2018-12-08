# Argot
![Status](https://travis-ci.org/steventhanna/Argot.svg?branch=master)

Parse documentation from codebases into markdown for easy doc creation. Argot is inspired by the traditional JavaDoc system.

## Motivation
Maintaining large systems in differenet languages brings its own set of unique challenges, especially when maintaining documentation for those systems.  Different documentation systems degrade the overall quality of the codebase, while hindering the productivity of developers. Argot attempts to rectify this by providing a single documentation style that can support any language, and dumps the resultant documentation to Markdown.

## The Name?
The defintion of argot is "the jaron or slang of a particular group or class".  Seems like a pretty fitting name for a documentation system.

## Warning
This is still a work in progress, so things might break. Submitting a bug report is the best way to help me identify issues.

## Argot in Action
Check out [Argot's Wiki](https://github.com/steventhanna/Argot/wiki) where Argot is run against this repo.

## Installation (for use)
Coming soon to your favorite package manager.

## Installation (for development)
- Clone the repo
- Ensure Rust / Cargo is installed
- In the command line ...

```shell
cargo build
```
cargo run -- --help

### Build for relase

```shell
cargo build --release
```

## CLI Arguments

```
Argot 0.2.0
Steven Hanna <steventhanna@gmail.com>
Parse documentation from codebases into Markdown for easy doc creation.

USAGE:
    argot [FLAGS] --destination <DESTINATION> --origin <ORIGIN>

FLAGS:
    -h, --help         Prints help information
    -r, --recursive    Recursively walk the file tree parsing
    -V, --version      Prints version information

OPTIONS:
    -d, --destination <DESTINATION>    Sets a custom destination path for rendered markdown files
    -o, --origin <ORIGIN>              Sets the origin of where Argot should start parsing from
```

### Example

```
argot -o /src -d /docs -r
```


## The Markdown

### All Supported Languages
The system is being designed to adjust parsing based on the supplied commenting style. Whether it be for Slash based languages like Java of C, to other commenting systems used in Python or Haskell. Instead of designing a specific language class and filling in the holes provided by the abstract class, new languages would be supplied through a simple constructor.

All tags must include a `type` tag.

**Java, Rust, C++, JS**
```java
/**
* @type :: FUNC
* @name :: test
*/
```

**Python**
```py
'''
@type :: example
@name :: test
@end
'''
```

**NOTE:**  Markdown can be included within the documentation itself, and it will be rendered on the final page.

### Types
- `FUNC` - Tag for a function or method
- `VAR` - Tag for a variable
- `CLASS` - Tag for a class

### Beginning the file
When beginning the file, you can use any of the following in any combination at the start of your class, before any declarations.

`@name :: ` - The classname for the file

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
* @name :: Example File
* @author :: Steven Hanna, Other People
* @date :: 7/25/16
* @version :: 0.1.0
*/
```

**Python**
```py
'''
@type :: CLASS
@class :: Example File
@author :: Steven Hanna, Other People
@date :: 7/25/16
@version :: 0.1.0
'''
```

### Methods
**Note:** The actual body of the method is not analyzed, just the commented documentation.

`@name :: ` - the name of the method.

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
* Overflow text can continue here
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

`@name` - the name of the instance variable 

`@description` - description of the variable in relation to rest of the class

`@see` - provides a link to external documentation


```java
/**
* @type :: VAR
* @name :: exampleInt
* @vartype :: the type of the variable
* @description :: example integer variable
*/
private int exampleInt;
```
