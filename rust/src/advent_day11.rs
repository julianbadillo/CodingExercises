use std::{fs, vec};

pub fn read_file(file_name: &str) -> LightGrid {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let input: Vec<Vec<u8>> = data.split("\r\n")
                            .map(|x| x.chars().into_iter()
                                            .map(|c| c as u8 - '0' as u8).collect())
                            .collect();
    return LightGrid::from(input);
}

static AROUND: &'static[(isize, isize)] = &[
    (-1,-1),
    (0, -1),
    (1, -1),
    (1, 0),
    (1, 1),
    (0, 1),
    (-1, 1),
    (-1, 0),
];

pub struct LightGrid {
    grid: Vec<Vec<u8>>,
    n: usize,
    m: usize,
}

impl From<Vec<Vec<u8>>> for LightGrid {
    fn from(input: Vec<Vec<u8>>) -> LightGrid {
        LightGrid {n: input.len(), m: input.len(), grid: input}
    }
}

impl LightGrid {
    pub fn one_step(&mut self) -> u32 {
        let mut flashed = vec![vec![false; self.m]; self.n];
        // increase all by one
        for i in 0..self.n {
            for j in 0..self.m {
                self.grid[i][j] += 1;
            }
        }
        // while still flashed
        loop {
            let mut go = false;
            for i in 0..self.n {
                for j in 0..self.m {
                    if self.grid[i][j] > 9 && !flashed[i][j] {
                        flashed[i][j] = true;
                        go = true;
                        // increase all around
                        for (di, dj) in AROUND.iter() {
                            let i2 = i as isize + di;
                            let j2 = j as isize + dj;
                            // within bounds
                            if 0 <= i2 && i2 < self.n as isize && 0 <= j2 && j2 < self.m as isize{
                                self.grid[i2 as usize][j2 as usize] += 1;
                            }
                        }
                    }
                }
            }
            if !go {
                break;
            }
        }
        // set all flashed to zero
        let mut count = 0;
        // increase all by one
        for i in 0..self.n {
            for j in 0..self.m {
                if flashed[i][j] {
                    self.grid[i][j] = 0;
                    count += 1;
                }
            }
        }
        return count;
    }

    pub fn n_steps(&mut self, n: u32) -> u32 {
        let mut s: u32 = 0;
        for _i in 0..n{
            s += self.one_step();
        }
        return s;
    }
    
    pub fn first_all_flash(&mut self) -> u32 {
        let mut step: u32 = 0;
        loop {
            step += 1;
            if self.one_step() == (self.n * self.m) as u32 {
                return step;
            }
        }
    }
    

}



#[cfg(test)]
mod tests {
    
    use super::*;

    #[test]
    fn test_read_file() {
        let actual = read_file("advent_day11_test.txt");
        assert_eq!(actual.n, 10);
        let expected = [[5,4,8,3,1,4,3,2,2,3],
        [2,7,4,5,8,5,4,7,1,1],
        [5,2,6,4,5,5,6,1,7,3],
        [6,1,4,1,3,3,6,1,4,6],
        [6,3,5,7,3,8,5,4,7,8],
        [4,1,6,7,5,2,4,6,4,5],
        [2,1,7,6,8,4,1,7,2,1],
        [6,8,8,2,8,8,1,1,3,4],
        [4,8,4,6,8,4,8,5,5,4],
        [5,2,8,3,7,5,1,5,2,6]];
        
        assert_eq!(actual.grid, expected);
    }
    
    #[test]
    fn test_one_step() {
        let mut lg = LightGrid::from( vec![
                                                vec![1,1,1,1,1],
                                                vec![1,9,9,9,1],
                                                vec![1,9,1,9,1],
                                                vec![1,9,9,9,1],
                                                vec![1,1,1,1,1],]); 
        let expected1: Vec<Vec<u8>> = vec![
                vec![3,4,5,4,3],
                vec![4,0,0,0,4],
                vec![5,0,0,0,5],
                vec![4,0,0,0,4],
                vec![3,4,5,4,3],
            ];    
        let expected2: Vec<Vec<u8>> = vec![
            vec![4,5,6,5,4],
            vec![5,1,1,1,5],
            vec![6,1,1,1,6],
            vec![5,1,1,1,5],
            vec![4,5,6,5,4],
        ];
        let mut r = lg.one_step();
        assert_eq!(r, 9);
        assert_eq!(expected1, lg.grid);
        r = lg.one_step();
        assert_eq!(r, 0);
        assert_eq!(expected2, lg.grid);
    }

    #[test]
    fn test_n_steps_test(){
        let mut actual = read_file("advent_day11_test.txt");
        let r = actual.n_steps(10);
        assert_eq!(r, 204);
    }

    #[test]
    fn test_n_steps_test2(){
        let mut actual = read_file("advent_day11_test.txt");
        let r = actual.n_steps(100);
        assert_eq!(r, 1656);
    }

    #[test]
    fn test_n_steps(){
        let mut actual = read_file("advent_day11.txt");
        let r = actual.n_steps(100);
        println!("Total Flashes = {}", r);
    }

    #[test]
    fn test_first_all_flash_test(){
        let mut actual = read_file("advent_day11_test.txt");
        let r = actual.first_all_flash();
        assert_eq!(r, 195);
    }

    #[test]
    fn test_first_all_flash(){
        let mut actual = read_file("advent_day11.txt");
        let r = actual.first_all_flash();
        println!("First to flash = {}", r);
    }
}