pub mod rendering {

    use std::path::Path;
    use std::fs::File;
    use std::error::Error;
    use std::io::Write;

    use simple_error::SimpleError;


    /**
    * @type :: FUNC
    * @name :: write_markdown_to_file
    * @description :: Writes a series of markdown elements to a file
    * @param :: filename: &str - a string slice representing the source filename with the source
    * extension
    * @param :: elements: Vec<MarkdownElement> - a vector full of markdown elements to be rendered
    */
    pub fn write_markdown_to_file(filename: &str, elements: Vec<MarkdownElement>) {
        // Chop the extension off the file
        let path_stem = match Path::new(filename).file_stem() {
            Some(x) => format!("{}{}", x.to_str().unwrap(), ".md"),
            None => String::from("unnamed.md")
        };

        let path = Path::new(path_stem.as_str());

        let mut file = match File::create(&path) {
            Err(why) => panic!("Couldn't create {}: {}", path.display(), why.description()),
            Ok(file) => file
        };

        // Render the strings
        let elems: Vec<String> = elements.iter().map(|ref elem| elem.render()).collect();
        let joined_strings = elems.join("\n");
        let rendered_strings = joined_strings.as_str();

        match file.write_all(rendered_strings.as_bytes()) {
            Err(why) => panic!("Couldn't write to {}: {}", path.display(), why.description()),
            Ok(_) => return
        };
    }

    pub fn test() {
        println!("test");
    }

    /**
    * @type :: CLASS
    * @name :: ParameterRep
    * @description :: Represents a chunk of comments to be rendered.
    */
    pub struct ParameterRep {
        /**
        * @type :: VAR
        * @name :: argot_type
        * @vartype :: String
        * @description :: Holds the type of each parameter, currently either FUNC, VAR, CLASS
        */
        argot_type: String,

        /**
        * @type :: VAR
        * @name :: raw_elements
        * @vartype :: Vec<(String, String)>
        * @description :: Holds the raw source comments in a tuple, with the first element the
        * part on the left of the ::, and second element on the right
        */
        raw_elements: Vec<(String, String)>,

        /**
        * @type :: VAR
        * @name :: elements
        * @vartype :: Vec<MarkdownElement>
        * @description :: Holds a list of MarkdownElement's.  Should be considered to be in order,
        * and can be rendered simply by looping through.  This instance variable will be built
        * from data passed through the constructor
        */
        elements: Vec<MarkdownElement>
    }

    impl ParameterRep {
        pub fn new(raw_elements: Vec<(String, String)>) -> Result<ParameterRep, SimpleError> {
            // Find a type
            let maybe_index = raw_elements.iter().position(|tuple| {
                let (type_elem, _) = tuple;
                type_elem == "@type"
            });
            let (_, type_elem) = match maybe_index {
                Some(x) => raw_elements[x].clone(),
                None => return Err(SimpleError::new("invalid format"))
            };

            let elems: Vec<MarkdownElement> = Vec::new();

            Ok(ParameterRep {
                argot_type: type_elem,
                elements: elems,
                raw_elements: raw_elements.clone()
            })
        }

        pub fn render(&mut self) -> String {
            match self.argot_type.as_str() {
                "CLASS" => self.create_class(),
                "VAR" => self.create_var(),
                "FUNC" => self.create_func(),
                _ => return String::new()
            };

            self.render_to_string()
        }

        fn extract_type(&self, type_name: &str) -> Vec<String> {
            self.raw_elements
                .iter()
                .filter(|x| x.0.as_str() == type_name)
                .map(|x| x.1.clone())
                .collect::<Vec<String>>()
        }

        fn create_class(&mut self) {
            // Look for a @name
            let mut names = self.extract_type("@name")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "h1"))
                .collect::<Vec<MarkdownElement>>();
            self.elements.append(&mut names);

