use std::{fs, vec, ops::Index, io::Read};
use itertools::{Permutations, Itertools};

pub struct ReadLine {
    readings: Vec<String>,
    output: Vec<String>
}

static VALID_DIGITS: &'static[&str] = &["abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg"];

impl ReadLine {
    
    pub fn count_unique_output(&self) -> u32 {
        self.output.iter()
                    .filter(|&x| x.len() == 2||x.len()==4||x.len()==3||x.len()==7)
                    .count() as u32
    }


    pub fn encode(perm: &str, display: &str) -> String {
        // apply permutation
        let mut result = String::new();
        let perm2: Vec<char> = perm.chars().collect();
        // replace each char with its corresponding encoding
        for c in display.chars(){
            let pos = c as usize - 'a' as usize;
            result.push(perm2[pos]);
        }
        // sort
        let mut chars: Vec<char> = result.chars().collect();
        chars.sort_by(|a, b| a.cmp(b));
        // return result
        String::from_iter(chars)
    }

    pub fn get_digit(perm: &str, display: &str) -> u8 {
        let enc = ReadLine::encode(perm, display);
        match VALID_DIGITS.iter().position(|&x| x == enc) {
            Some(r) => r as u8,
            None => panic!()
        }
    }

    pub fn is_valid_perm(perm: &str, display: &str) -> bool {
        let result = ReadLine::encode(perm, display);
        if VALID_DIGITS.contains(&result.as_str()) {
            return true;
        }
        return false;
    }

    pub fn is_valid_perm_rl(&self, perm: &str) -> bool {
        // test permutations on all entries
        let readings_ok = self.readings.iter()
                                    .all(|x| ReadLine::is_valid_perm(perm, &x));
        let outputs_ok = self.output.iter()
                                    .all(|x| ReadLine::is_valid_perm(perm, &x));

        return readings_ok && outputs_ok;
    }

    pub fn decode_output(&self, perm: &str) -> u32 {
        let mut r: u32 = 0;
        for out in self.output.iter() {
            r = r * 10 + ReadLine::get_digit(perm, out) as u32;
        }
        return r;
    }

    pub fn solve_rl(&self) -> Option<u32> {
        // test permutations on all entries
        let items = vec!['a', 'b', 'c', 'd', 'e', 'f', 'g'];
        for perm in items.iter().permutations(items.len()){
            let s = String::from_iter(perm);
            if self.is_valid_perm_rl(&s){
                return Some(self.decode_output(&s));
            }
        }
        return None;
    }
}

impl From<&str> for ReadLine {
    fn from(item: &str) -> Self {
        let mut rl = ReadLine {readings: vec![], output: vec![]};
        let mut l = item.split(" | ");
        rl.readings = l.next().unwrap().split_whitespace()
                                .map(|x| x.to_string())
                                .collect();
        rl.output = l.next().unwrap().split_whitespace()
                                .map(|x| x.to_string())
                                .collect();
        return  rl;
    }

}


pub fn read_file(file_name: &str) -> Vec<ReadLine> {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let input: Vec<ReadLine> = data.split("\r\n")
                            .map(|x| ReadLine::from(x))
                            .collect();
    return input;
}

pub fn count_unique_output(input: Vec<ReadLine>) -> u32 {
    input.iter().map(|rl| rl.count_unique_output()).sum()
}

pub fn sum_all_output(lines: Vec<ReadLine> ) -> u32 {
    lines.iter().map(|rl| rl.solve_rl().unwrap()).sum::<u32>()
}



#[cfg(test)]
mod tests {
    use itertools::Itertools;

    use super::*;
    #[test]
    fn test_read_line() {
        let actual = ReadLine::from("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe");
        
        let expected_read: Vec<String> = vec!["be", "cfbegad", "cbdgef", "fgaecd", "cgeb", "fdcge", "agebfd", "fecdb", "fabcd", "edb"]
                        .iter().map(|x| x.to_string()).collect();
        let expected_out: Vec<String> = vec!["fdgacbe", "cefdb", "cefbgd", "gcbe"]
                        .iter().map(|x| x.to_string()).collect();
        
        assert_eq!(actual.readings, expected_read);
        assert_eq!(actual.output, expected_out);
    }
    

