use std::{fmt::Display, fs, ops::Range};


pub enum SFNumber {
    RegNumber {n: u32},
    Pair{left: Box<SFNumber>, right: Box<SFNumber>},
}
use itertools::enumerate;
use regex::Regex;

use self::SFNumber::{Pair, RegNumber};

impl SFNumber{
    pub fn mag(&self) -> u32 {
        match self {
            RegNumber {n} => *n,
            Pair{left, right} => 3*&left.mag() + 2*&right.mag()
        }
    }
}

impl Display for SFNumber {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            RegNumber {n } => f.write_fmt(format_args!("{}", n)),
            Pair{left, right} => f.write_fmt(format_args!("[{},{}]", left.as_ref(), right.as_ref())),
        }
    }
}

impl From<&str> for SFNumber {
    fn from(input: &str) -> Self {
        let len = input.len();
        if input.len() == 1 {
            return RegNumber{n: input.parse::<u32>().unwrap()}
        } else {
            // find splitting comma
            let mut d = 0;
            let mut m = 0;
            for (i, c) in input.chars().enumerate() {
                if i == 0 { continue; }
                if c == '[' {
                    d += 1;
                } else if c == ']' {
                    d -= 1;
                } else if c == ',' && d == 0 {
                    m = i;
                    break;
                }
            }
            let l = SFNumber::from(&input[1..m]);
            let r = SFNumber::from(&input[m+1..len-1]);
            return Pair{left: Box::new(l), right: Box::new(r)};
        }
    }
}

pub fn read_file(file_name: &str) -> Vec<String> {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let input: Vec<String> = data.split("\r\n")
                                .map(|l| String::from(l))
                                .collect();
    return input;
}

pub fn add(num1: &mut String, num2: &str){
    // add num2 to num1 and store it in num1
    num1.insert_str(0, "[");
    num1.push_str(",");
    num1.push_str(num2);
    num1.push_str("]");
    reduce(num1);
}

pub fn add_all(nums: Vec<String>) -> String {
    let mut total: String = String::new();
    for (i, n) in enumerate(nums.iter()) {
        if i == 0 {
            total = String::from(n);
        }
        else {
            add(&mut total, n);
        }
    }
    return total;
}

pub fn largest_mag_sum(nums: Vec<String>) -> u32 {
    let mut max_mag = 0;
    let n = nums.len();
    for i in 0..n {
        println!("{}", i);
        for j in 0..n{
            if i != j {
                let mut n1 = nums[i].clone();
                add(&mut n1, nums[j].as_str());
                let mag = SFNumber::from(n1.as_str()).mag();
                if mag > max_mag {
                    max_mag = mag;
                    println!("  {}",max_mag);
                }
            }

        }
    }
    return max_mag;

}

pub fn reduce(exp: &mut String)  {
    loop {
        // find four nested pair
        let f = find4_nested(exp.as_str());
        if f.is_some() {
            // explode
            explode(exp, f.unwrap());
            continue;
        } 
        // split
        if !split(exp) {
            // finish when not reduce and not explode
            break;
        }

    }
}

pub fn explode(res: &mut String, ran: Range<usize>) {
    // left and right number - TODOhandle multidigit
    //let i = ran.start;
    let parts: Vec<&str> = res[ran.start+1..ran.end-1].split(",").collect();
    let l = parts[0].parse::<u32>().unwrap();
    let r = parts[1].parse::<u32>().unwrap();


    // replace pair with zero
    res.replace_range(ran.clone(),"0");
    let re = Regex::new(r"\d+").unwrap();
    // any number to the right
    if ran.start + 1 < res.len() {
        let m = re.find_at(res.as_str(), ran.start + 1);
        if m.is_some() {
            let ran2 = m.unwrap().range();
            let n = m.unwrap().as_str().parse::<u32>().unwrap();
            res.replace_range(ran2, (n + r).to_string().as_str());
        }
    }
    // any number to the left
    if ran.start > 0 {
        let m = re.find_iter(&res[..ran.start]).into_iter().last();
        if m.is_some() {
            let ran2 = m.unwrap().range();
            let n = m.unwrap().as_str().parse::<u32>().unwrap();
            res.replace_range(ran2, (n + l).to_string().as_str());
        }
    }
}

pub fn split(res: &mut String) -> bool {
    let re = Regex::new(r"\d\d+").unwrap();
    match re.find(res) {
        Some(m) => {
            let n = m.as_str().parse::<u32>().unwrap();
            let l = n / 2;
            let r = if n % 2 == 0 { n / 2 } else { n / 2 + 1 };
            let ran = m.range();
            res.replace_range(ran, format!("[{},{}]", l, r).as_str());
            return true;
        },
        None => return false
    }
}

pub fn find4_nested(exp: &str) -> Option<Range<usize>> {
    // find four nested pair
    let mut d = 0;
    let re = Regex::new(r"\[\d+,\d+\]").unwrap();
        
    for (i, c) in exp.chars().enumerate() {
        if c == '[' {
            d += 1;
            if d > 4 {
                // four levels deep
                let m = re.find_at(&exp, i);
                if m.is_some() {
                    return Some(m.unwrap().range());
                } 
            }
        } else if c == ']' {
            d -= 1;
        } 
    }

    return None;
}



#[cfg(test)]
mod tests {
    
    use super::*;
    use regex::Regex;

