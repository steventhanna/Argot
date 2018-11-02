mod rendering;
mod parser;
// use rendering::MarkdownElement;

use parser::*;

fn main() {
    println!("Hello, world!");
    println!("{:?}", get_comments_from_file("src/parser.rs").unwrap());
}
