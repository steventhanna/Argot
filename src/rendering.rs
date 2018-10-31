pub mod rendering {

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
        pub fn new(text: String, markdown_type: String) -> MarkdownElement {
            MarkdownElement {
                text: text,
                markdown_type: markdown_type
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
                "ul" => String::from("- ") + &self.text.clone(),
                "ol" => String::from("1. ") + &self.text.clone(),
                "code" => String::from("`") + &self.text.clone() + &String::from("`"),
                "codeblock" => String::from("```\n") + &self.text.clone() + &String::from("\n```"),
                "todo" => String::from("- [] ") + &self.text.clone(),
                "todochecked" => String::from("- [x] ") + &self.text.clone(),
                "bold" => String::from("**") + &self.text.clone() + &String::from("**"),
                "italic" => String::from("*") + &self.text.clone() + &String::from("*"),
                _ => String::new()
            }
        }
    }

    #[cfg(test)]
    mod test {
        use super::MarkdownElement;

        #[test]
        fn new() {
            let elem = MarkdownElement::new(String::from("test"), String::from("h1"));
            assert_eq!(elem.text, String::from("test"));
            assert_eq!(elem.markdown_type, String::from("h1"));
        }

        #[test]
        fn render() {
            let elem = MarkdownElement::new(String::from("test"), String::from("h1"));
            assert_eq!(elem.render(), String::from("# test"));
            let elem = MarkdownElement::new(String::from("test"), String::from("h2"));
            assert_eq!(elem.render(), String::from("## test"));
            let elem = MarkdownElement::new(String::from("test"), String::from("h3"));
            assert_eq!(elem.render(), String::from("### test"));
            let elem = MarkdownElement::new(String::from("test"), String::from("h4"));
            assert_eq!(elem.render(), String::from("#### test"));
            let elem = MarkdownElement::new(String::from("test"), String::from("ul"));
            assert_eq!(elem.render(), String::from("- test"));
            let elem = MarkdownElement::new(String::from("test"), String::from("ol"));
            assert_eq!(elem.render(), String::from("1. test"));
            let elem = MarkdownElement::new(String::from("test"), String::from("code"));
            assert_eq!(elem.render(), String::from("`test`"));
            let elem = MarkdownElement::new(String::from("test"), String::from("codeblock"));
            assert_eq!(elem.render(), String::from("```\ntest\n```"));
            let elem = MarkdownElement::new(String::from("test"), String::from("todo"));
            assert_eq!(elem.render(), String::from("- [] test"));
            let elem = MarkdownElement::new(String::from("test"), String::from("todochecked"));
            assert_eq!(elem.render(), String::from("- [x] test"));
            let elem = MarkdownElement::new(String::from("test"), String::from("bold"));
            assert_eq!(elem.render(), String::from("**test**"));
            let elem = MarkdownElement::new(String::from("test"), String::from("italic"));
            assert_eq!(elem.render(), String::from("*test*"));
        }
    }
}