    #[test]
    fn test_mag(){
        let sf = SFNumber::from("5");
        assert_eq!(sf.mag(), 5);
        let sf = SFNumber::from("[9,1]");
        assert_eq!(sf.mag(), 29);
        let sf = SFNumber::from("[1,9]");
        assert_eq!(sf.mag(), 21);
        let sf = SFNumber::from("[[9,1],[1,9]]");
        assert_eq!(sf.mag(), 129);
        //
        let sf = SFNumber::from("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
        assert_eq!(sf.mag(), 3488);
    }

    #[test]
    fn test_regex(){
        let re = Regex::new(r"^\d{4}-\d{2}-\d{2}$").unwrap();
        assert!(re.is_match("2014-01-01"));
    }

    #[test]
    fn test_re_find_four(){
        let re = Regex::new(r"\[\d+,\d+\]").unwrap();
        let m = re.find("[[[[[9,8],1],2],3],4]").unwrap();
        assert_eq!(m.as_str(), "[9,8]");
        let r = m.range();
        assert_eq!(r.start, 4);
    }

    #[test]
    fn test_find_four(){
        let s = "[[[[[9,8],1],2],3],4]";
        assert_eq!(find4_nested(s), Some(4..9));
        //
        let s = "[7,[6,[5,[4,[3,2]]]]]";
        assert_eq!(find4_nested(s), Some(12..17));
        let s = "[[6,[5,[4,[3,2]]]],1]";
        assert_eq!(find4_nested(s), Some(10..15));
        let s = "[[6,[5,[4,[3,2]]]],1]";
        assert_eq!(find4_nested(s), Some(10..15));
        let s = "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]";
        assert_eq!(find4_nested(s), Some(24..29));
        
    }

    #[test]
    fn test_explode_left(){
        let mut s = String::from("[7,[6,[5,[4,[3,2]]]]]");
        explode(&mut s, 12..17);
        assert_eq!(s, "[7,[6,[5,[7,0]]]]");
    }

    #[test]
    fn test_explode_right(){
        let mut s = String::from("[[6,[5,[4,[3,2]]]],1]");
        explode(&mut s, 10..15);
        assert_eq!(s, "[[6,[5,[7,0]]],3]");
    }

    #[test]
    fn test_explode() {
        let mut s = String::from("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]");
        assert_eq!(find4_nested(s.as_str()), Some(4..9));
        explode(&mut s, 4..9);
        assert_eq!(s, "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]");
        assert_eq!(find4_nested(s.as_str()), Some(16..21));
        explode(&mut s, 16..21);
        assert_eq!(s, "[[[[0,7],4],[15,[0,13]]],[1,1]]");

        let mut s = String::from("[[[[[9,8],1],2],3],4]");
        let i = find4_nested(s.as_str()).unwrap();
        explode(&mut s, i);
        assert_eq!(s, "[[[[0,9],2],3],4]");

        let mut s = String::from("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
        let i = find4_nested(s.as_str()).unwrap();
        explode(&mut s, i);
        assert_eq!(s, "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");

        let mut s = String::from("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
        let i = find4_nested(s.as_str()).unwrap();
        explode(&mut s, i);
        assert_eq!(s, "[[3,[2,[8,0]]],[9,[5,[7,0]]]]");
    }

    #[test]
    fn test_split(){
        let mut s = String::from("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]");
        split(&mut s);
        assert_eq!(s, "[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]");
    }

    #[test]
    fn test_reduce(){
        let mut s = String::from("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]");
        reduce(&mut s);
        assert_eq!(s, "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
        
        let mut s = String::from("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]");
        reduce(&mut s);
        assert_eq!(s, "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
        
    }

    #[test]
    fn test_add(){
        let mut n1 = String::from("[[[[4,3],4],4],[7,[[8,4],9]]]");
        let n2 = "[1,1]";
        add(&mut n1, n2);
        assert_eq!(n1, "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
    }

    #[test]
    fn test_add2(){
        let mut n1 = String::from("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]");
        let n2 = "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]";
        add(&mut n1, n2);
        assert_eq!(n1, "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]");
    }

    #[test]
    fn test_add_all_test1(){
        let nums = read_file("advent_day18_test1.txt");
        let res = add_all(nums);
        assert_eq!(res, "[[[[1,1],[2,2]],[3,3]],[4,4]]");
    }

    #[test]
    fn test_add_all_test2(){
        let nums = read_file("advent_day18_test2.txt");
        let res = add_all(nums);
        assert_eq!(res, "[[[[5,0],[7,4]],[5,5]],[6,6]]");
    }

    #[test]
    fn test_add_all_test3(){
        let nums = read_file("advent_day18_test3.txt");
        let res = add_all(nums);
        assert_eq!(res, "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
    }

    #[test]
    fn test_add_all_test4(){
        let nums = read_file("advent_day18_test4.txt");
        let res = add_all(nums);
        assert_eq!(res, "[[[[3,0],[5,3]],[4,4]],[5,5]]");
    }

    #[test]
    fn test_add_all_test5_mag(){
        let nums = read_file("advent_day18_test5.txt");
        let res = add_all(nums);
        assert_eq!(res, "[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]");
        let sf = SFNumber::from(res.as_str());
        assert_eq!(sf.mag(), 4140);
    }

    #[test]
    fn test_add_all_mag(){
        let nums = read_file("advent_day18.txt");
        let res = add_all(nums);
        println!("Sum result = {}", res);
        let sf = SFNumber::from(res.as_str());
        println!("Mag = {}", sf.mag());
    }

    #[test]
    fn test_largest_mag_test(){
        let nums = read_file("advent_day18_test5.txt");
        let r = largest_mag_sum(nums);
        assert_eq!(r, 3993)
    }

    #[test]
    fn test_largest_mag(){
        let nums = read_file("advent_day18.txt");
        let r = largest_mag_sum(nums);
        println!("Largest sum mag = {}", r);
    }

}