    #[test]
    fn test_read_file() {
        let actual = read_file("advent_day8_test.txt");
        assert_eq!(actual.len(), 10);
        let expected_read: Vec<String> = vec!["be", "cfbegad", "cbdgef", "fgaecd", "cgeb", "fdcge", "agebfd", "fecdb", "fabcd", "edb"]
                        .iter().map(|x| x.to_string()).collect();
        let expected_out: Vec<String> = vec!["fdgacbe", "cefdb", "cefbgd", "gcbe"]
                        .iter().map(|x| x.to_string()).collect();
        
        assert_eq!(actual[0].readings, expected_read);
        assert_eq!(actual[0].output, expected_out);
    }

    #[test]
    fn test_count_out() {
        let rl = ReadLine::from("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe");
        assert_eq!(rl.count_unique_output(), 2);
        let rl = ReadLine::from("edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc");
        assert_eq!(rl.count_unique_output(), 3);
    }

    
    #[test]
    fn test_all_test() {
        let input = read_file("advent_day8_test.txt");
        let actual = count_unique_output(input);
        assert_eq!(actual, 26);
    }

    #[test]
    fn test_all() {
        let input = read_file("advent_day8.txt");
        let actual = count_unique_output(input);
        assert_eq!(actual, 310);
    }

    #[test]
    fn test_encode (){
        // a -> c
        // b -> f
        let perm = "deafgbc";
        assert_eq!(ReadLine::encode(perm, "cf"), "ab");
        assert_eq!(ReadLine::encode(perm, "abdfg"), "bcdef");
        assert_eq!(ReadLine::encode(perm, "acf"), "abd");
        assert_eq!(ReadLine::encode(perm, "acdeg"), "acdfg");
     
        let perm2 = "cfgabde";
        assert_eq!(ReadLine::encode(perm2, "ab"), VALID_DIGITS[1]);
        assert_eq!(ReadLine::encode(perm2, "cdfbe"), VALID_DIGITS[5]);
        assert_eq!(ReadLine::encode(perm2, "gcdfa"), VALID_DIGITS[2]);
        assert_eq!(ReadLine::encode(perm2, "dab"),VALID_DIGITS[7]);
    }

    
    #[test]
    fn test_get_digit (){
        // a -> c
        // b -> f
        let perm2 = "cfgabde";
        assert_eq!(ReadLine::get_digit(perm2, "ab"), 1);
        assert_eq!(ReadLine::get_digit(perm2, "cdfbe"), 5);
        assert_eq!(ReadLine::get_digit(perm2, "gcdfa"), 2);
        assert_eq!(ReadLine::get_digit(perm2, "dab"), 7);
    }

    #[test]
    fn test_is_valid_perm() {
        // test basic digits
        for d in VALID_DIGITS {
            assert!(ReadLine::is_valid_perm("abcdefg", d))
        }
        let perm = "cfgabde";
        let ds: Vec<&str> = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab cdfeb fcadb cdfeb cdbaf"
                            .split_whitespace()
                            .collect();
        for d in ds.iter() {
            assert!(ReadLine::is_valid_perm(perm, d));
        }
    }

    #[test]
    fn test_is_valid_perm_rl() {
        let rl = ReadLine::from("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf");
        let perm = "cfgabde";
        assert!(rl.is_valid_perm_rl(perm));
    }

    #[test]
    fn test_solve() {
        let rl = ReadLine::from("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf");
        assert_eq!(rl.solve_rl(), Some(5353));
    }

    #[test]
    fn test_sum_all_output_test() {
        let rls = read_file("advent_day8_test.txt");
        let res = sum_all_output(rls);
        assert_eq!(res, 61229);
    }

    #[test]
    fn test_sum_all_output() {
        let rls = read_file("advent_day8.txt");
        let res = sum_all_output(rls);
        print!("{}", res);
    }

    #[test]
    fn test_perms() {
        // test permutations
        let items = vec!['a', 'b', 'c'];

        for perm in items.iter().permutations(items.len()){
            println!("{:?}", perm);
        }
    }

}