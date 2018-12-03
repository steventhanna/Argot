mod rendering;
mod parser;
use rendering::rendering::*;

#[macro_use]
extern crate simple_error;

use parser::*;
use rendering::*;


// use rendering::ParameterRep;

fn main() {
    println!("Hello, world!");
    let x = get_comments_from_file("src/parser.rs").unwrap();
    for set in x {
        let new_set = set.into_iter().map(|j| extract_types(j)).collect();
        let joined = join_extracted_comments(new_set);
        println!("{:?}", joined);
        test();
        let mut param_rep = ParameterRep::new(joined).unwrap();
        println!("{:?}", param_rep.render().clone());
    }
    // let extracted = x.into_iter().map(|x| extract_types(x)).collect();
    // let joined = join_extracted_comments(extracted);
    // println!("{:?}", joined);
    // let y = x.into_iter().map(|x| extract_types(x)).collect();
    // let comments = join_extracted_comments(y);
    // println!("{:?}", comments);
    // println!("{:?}", get_comments_from_file("src/parser.rs").unwrap());
}
