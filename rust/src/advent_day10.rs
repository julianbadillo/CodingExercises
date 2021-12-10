use std::{fs, vec};
use std::collections::LinkedList;

pub fn read_file(file_name: &str) -> Vec<String> {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let input: Vec<String> = data.split("\r\n")
                            .map(|x| x.to_string())
                            .collect();
    return input;
}

pub fn check_syntax_illegal(line: &str) -> Option<u32> {
    let mut stack = LinkedList::new();

    for c in line.chars() {
        if c == '(' || c == '[' || c == '{'|| c == '<' {
            stack.push_back(c);
        } else {
            let c2 = stack.pop_back();
            // check illegal chars
            if c2 == None {
                panic!("Error!");
            } else if c == ')' && c2.unwrap() != '(' {
                return Some(3);
            } else if c == ']' && c2.unwrap() != '[' {
                return Some(57);
            } else if c == '}' && c2.unwrap() != '{' {
                return Some(1197);
            } else if c == '>' && c2.unwrap() != '<' {
                return Some(25137);
            }
        }
    }
    return None;
}

pub fn check_syntax_complete(line: &str) -> Option<u64> {
    let mut stack = LinkedList::new();

    for c in line.chars() {
        if c == '(' || c == '[' || c == '{'|| c == '<' {
            stack.push_back(c);
        } else {
            let c2 = stack.pop_back();
            // check illegal chars
            if c2 == None {
                panic!("Error!");
            } else if c == ')' && c2.unwrap() != '(' {
                return None;
            } else if c == ']' && c2.unwrap() != '[' {
                return None;
            } else if c == '}' && c2.unwrap() != '{' {
                return None;
            } else if c == '>' && c2.unwrap() != '<' {
                return None;
            }
        }
    }

    let mut s: u64 = 0;
    // add up missing chars
    while let Some(x) =  stack.pop_back() {
        s = 5 * s + match x {
                '(' => 1,
                '[' => 2,
                '{' => 3,
                '<' => 4,
                _ => panic!("Error!!!")
            } 
    }
    return Some(s);
}

pub fn add_illegal_lines(lines: Vec<String>) -> u32 {
    lines.iter().map(|line| check_syntax_illegal(line.as_str()))
                .filter(|r| r.is_some())
                .map(|r| r.unwrap())
                .sum()
}

pub fn median_incomplete_lines(lines: Vec<String>) -> u64 {
    let mut scores: Vec<u64> = lines.iter().map(|line| check_syntax_complete(line.as_str()))
                                .filter(|r| r.is_some())
                                .map(|r| r.unwrap())
                                .collect();
    scores.sort();
    // return median
    scores[scores.len() / 2]
}

#[cfg(test)]
mod tests {
    
    use super::*;
    #[test]    
    fn test_read_file(){
        let actual = read_file("advent_day10_test.txt");
        let expected: Vec<String> = vec![String::from("[({(<(())[]>[[{[]{<()<>>"),
                                        String::from("[(()[<>])]({[<{<<[]>>("),
                                        String::from("{([(<{}[<>[]}>{[]{[(<()>"),
                                        String::from("(((({<>}<{<{<>}{[]{[]{}"),
                                        String::from("[[<[([]))<([[{}[[()]]]"),
                                        String::from("[{[{({}]{}}([{[{{{}}([]"),
                                        String::from("{<[[]]>}<{[{[{[]{()[[[]"),
                                        String::from("[<(<(<(<{}))><([]([]()"),
                                        String::from("<{([([[(<>()){}]>(<<{{"),
                                        String::from("<{([{{}}[<[[[<>{}]]]>[]]")];
        assert_eq!(actual, expected);
    }

    #[test]
    fn test_check_syntax_illegal(){
        assert_eq!(check_syntax_illegal("<{([([[(<>()){}]>(<<{{"), Some(25137));
        assert_eq!(check_syntax_illegal("[{[{({}]{}}([{[{{{}}([]"), Some(57));
        assert_eq!(check_syntax_illegal("{([(<{}[<>[]}>{[]{[(<()>"), Some(1197));
        assert_eq!(check_syntax_illegal("[[<[([]))<([[{}[[()]]]"), Some(3));
    }

    #[test]
    fn test_add_illegal_test(){
        let lines = read_file("advent_day10_test.txt");
        assert_eq!(add_illegal_lines(lines), 26397);
    }

    #[test]
    fn test_add_illegal(){
        let lines = read_file("advent_day10.txt");
        println!("Sum of illegal {}", add_illegal_lines(lines));
    }

    #[test]
    fn test_check_syntax_complete(){
        assert_eq!(check_syntax_complete("[({(<(())[]>[[{[]{<()<>>"), Some(288957));
        assert_eq!(check_syntax_complete("[(()[<>])]({[<{<<[]>>("), Some(5566));
        assert_eq!(check_syntax_complete("(((({<>}<{<{<>}{[]{[]{}"), Some(1480781));
        assert_eq!(check_syntax_complete("{<[[]]>}<{[{[{[]{()[[[]"), Some(995444));
        assert_eq!(check_syntax_complete("<{([{{}}[<[[[<>{}]]]>[]]"), Some(294));
    }

    #[test]
    fn test_median_incomplete_test(){
        let lines = read_file("advent_day10_test.txt");
        assert_eq!(median_incomplete_lines(lines), 288957);
    }

    #[test]
    fn test_median_incomplete(){
        let lines = read_file("advent_day10.txt");
        println!("Median of incomplete {}", median_incomplete_lines(lines));
    }

}