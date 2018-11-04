mod rendering;
mod parser;
// use rendering::MarkdownElement;
#[macro_use]
extern crate simple_error;

use parser::*;

fn main() {
    println!("Hello, world!");
    let x = get_comments_from_file("src/parser.rs").unwrap();
    for set in x {
        let new_set = set.into_iter().map(|j| extract_types(j)).collect();
        let joined = join_extracted_comments(new_set);
        println!("{:?}", joined);
    }
    // let extracted = x.into_iter().map(|x| extract_types(x)).collect();
    // let joined = join_extracted_comments(extracted);
    // println!("{:?}", joined);
    // let y = x.into_iter().map(|x| extract_types(x)).collect();
    // let comments = join_extracted_comments(y);
    // println!("{:?}", comments);
    // println!("{:?}", get_comments_from_file("src/parser.rs").unwrap());
}
