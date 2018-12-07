use std::ffi::OsStr;
/**
* @type :: CLASS
* @name :: Parser
* @author :: Steven Hanna - steventhanna@gmail.com
*/
use std::fs::File;
use std::io::prelude::*;
use std::io::BufReader;
use std::io::Error;
use std::path::Path;

/**
* @type :: FUNC
* @name :: extract_types
* @description :: Extracts the data, and the type from a comment
* @param :: raw - String - The raw string to parse
* @return :: Option<(String, String)> - An optional tuple containing the both parts
*/
pub fn extract_types(raw: String) -> (String, String) {
    // Convert the string to &str
    let s = raw.as_str().trim();
    let mut v: Vec<&str> = s.split("::").map(|el| el.trim()).collect();
    match v.len() {
        0 => (String::new(), s.to_string()),
        1 => (String::new(), s.to_string()),
        _ => {
            // This part handles in case where there are arbritrary `::` inside the docs
            // Reformat after to delimit by ` :: `
            let type_var = v[0].to_string();
            v.remove(0);
            let mut text_var = String::new();
            while v.len() > 0 {
                text_var.push_str(v[0]);
                v.remove(0);
                if v.len() > 0 {
                    text_var.push_str(" :: ");
                }
            }
            (type_var, text_var)
        }
    }
}

/**
* @type :: FUNC
* @name :: join_extracted_comments
* @description :: Joins multiline comments together
* @param :: mut types: Vec<(String, String)> - a list of extracted string tuples
* @return :: Vec<(String, String)> - a list of the joined string tuples
*/
pub fn join_extracted_comments(mut types: Vec<(String, String)>) -> Vec<(String, String)> {
    if types.len() < 2 {
        return types;
    }
    let mut x = 1;
    while x < types.len() - 1 {
        let (second_type, second_text) = types[x].clone();
        let (first_type, mut first_text) = types[x - 1].clone();
        // If the second string is blank, than it is a multiline comment and should be joined
        if second_type.as_str() == "" {
            first_text.push_str(" ");
            first_text.push_str(second_text.as_str());
            // Set the result text back fo the first text
            types[x - 1] = (first_type, first_text);
            // Delete the x object
            types.remove(x);
            x -= 1;
        }
        x += 1;
    }
    types
}

/**
* @type :: FUNC
* @name :: remove_comment_style
* @description :: Removes the comment style by. Takes the beginning of the string, takes sub
* string the length of the comment style, checks to see if there is a match. The comment
* style vector should be sorted by length
* @return :: (String, bool) - String representing the cleaned array, bool if it was altered
*/
fn remove_comment_style(raw: String, comment_styles: &Vec<&str>) -> (String, bool) {
    let mut raw_str = raw.as_str().trim();
    let mut has_altered = false;
    for style in comment_styles {
        // Take a substring
        if raw_str.len() >= style.len() {
            let s = &raw_str[0..style.len()];
            if s == *style {
                // If the strings match, make a new slice
                raw_str = &raw_str[style.len()..];
                has_altered = true;
            }
        }
    }

    raw_str = raw_str.trim();
    (raw_str.to_string(), has_altered)
}

/**
* @type :: FUNC
* @name :: get_extension_from_filename
* @param :: filename: &str - the filename to get the extension from
* @return :: Option<&str> - an optional type that could contain a valid string
* @description :: Extracts an extension from a filename
*/
fn get_extension_from_filename(filename: &str) -> Option<&str> {
    Path::new(filename).extension().and_then(OsStr::to_str)
}

/**
* @type :: FUNC
* @name :: get_comment_styles
* @param :: extension - &str - The file extension to return the comment style for
* @return :: Vec<&str> - A vector with string slices representing the comment styles
* @description :: Returns a list of comment styles for each supported language
*/
fn get_comment_styles(extension: &str) -> Vec<&str> {
    match extension {
        "rs" => vec!["/**", "*/", "*"],
        "java" => vec!["/**", "*/", "*"],
        "js" => vec!["/**", "*/", "*"],
        "jsx" => vec!["/**", "*/", "*"],
        "py" => vec!["'''"],
        _ => Vec::new(),
    }
}

/**
* @type :: FUNC
* @name :: get_comments_from_file
* @description :: Extracts comments from a file, pushing sets of comments into vectors
*/
pub fn get_comments_from_file(filename: &str) -> Result<Vec<Vec<String>>, Error> {
    let f = match File::open(filename) {
        Ok(ff) => ff,
        Err(e) => return Err(e),
    };
    let f = BufReader::new(f);

    let extension = match get_extension_from_filename(filename) {
        Some(x) => x,
        None => "",
    };

    let comment_styles = &get_comment_styles(extension);

    let mut contents = Vec::new();
    let mut comment_break = false;

    for line in f.lines() {
        match line {
            Ok(l) => {
                let (altered_string, has_altered) =
                    remove_comment_style(l.to_string(), comment_styles);
                if altered_string.len() > 0 && has_altered {
                    comment_break = false;
                    let mut current = match contents.pop() {
                        Some(x) => x,
                        None => Vec::new(),
                    };
                    current.push(altered_string);
                    contents.push(current);
                } else {
                    if !comment_break {
                        let mut inner_content = Vec::new();
                        contents.push(inner_content);
                    }
                    comment_break = true;
                }
            }
            Err(e) => return Err(e),
        };
    }
    // Filter out blank vectors
    contents.retain(|x: &Vec<String>| x.len() > 0);
    Ok(contents)
}

#[cfg(test)]
mod test {

    use super::*;

    #[test]
    fn test_extract_types() {
        assert_eq!(
            extract_types(String::from("@param :: test")),
            ((String::from("@param"), String::from("test")))
        );
        assert_eq!(
            extract_types(String::from("   @param    ::    test   ")),
            ((String::from("@param"), String::from("test")))
        );
        assert_eq!(
            extract_types(String::from("   @param    ::    test :: x")),
            ((String::from("@param"), String::from("test :: x")))
        );
        assert_eq!(
            extract_types(String::from("")),
            ((String::new(), String::new()))
        );
    }

    #[test]
    fn test_remove_comment_style() {
        let comment_styles = vec!["/**", "*/", "*"];
        assert_eq!(
            remove_comment_style(String::from("/**"), &comment_styles),
            (String::from(""), true)
        );
        assert_eq!(
            remove_comment_style(String::from("*/"), &comment_styles),
            (String::from(""), true)
        );
        assert_eq!(
            remove_comment_style(String::from("* @param :: test"), &comment_styles),
            (String::from("@param :: test"), true)
        );
        assert_eq!(
            remove_comment_style(String::from("* @param :: *test /** */"), &comment_styles),
            (String::from("@param :: *test /** */"), true)
        );

        let comment_styles = vec!["'''"];
        assert_eq!(
            remove_comment_style(String::from("'''"), &comment_styles),
            (String::from(""), true)
        );
        assert_eq!(
            remove_comment_style(String::from("Comment test"), &comment_styles),
            (String::from("Comment test"), false)
        );
    }

    #[test]
    fn test_get_extension_from_filename() {
        assert_eq!(get_extension_from_filename("parser.rs").unwrap(), "rs");
    }

    #[test]
    fn test_get_comment_styles() {
        assert_eq!(get_comment_styles("rs"), vec!["/**", "*/", "*"]);
        assert_eq!(get_comment_styles("java"), vec!["/**", "*/", "*"]);
        assert_eq!(get_comment_styles("py"), vec!["'''"]);
        assert_eq!(get_comment_styles("nonexistant"), Vec::<&str>::new());
    }
}
