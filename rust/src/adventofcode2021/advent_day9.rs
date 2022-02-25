use std::{fs, vec};
use std::collections::LinkedList;

static AROUND: &'static[(i8, i8)] = &[
    (-1,-1),
    (0, -1),
    (1, -1),
    (1, 0),
    (1, 1),
    (0, 1),
    (-1, 1),
    (-1, 0),
];

static AROUND_HV: &'static[(i8, i8)] = &[
    (0, -1),
    (1, 0),
    (0, 1),
    (-1, 0),
];

pub fn read_file(file_name: &str) -> Vec<Vec<u8>> {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let input: Vec<Vec<u8>> = data.split("\r\n")
                            .map(|x| x.chars().into_iter()
                                            .map(|c| c as u8 - '0' as u8).collect())
                            .collect();
    return input;
}

pub fn is_local_min(map: &Vec<Vec<u8>>, i: usize, j: usize) -> Option<u8> {
    let n = map.len() as i8;
    let m = map[0].len() as i8;
    for (di, dj) in AROUND.iter() {
        let i2 = i as i8 + di;
        let j2 = j  as i8 + dj;
        if 0 <= i2 && i2 < n && 0 <= j2 && j2 < m {
            // not a local min
            if map[i][j] > map[i2 as usize][j2 as usize] {
                return None;
            }
        }
    }
    return Some(map[i][j] + 1);
}


pub fn sum_local_min(map: Vec<Vec<u8>>) -> u32 {
    let n = map.len();
    let m = map[0].len();
    let mut s:u32 = 0;
    for i in 0..n {
        for j in 0..m{
            match is_local_min(&map, i, j) {
                Some(x) => s += x as u32,
                None => continue
            }
        }
    }
    return s;
}

pub fn get_all_local_mins(map: &Vec<Vec<u8>>) -> Vec<(usize, usize)> {
    let n = map.len();
    let m = map[0].len();
    let mut mins: Vec<(usize, usize)> = vec![];
    for i in 0..n {
        for j in 0..m{
            match is_local_min(&map, i, j) {
                Some(_x) => mins.push((i, j)),
                None => continue
            }
        }
    }
    return mins;
}

pub fn basin_size(map: &Vec<Vec<u8>>, i: usize, j: usize) -> u32 {
    let n = map.len() as i8;
    let m = map[0].len() as i8;
    let mut mark:Vec<Vec<bool>> = vec![vec![false;m as usize];n as usize];
    let mut count = 0;
    let mut queue: LinkedList<(usize, usize)> = LinkedList::new();
    queue.push_back((i, j));
    // BFS
    while !queue.is_empty() {
        let (i2, j2) = queue.pop_front().unwrap();
        // if marked - skup
        if mark[i2][j2] {
            continue;
        }
        // mark
        mark[i2][j2] = true;
        count += 1;
        
        // add surroundings
        for (di, dj) in AROUND_HV.iter() {
            let i3 = i2 as i8 + di;
            let j3 = j2 as i8 + dj;
            if 0 <= i3 && i3 < n && 0 <= j3 && j3 < m {
                // not 9 and not marked
                if !mark[i3 as usize][j3 as usize]
                    && map[i3 as usize][j3 as usize] != 9 {
                    queue.push_back((i3 as usize, j3 as usize));
                }
            }
        }
    }
    return count;
}

pub fn solve(map: Vec<Vec<u8>>) -> u32 {
    let mins = get_all_local_mins(&map);
    let mut sizes: Vec<u32> = mins.iter().map(|(i, j)| basin_size(&map, *i, *j))
                .collect();
    // sort backwards
    sizes.sort_by(|x, y| y.cmp(x));

    return (sizes[0] * sizes[1] * sizes[2]) as u32;
}



#[cfg(test)]
mod tests {
    
    use super::*;

    #[test]
    fn test_read_file() {
        let actual = read_file("advent_day9_test.txt");
        assert_eq!(actual.len(), 5);
        let expected = [[2,1,9,9,9,4,3,2,1,0],
        [3,9,8,7,8,9,4,9,2,1],
        [9,8,5,6,7,8,9,8,9,2],
        [8,7,6,7,8,9,6,7,8,9],
        [9,8,9,9,9,6,5,6,7,8]];
        
        assert_eq!(actual, expected);
    }

    #[test]
    fn test_is_local_min() {
        let map = read_file("advent_day9_test.txt");
        assert_eq!(Some(1), is_local_min(&map, 0, 9));
        assert_eq!(Some(2), is_local_min(&map, 0, 1));
        assert_eq!(Some(6), is_local_min(&map, 2, 2));
        assert_eq!(Some(6), is_local_min(&map, 4, 6));
    }

    #[test]
    fn test_sum_local_min_test() {
        let map = read_file("advent_day9_test.txt");
        let s = sum_local_min(map);
        assert_eq!(s, 15);
    }

    #[test]
    fn test_sum_local_min() {
        let map = read_file("advent_day9.txt");
        let s = sum_local_min(map);
        println!("Sum of local mins: {}", s);
    }

    #[test]
    fn test_basin_size() {
        let map = read_file("advent_day9_test.txt");
        assert_eq!(basin_size(&map, 0, 0), 3);
        assert_eq!(basin_size(&map, 0, 9), 9);
        assert_eq!(basin_size(&map, 2, 2), 14);
        assert_eq!(basin_size(&map, 3, 7), 9);
    }

    #[test]
    fn test_solve_test() {
        let map = read_file("advent_day9_test.txt");
        assert_eq!(solve(map), 1134);
    }

    #[test]
    fn test_solve() {
        let map = read_file("advent_day9.txt");
        println!("Sol = {}", solve(map));
    }
}