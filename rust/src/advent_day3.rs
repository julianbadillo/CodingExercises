use std::fs;
use std::{io::{BufRead, BufReader}, fs::File};


fn gamma_epsilon_rate(input: &Vec<String>) -> (u32, u32) {
    let n = input.len();
    let bits = input[0].len();
    let mut ones = vec![0;bits];
    for line in input {
        for (i, b) in line.chars().enumerate() {
            if b == '0'{
                ones[i]+= 1;
            }
        }
    }
    let mut gamma = String::from("");
    let mut epsilon = String::from("");
    for x in ones.iter() {
        if x > &(&n / 2) {
            gamma.push('0');
            epsilon.push('1');
        }
        else {
            gamma.push('1');
            epsilon.push('0');
        }
    }
    println!("{} {}", gamma, epsilon);
    let g: u32 = u32::from_str_radix(&gamma, 2).unwrap();
    let e: u32 = u32::from_str_radix(&epsilon, 2).unwrap();
    // return values
    return (g,e);
}

fn oxygen_rate(input: &Vec<String>) -> u32 {
    let mut n = input.len();
    let mut input2: Vec<String> = input.clone();
    let bits = input[0].len();
    let mut ones = vec![0;bits];
    let mut i = 0;
    loop {
        // get the most common
        for line in input2.iter() {
            if line.as_bytes()[i] as char == '1' {
                ones[i]+= 1;
            }
        }
        if ones[i] as f32 >= n as f32 / 2.0f32 {
            input2 = input2.into_iter().filter(|l| l.as_bytes()[i] as char =='1').collect();
        }
        else {
            input2 = input2.into_iter().filter(|l| l.as_bytes()[i] as char =='0').collect();
        }
        n = input2.len();
        if n == 1 {
            break;
        }
        i += 1;
    }
    let oxy = u32::from_str_radix(&input2[0], 2).unwrap();
    return oxy;
}

fn co2_rate(input: &Vec<String>) -> u32 {
    let mut n = input.len();
    let mut input2: Vec<String> = input.clone();
    let bits = input[0].len();
    let mut zeros = vec![0;bits];
    let mut i = 0;
    loop {
        // get the most common
        for line in input2.iter() {
            if line.as_bytes()[i] as char == '0' {
                zeros[i]+= 1;
            }
        }
        if zeros[i] as f32 <= n as f32 / 2.0f32 {
            input2 = input2.into_iter().filter(|l| l.as_bytes()[i] as char =='0').collect();
        }
        else {
            input2 = input2.into_iter().filter(|l| l.as_bytes()[i] as char =='1').collect();
        }
        n = input2.len();
        if n == 1 {
            break;
        }
        i += 1;
    }
    let co2 = u32::from_str_radix(&input2[0], 2).unwrap();
    return co2;
}

fn main() {
    //let data = fs::read_to_string("advent_day2.txt").expect("Unable to read file");
    // let data = fs::read_to_string("advent_day3.txt")
    //                      .expect("Unable to read file");
    // let lines: Vec<&str> = data.split("\r\n").collect();
    
    let f = File::open("advent_day3.txt").expect("Unable to open");
    let lines:Vec<String> = BufReader::new(f)
                                        .lines()
                                        .map(|l| l.unwrap())
                                        .collect();

    let (gamma, epsilon) = gamma_epsilon_rate(&lines);
    println!("{} {} {}", &gamma, &epsilon, gamma*epsilon);

    let oxy = oxygen_rate(&lines);
    let co2 = co2_rate(&lines);
    println!("{} {} {}", &oxy, &co2, oxy*co2);
}

