use std::{io::Read, fs};

fn read_file(file_name: &str) -> Vec<u32> {
    let data = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let numbers: Vec<u32> = data.split(",")
                            .map(|x| x.parse().unwrap())
                            .collect();
    return numbers;
}

pub fn lf_initialize(lf_list: Vec<u32>) -> Vec<u32> {
    let mut res = vec![0;9];
    for i in lf_list{
        res[i as usize] += 1;
    }
    return res;
}

pub fn lf_progress(mut lf_dict: Vec<u32>, days: u32) -> u32 {
    let mut d = days;
    while d > 0 {
        d -= 1;
        // keep the ones about to hatch
        let rep = lf_dict[0];
        // progress all others
        for i in 1..9{
            lf_dict[i - 1] = lf_dict[i];
        }
        // the ones that just reproduced - reset to day 6
        lf_dict[6] += rep;
        // new ones to day 8
        lf_dict[8] = rep;
    }
    return lf_dict.iter().sum();
}

fn main() {
    let lf_list = read_file("advent_day6_test.txt");
    println!("{:?}", lf_list);
    let lf_dict = lf_initialize(lf_list);
    println!("{:?}", lf_dict);
    let res = lf_progress(lf_dict, 80);
    println!("{:?} days", res);
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_read_file(){
        let lf_list = read_file("advent_day6_test.txt");
        let expected = vec![3,4,3,1,2];
        assert_eq!(lf_list, expected);
    }
}