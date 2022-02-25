use std::{fs, vec, collections::HashMap};

use itertools::Itertools;

pub struct Node<'a> {
    name: String,
    edges: Vec<&'a Node<'a>>,
    big: bool,
    visited: bool,
}

impl From<&str> for Node<'_>{
    fn from(name: &str) -> Self {
        let c = name.as_bytes()[0];
        Node{name: String::from(name), edges: vec![], big: b'A' <= c && c <= b'Z', visited: false}
    }
}

impl<'a> Node<'a> {
    fn link_node(&mut self, node: &'a Node) {
        self.edges.push(node);
    }
}

pub fn read_file(file_name: &str) -> Vec<String> {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let input: Vec<String> = data.split("\r\n")
                            .map(|x| String::from(x))
                            .collect();
    return input;
}

pub fn graph(lines: Vec<String>){
    let mut graph:HashMap<String, Node> = HashMap::new();
    for line in lines {
        let names: Vec<&str> = line.split("-")
                                    .collect();
        let name1 = names[0].to_owned();
        if !graph.contains_key(&name1) {
            let n = Node::from(name1.as_str());
            graph.insert(name1, n);
        }
        let name2 = names[1].to_owned();
        if !graph.contains_key(&name2) {
            let n = Node::from(name2.as_str());
            graph.insert(name2, n);
        }
        let n1 = graph.get_mut(names[0]);
        let n2 = graph.get_mut(names[1]);
        //n1.unwrap().link_node(n2.unwrap());
        //n1.unwrap().edges.push(n2.unwrap());
    }
}