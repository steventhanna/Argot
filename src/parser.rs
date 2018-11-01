/**
* @type :: CLASS
* @name :: Parser
* @author :: Steven Hanna - steventhanna@gmail.com
*/
pub mod parser {

    /**
    * @type :: FUNC
    * @name :: extract_types
    * @description :: Extracts the data, and the type from a comment
    * @param :: raw - String - The raw string to parse
    * @return :: Option<(String, String)> - An optional tuple containing the both parts
    */
    fn extract_types(raw: String) -> Option<(String, String)> {
        // Convert the string to &str
        let s = raw.as_str().trim().to_lowercase();
        let mut v: Vec<&str> = s
            .split("::")
            .map(|el| el.trim())
            .collect();
        match v.len() {
            0 => None,
            1 => None,
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
                Some((type_var, text_var))
            }
        }
    }

    /**
    * @type :: FUNC
    * @name :: remove_comment_style
    * @description :: Removes the comment style by. Takes the beginning of the string, takes sub
    * string the length of the comment style, checks to see if there is a match. The comment
    * style vector should be sorted by length
    */
    fn remove_comment_style(raw: String, comment_styles: &Vec<&str>) -> String {
        let mut raw_str = raw.as_str();
        for style in comment_styles {
            // Take a substring
            if raw_str.len() >= style.len() {
                let s = &raw_str[0..style.len()];
                if s == *style {
                    // If the strings match, make a new slice
                    raw_str = &raw_str[style.len()..];
                }
            }
        }

        raw_str = raw_str.trim();
        raw_str.to_string()
    }

    #[cfg(test)]
    mod test {
        use parser::parser::*;

        #[test]
        fn test_extract_types() {
            assert_eq!(extract_types(String::from("@param :: test")), Some((String::from("@param"), String::from("test"))));
            assert_eq!(extract_types(String::from("   @param    ::    test   ")), Some((String::from("@param"), String::from("test"))));
            assert_eq!(extract_types(String::from("   @param    ::    test :: x")), Some((String::from("@param"), String::from("test :: x"))));
            assert_eq!(extract_types(String::from("")), None);
        }

        #[test]
        fn test_remove_comment_style() {
            let comment_styles = vec!["/**", "*/", "*"];
            assert_eq!(remove_comment_style(String::from("/**"), &comment_styles), String::from(""));
            assert_eq!(remove_comment_style(String::from("*/"), &comment_styles), String::from(""));
            assert_eq!(remove_comment_style(String::from("* @param :: test"), &comment_styles), String::from("@param :: test"));
            assert_eq!(remove_comment_style(String::from("* @param :: *test /** */"), &comment_styles), String::from("@param :: *test /** */"));

            let comment_styles = vec!["'''"];
            assert_eq!(remove_comment_style(String::from("'''"), &comment_styles), String::from(""));
            assert_eq!(remove_comment_style(String::from("Comment test"), &comment_styles), String::from("Comment test"));
        }
    }
}
