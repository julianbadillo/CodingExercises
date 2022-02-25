use std::{fs, vec, collections::LinkedList};

pub fn read_file(file_name: &str) -> Vec<Vec<u8>> {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let input: Vec<Vec<u8>> = data.split("\r\n")
                            .map(|x| x.as_bytes()
                                            .iter()
                                            .map(|c| c - b'0')
                                            .collect())
                            .collect();
    return input;
}


static AROUND_HV: &'static[(isize, isize)] = &[
    (0, -1),
    (1, 0),
    (0, 1),
    (-1, 0),
];


pub fn bfs_min_risk(risk: &Vec<Vec<u8>>) -> u32 {
    let n = risk.len();
    let m = risk[0].len();
    let total_risk: u32 = risk.into_iter().flatten().map(|x| *x as u32).sum();
    let mut min_risk = vec![vec![total_risk; m]; n];
    min_risk[0][0] = 0;
    let mut queue: LinkedList<(usize, usize)> = LinkedList::new();

    queue.push_back((0, 0));
    while !queue.is_empty() {
        let (i, j) = queue.pop_front().unwrap();
        for (di, dj) in AROUND_HV.iter() {
            let i2 = i as isize + di;
            let j2 = j  as isize + dj;
            if 0 <= i2 && i2 < n as isize && 0 <= j2 && j2 < m as isize {
                let i2_ = i2 as usize;
                let j2_ = j2 as usize;
                // if worth moving there
                if (min_risk[i][j] + risk[i2_][j2_] as u32) < min_risk[i2_][j2_] {
                    min_risk[i2_][j2_] = min_risk[i][j] + risk[i2_][j2_] as u32;
                    let p2 = (i2_, j2_);
                    queue.push_back(p2);
                }
            }
        }
    }
    min_risk[n -1][m - 1]
}

pub fn bfs_min_risk_expanded(risk: &Vec<Vec<u8>>) -> u32 {
    let n = risk.len();
    let m = risk[0].len();
    let total_risk: u32 = risk.into_iter().flatten().map(|x| *x as u32).sum::<u32>() * 25;
    let mut min_risk = vec![vec![total_risk; m * 5]; n * 5];
    min_risk[0][0] = 0;
    let mut queue: LinkedList<(usize, usize)> = LinkedList::new();

    queue.push_back((0, 0));
    while !queue.is_empty() {
        let (i, j) = queue.pop_front().unwrap();
        for (di, dj) in AROUND_HV.iter() {
            let i2 = i as isize + di;
            let j2 = j  as isize + dj;
            if 0 <= i2 && i2 < (n * 5) as isize && 0 <= j2 && j2 < (m * 5) as isize {
                let i2_ = i2 as usize;
                let j2_ = j2 as usize;
                // calculate real risk from the original risk matrix
                let real_risk = (risk[i2_ % n][j2_ % m]  + (i2_ / n) as u8 + (j2_ / n) as u8 - 1) % 9 + 1;
                // if worth moving there
                if (min_risk[i][j] + real_risk as u32) < min_risk[i2_][j2_] {
                    min_risk[i2_][j2_] = min_risk[i][j] + real_risk as u32;
                    let p2 = (i2_, j2_);
                    queue.push_back(p2);
                }
            }
        }
    }
    min_risk[n*5 -1][m*5 - 1]
}


#[cfg(test)]
mod tests {
    
    use super::*;

    #[test]
    fn test_read_file() {
        let x = read_file("advent_day15_test.txt");
        assert_eq!(x.len(), 10);
        assert_eq!(x[0].len(), 10);
    }

    #[test]
    fn test_bfs_min_risk_test() {
        let risk = read_file("advent_day15_test.txt");
        let r = bfs_min_risk(&risk);
        assert_eq!(r, 40);
        
    }

    #[test]
    fn test_bfs_min_risk() {
        let risk = read_file("advent_day15.txt");
        let r = bfs_min_risk(&risk);
        println!("Min risk {}", r);
        
    }

    #[test]
    fn test_bfs_min_risk_expanded_test() {
        let risk = read_file("advent_day15_test.txt");
        let r = bfs_min_risk_expanded(&risk);
        assert_eq!(r, 315);
        
    }

    #[test]
    fn test_bfs_min_risk_expanded() {
        let risk = read_file("advent_day15.txt");
        let r = bfs_min_risk_expanded(&risk);
        println!("Min risk expanded {}", r);
        
    }
}