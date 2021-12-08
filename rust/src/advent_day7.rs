use std::{fs};

pub fn read_file(file_name: &str) -> Vec<i32> {
    let data = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let numbers: Vec<i32> = data.split(",")
                            .map(|x| x.parse().unwrap())
                            .collect();
    return numbers;
}

///
/// Returns center position for min fuel usage, and total fuel usage.
/// 
pub fn min_fuel_usage(mut positions: Vec<i32>) -> (i32, i32) {
    // soert
    positions.sort();
    // median
    let median = positions[positions.len() / 2];
    // total
    let total = positions.iter()
                .map(|x| i32::abs(x - median))
                .sum();
    return (median, total);
}

///
/// Fuel usage as a function of distance
/// 
pub fn fuel_usage_sq(p1: i32, p2: i32) -> i32 {
    let d = i32::abs(p1 - p2);
    // 1 + 2 + 3 + 4 ... + n
    // (1 + n) + (2 + n - 1) + (3 + n - 2)
    // (1 + n) 
    // n * (n + 1) / 2
    // Gauss Sum


    d * (d + 1) / 2
}

///
/// For part two, using a different usage calcualtion function
/// 
pub fn min_fuel_usage_sq(positions: Vec<i32>) -> (i32, i32) {
    // minimize total
    let average1 = positions.iter().sum::<i32>() / positions.len() as i32;
    let average2 = average1 + 1;
    let average3 = average1 - 1;
    // total
    let total1: i32 = positions.iter()
                .map(|x| fuel_usage_sq(*x, average1))
                .sum();
    let total2: i32 = positions.iter()
                .map(|x| fuel_usage_sq(*x, average2))
                .sum();
    let total3: i32 = positions.iter()
                .map(|x| fuel_usage_sq(*x, average3))
                .sum();
    if total1 <= i32::min(total1, total2) {
        return (average1, total1);
    }
    else if total2 <= i32::min(total1, total3) {
        return (average2, total2);
    }
    return (average3, total3);
}


#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_read_file() {
        let numbers = read_file("advent_day7_test.txt");
        let expected = vec![16,1,2,0,4,2,7,1,2,14];
        assert_eq!(numbers, expected);
    }

    #[test]
    fn test_min_fuel_usage(){
        let positions = vec![16,1,2,0,4,2,7,1,2,14];
        let (pos, total) = min_fuel_usage(positions);
        let expected_pos = 2;
        let expected_total = 37;
        assert_eq!(expected_pos, pos);
        assert_eq!(expected_total, total);
    }

    #[test]
    fn test_min_fuel_usage_full_test(){
        let positions = read_file("advent_day7_test.txt");
        let (pos, total) = min_fuel_usage(positions);
        let expected_pos = 2;
        let expected_total = 37;
        assert_eq!(expected_pos, pos);
        assert_eq!(expected_total, total);
    }

    #[test]
    fn test_min_fuel_usage_full(){
        let positions = read_file("advent_day7.txt");
        let (pos, total) = min_fuel_usage(positions);
        println!("{} {}", expected_pos, pos);
    }

    #[test]
    fn test_min_fuel_usage_sq_full_test(){
        let positions = read_file("advent_day7_test.txt");
        let (pos, total) = min_fuel_usage_sq(positions);
        let expected_pos = 5;
        let expected_total = 168;
        assert_eq!(expected_pos, pos);
        assert_eq!(expected_total, total);
    }

    #[test]
    fn test_min_fuel_usage_sq_full(){
        let positions = read_file("advent_day7.txt");
        let (pos, total) = min_fuel_usage_sq(positions);
        println!("{} {}", pos, total);
        println!("{} {}", expected_pos, pos);
    }

    #[test]
    fn test_average(){
        let pos: Vec<i32> = vec![1,2,3,4,5];
        let avg: i32 = pos.iter().sum::<i32>() / pos.len() as i32;
        assert_eq!(3, avg);
    }
}