            let mut authors = self.extract_type("@author")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "ul"))
                .collect::<Vec<MarkdownElement>>();
            if authors.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Authors"), "h3"));
                self.elements.append(&mut authors);
            }

            let mut dates = self.extract_type("@date")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "p"))
                .collect::<Vec<MarkdownElement>>();
            if dates.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Date"), "h3"));
                self.elements.append(&mut dates);
            }


            let mut versions = self.extract_type("@version")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "italic"))
                .collect::<Vec<MarkdownElement>>();
            if versions.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Current Version"), "h3"));
                self.elements.append(&mut versions);
            }

            let mut sees = self.extract_type("@see")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "bold"))
                .collect::<Vec<MarkdownElement>>();
            if sees.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("See - Relevant Links / Documents"), "h3"));
                self.elements.append(&mut sees);
            }

            let mut children = self.extract_type("@child")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "ul"))
                .collect::<Vec<MarkdownElement>>();

            if children.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Children Classes"), "h3"));
                self.elements.append(&mut children);
            }

            let mut parents = self.extract_type("@parent")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "ul"))
                .collect::<Vec<MarkdownElement>>();
            if parents.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Parent Classes"), "h3"));
                self.elements.append(&mut parents);
            }


            let mut descriptions = self.extract_type("@description")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "p"))
                .collect::<Vec<MarkdownElement>>();
            if descriptions.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Description"), "h3"));
                self.elements.append(&mut descriptions);
            }


            let mut notes = self.extract_type("@note")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "ul"))
                .collect::<Vec<MarkdownElement>>();
            if notes.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Notes"), "h3"));
                self.elements.append(&mut notes);
            }
        }

        fn create_var(&mut self) {
            // Look for a @name
            let mut names = self.extract_type("@name")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "h2"))
                .collect::<Vec<MarkdownElement>>();
            self.elements.append(&mut names);

            let mut authors = self.extract_type("@author")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "ul"))
                .collect::<Vec<MarkdownElement>>();
            if authors.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Authors"), "h3"));
                self.elements.append(&mut authors);
            }

            let mut vartype = self.extract_type("@vartype")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "p"))
                .collect::<Vec<MarkdownElement>>();
            if vartype.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Type"), "h3"));
                self.elements.append(&mut vartype);
            }


            let mut dates = self.extract_type("@date")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "p"))
                .collect::<Vec<MarkdownElement>>();
            if dates.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Date"), "h3"));
                self.elements.append(&mut dates);
            }

            let mut versions = self.extract_type("@version")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "italic"))
                .collect::<Vec<MarkdownElement>>();
            if versions.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Current Version"), "h3"));
                self.elements.append(&mut versions);
            }

            let mut sees = self.extract_type("@see")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "bold"))
                .collect::<Vec<MarkdownElement>>();
            if sees.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("See - Relevant Links / Documents"), "h3"));
                self.elements.append(&mut sees);
            }

            let mut descriptions = self.extract_type("@description")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "p"))
                .collect::<Vec<MarkdownElement>>();
            if descriptions.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Description"), "h3"));
                self.elements.append(&mut descriptions);
            }

            let mut notes = self.extract_type("@note")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "ul"))
                .collect::<Vec<MarkdownElement>>();
            if notes.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Notes"), "h3"));
                self.elements.append(&mut notes);
            }
        }

        fn create_func(&mut self) {
            // Look for a @name
            let mut names = self.extract_type("@name")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "h2"))
                .collect::<Vec<MarkdownElement>>();
            self.elements.append(&mut names);

            let mut authors = self.extract_type("@author")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "ul"))
                .collect::<Vec<MarkdownElement>>();
            if authors.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Authors"), "h3"));
                self.elements.append(&mut authors);
            }

            let mut params = self.extract_type("@param")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "ul"))
                .collect::<Vec<MarkdownElement>>();
            if params.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Parameters"), "h3"));
                self.elements.append(&mut params);
            }

            let mut returns = self.extract_type("@return")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "ul"))
                .collect::<Vec<MarkdownElement>>();
            if returns.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Returns"), "h3"));
                self.elements.append(&mut returns);
            }

            let mut dates = self.extract_type("@date")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "p"))
                .collect::<Vec<MarkdownElement>>();
            if dates.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Date"), "h3"));
                self.elements.append(&mut dates);
            }

            let mut versions = self.extract_type("@version")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "italic"))
                .collect::<Vec<MarkdownElement>>();
            if versions.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Current Version"), "h3"));
                self.elements.append(&mut versions);
            }

            let mut sees = self.extract_type("@see")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "bold"))
                .collect::<Vec<MarkdownElement>>();
            if sees.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("See - Relevant Links / Documents"), "h3"));
                self.elements.append(&mut sees);
            }

            let mut descriptions = self.extract_type("@description")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "p"))
                .collect::<Vec<MarkdownElement>>();
            if descriptions.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Description"), "h3"));
                self.elements.append(&mut descriptions);
            }

            let mut notes = self.extract_type("@note")
                .iter()
                .map(|elem| MarkdownElement::new(elem.clone(), "ul"))
                .collect::<Vec<MarkdownElement>>();
            if notes.len() > 0 {
                self.elements.push(MarkdownElement::new(String::from("Notes"), "h3"));
                self.elements.append(&mut notes);
            }
        }

        /**
        * @type :: FUNC
        * @name :: render_to_string
        * @description :: Renders the markdown elements to a single String joined by newlines
        */
        fn render_to_string(&self) -> String{
            self.elements
                .iter()
                .map(|elem| elem.render())
                .collect::<Vec<String>>()
                .join("\n")
        }
    }


    /**
    * @type :: CLASS
    * @name :: MarkdownElement
    * @author :: Steven Hanna - steventhanna@gmail.com
    * @description :: Representation of an arbritrary element to be represented in Markdown
    */
    pub struct MarkdownElement {
        /**
        * @type :: VAR
        * @name :: markdown_type
        * @description :: String type that holds what kind of element the markdown should be
        * rendered as
        */
        markdown_type: String,

        /**
        * @type :: VAR
        * @name :: text
        * @description :: The text to be rendered out to the type
        */
        text: String
    }

    impl MarkdownElement {
        /**
        * @type :: FUNC
        * @name :: new
        * @description :: Creates a new markdown element that can be rendered
        * @param :: text - String - the text to be transformed to markdown
        * @param :: markdown_type - String - the type of element to render as
        */
        pub fn new(text: String, markdown_type: &str) -> MarkdownElement {
            MarkdownElement {
                text: text,
                markdown_type: String::from(markdown_type)
            }
        }

        /**
        * @type :: FUNC
        * @name :: render
        * @return :: String - the markdown representation of the object
        * @description :: Renders a MarkdownElement object to Markdown
        */
        pub fn render(&self) -> String {
            match self.markdown_type.as_ref() {
                "h1" => String::from("# ") + &self.text.clone(),
                "h2" => String::from("## ") + &self.text.clone(),
                "h3" => String::from("### ") + &self.text.clone(),
                "h4" => String::from("#### ") + &self.text.clone(),
                "ul" => String::from("- ") + &self.text.clone() + &String::from("\n"),
                "ol" => String::from("1. ") + &self.text.clone() + &String::from("\n"),
                "p"  => String::from(self.text.as_str()),
                "code" => String::from("`") + &self.text.clone() + &String::from("`"),
                "codeblock" => String::from("```\n") + &self.text.clone() + &String::from("\n```"),
                "todo" => String::from("- [] ") + &self.text.clone() + &String::from("\n"),
                "todochecked" => String::from("- [x] ") + &self.text.clone() + &String::from("\n"),
                "bold" => String::from("**") + &self.text.clone() + &String::from("**"),
                "italic" => String::from("*") + &self.text.clone() + &String::from("*"),
                "newline" => String::from("\n"),
                _ => String::new()
            }
        }
    }

    #[cfg(test)]
    mod test {
        use super::MarkdownElement;

        #[test]
        fn new() {
            let elem = MarkdownElement::new(String::from("test"), "h1");
            assert_eq!(elem.text, String::from("test"));
            assert_eq!(elem.markdown_type, "h1");
        }

        #[test]
        fn render() {
            let elem = MarkdownElement::new(String::from("test"), "h1");
            assert_eq!(elem.render(), String::from("# test"));
            let elem = MarkdownElement::new(String::from("test"), "h2");
            assert_eq!(elem.render(), String::from("## test"));
            let elem = MarkdownElement::new(String::from("test"), "h3");
            assert_eq!(elem.render(), String::from("### test"));
            let elem = MarkdownElement::new(String::from("test"), "h4");
            assert_eq!(elem.render(), String::from("#### test"));
            let elem = MarkdownElement::new(String::from("test"), "ul");
            assert_eq!(elem.render(), String::from("- test"));
            let elem = MarkdownElement::new(String::from("test"), "ol");
            assert_eq!(elem.render(), String::from("1. test"));
            let elem = MarkdownElement::new(String::from("test"), "code");
            assert_eq!(elem.render(), String::from("`test`"));
            let elem = MarkdownElement::new(String::from("test"), "codeblock");
            assert_eq!(elem.render(), String::from("```\ntest\n```"));
            let elem = MarkdownElement::new(String::from("test"), "todo");
            assert_eq!(elem.render(), String::from("- [] test"));
            let elem = MarkdownElement::new(String::from("test"), "todochecked");
            assert_eq!(elem.render(), String::from("- [x] test"));
            let elem = MarkdownElement::new(String::from("test"), "bold");
            assert_eq!(elem.render(), String::from("**test**"));
            let elem = MarkdownElement::new(String::from("test"), "italic");
            assert_eq!(elem.render(), String::from("*test*"));
        }
    }
}
