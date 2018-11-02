mod rendering;
mod parser;
// use rendering::MarkdownElement;

use parser::*;

fn main() {
    println!("Hello, world!");
    let x = get_comments_from_file("src/parser.rs").unwrap();
    for y in x {
        for z in y {
            println!("{:?}", extract_types(z));
        }
        // println!("{:?}", y);
    }
    // println!("{:?}", get_comments_from_file("src/parser.rs").unwrap());
}
