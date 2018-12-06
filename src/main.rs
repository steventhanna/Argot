mod rendering;
mod parser;
use rendering::rendering::*;
use std::path::{Path, PathBuf};
use std::ffi::OsStr;

#[macro_use]
extern crate simple_error;

use parser::*;
use rendering::*;


fn main() {
    handle_file("src/parser.rs", "src");
    handle_file("src/rendering.rs", "src");
    handle_file("src/Extraction.rs", "src");
}

fn handle_file(filename: &str, destination: &str) {
    let x = match get_comments_from_file(filename) {
        Ok(x) => x,
        _ => Vec::new()
    };

    let mut contents: Vec<String> = Vec::new();
    for set in x {
        let new_set = set.into_iter().map(|j| extract_types(j)).collect();
        let joined = join_extracted_comments(new_set);
        let mut param_rep = ParameterRep::new(joined).unwrap();
        contents.push(param_rep.render().clone());
    }

    let stem = match Path::new(filename).file_stem() {
        None => String::from("unnamed"),
        Some(x) => String::from(x.to_str().unwrap())
    };

    let destination_path = Path::new(destination);
    let final_file_path = destination_path.join(stem.as_str()).with_extension("md");

    write_string_to_file(final_file_path.as_path().to_str().unwrap(), contents.join("\n"));
}
