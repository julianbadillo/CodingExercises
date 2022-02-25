use std::iter::Peekable;
use std::{io::{BufRead, BufReader, Lines}, fs::File};

struct BingoCard {
    numbers:Vec<Vec<u8>>,
    marked:Vec<Vec<bool>>
}

const bingo_size:usize = 5;

impl BingoCard {
    fn from_str(lines: &mut Peekable<Lines<BufReader<File>>>) -> BingoCard {
        let mut bingo = BingoCard{
            numbers: vec![], 
            marked: vec![vec![false; bingo_size], 
                        vec![false; bingo_size], 
                        vec![false; bingo_size], 
                        vec![false; bingo_size], 
                        vec![false; bingo_size]]};
        for i in 0..bingo_size {
            let numbers: Vec<u8> = lines.next().unwrap().unwrap().split_whitespace()
                                            .map(|x| x.parse().unwrap())
                                            .collect();
            bingo.numbers.push(numbers);
        }

        return bingo;
    }

    fn mark_number(&mut self, number: &u8) -> Option<u32> {
        for i in 0..bingo_size{
            for j in 0..bingo_size {
                if self.numbers[i][j] == *number {
                    self.marked[i][j] = true;
                    // look row
                    if self.marked[i].iter().all(|x| *x) {
                        let res = self.numbers[i].iter().fold(0, |a, b| a + b) as u32;
                        return Some(res);
                    }
                    // TODO look column
                }
            }
        }
        return None;
    }


    fn print(&self){
        for l in self.numbers.iter() {
            println!("{:?}", l);
        }
        println!()
    }
}

fn play_bingo(mut bingos: Vec<BingoCard>, mut numbers: Vec<u8>) -> u32 {
    let i = 0;
    for n in numbers.iter_mut() {
        for b in bingos.iter_mut() {
            match b.mark_number(n) {
                None => {},
                Some(result) => {
                    println!("Bingo!");
                    println!("Resulst {}", result);
                    return result * (i as u32);
                }
            }
        }
    }

    return 0;
}



fn main() {
    
    let f = File::open("advent_day4_test.txt").expect("Unable to open");
    let mut lines = BufReader::new(f).lines().peekable();
    let mut numbers: Vec<u8> = vec![];
    let mut bingos: Vec<BingoCard> = vec![];
    let mut i = 0;
    while lines.peek().is_some()  {
        let line = lines.next().unwrap().unwrap();
        // read ballot numbers
         if i == 0 {
            numbers = line.split(",")
                        .map(|x| x.parse().unwrap())
                        .collect()
            
        } else {
            // bingo card numbers
            let bingo = BingoCard::from_str(&mut lines);
            bingo.print();
            bingos.push(bingo);
        }
    
        i += 1;
    }
    println!("Here!!");
    let r = play_bingo(bingos, numbers);
    println!("{}", r);
}



#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_bingo_from_str(){
        let feed = " 8  2 23  4 24\n"+
                    "21  9 14 16  7\n"+
                    "6 10  3 18  5\n"+
                    "1 12 20 15 19";
        let bingo = BingoCard::from_str(feed);
        assert_eq!()
    }

    #[test]
    fn test_add() {
        assert_eq!(add(1, 2), 3);
    }
}