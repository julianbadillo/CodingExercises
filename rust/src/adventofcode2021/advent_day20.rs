use std::{fs, ops::Range, collections::HashSet};



pub fn read_file(file_name: &str) -> (Vec<char>, HashSet<(i32,i32)>) {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let mut pix: HashSet<(i32, i32)> = HashSet::new();
    let mut alg= vec![];
    let mut i: usize = 0;
    for (ln, line) in  data.split("\r\n").enumerate() {
        if ln == 0 {
            alg = line.chars().collect();
        } else if !line.is_empty() {
            for (j, c) in line.chars().into_iter().enumerate() {
                if c == '#' {
                    pix.insert((i as i32, j as i32));
                }
            }
            i += 1;
        }
    }
    return (alg, pix);
}

pub fn pixel_idx(pix: &HashSet<(i32, i32)>, i: i32, j: i32) -> u32 {
    let mut idx = 0;
    for di in -1..2 {
        for dj in -1..2 {
            // shift
            idx <<= 1;
            // a bit
            if pix.contains(&(i + di, j + dj)){
                idx |= 1;
            }
        }
    }
    return idx;
}

pub fn pixel_idx_edge(pix: &HashSet<(i32, i32)>, i: i32, j: i32, ran_i: Range<i32>, ran_j: Range<i32>, inf_char: bool) -> u32 {
    let mut idx = 0;
    for di in -1..2 {
        for dj in -1..2 {
            // shift
            idx <<= 1;

            // a bit
            if ran_i.start <= i + di && i + di <= ran_i.end - 1
                && ran_j.start <= j + dj && j + dj <= ran_j.end - 1
                 && pix.contains(&(i + di, j + dj)){
                idx |= 1;
            }
            // if out of range but infinite is light
            else if (i + di < ran_i.start || i + di > ran_i.end -1 || j + dj < ran_j.start || j + dj > ran_j.end -1 ) && inf_char {
                idx |= 1;
            }
        }
    }
    return idx;
}



pub fn enhance(pix: &HashSet<(i32, i32)>, alg: &Vec<char>, inf_char: bool) -> HashSet<(i32, i32)> {
    // Tried this, but didn't work - still don't understand why
    // let ran_i = Range { start: pix.iter().map(|p| p.0).min().unwrap() - 1,
    //                                 end: pix.iter().map(|p| p.0).max().unwrap() + 2};
    // let ran_j = Range { start: pix.iter().map(|p| p.1).min().unwrap() - 1,
    //                                 end: pix.iter().map(|p| p.1).max().unwrap() + 2};

    let ran_i: Range<i32> = -100..200;
    let ran_j: Range<i32> = -100..200;

    let mut res: HashSet<(i32, i32)> = HashSet::new();
    
    for i in ran_i.clone() {
        for j in ran_j.clone() {
            // if not in the edges of the known image
            let idx: usize = pixel_idx_edge(pix, i, j, ran_i.clone(), ran_j.clone(), inf_char) as usize;
            if alg[idx] == '#' {
                res.insert((i, j));
            }
        }
    }
    return res;
}

pub fn print_pix(pix: &HashSet<(i32, i32)>) {
    let ran_i = Range { start: pix.iter().map(|p| p.0).min().unwrap(),
        end: pix.iter().map(|p| p.0).max().unwrap() + 1};
    let ran_j = Range { start: pix.iter().map(|p| p.1).min().unwrap(),
            end: pix.iter().map(|p| p.1).max().unwrap() + 1};
    for i in ran_i {
        for j in ran_j.clone() {
            print!("{}", if pix.contains(&(i, j)) {"#"} else {"."})
        }
        println!();
    }
}


#[cfg(test)]
mod tests {
    
    use super::*;
    
    #[test]
    fn test_read_file(){
        let (a, p) = read_file("advent_day20_test.txt");
        assert_eq!(a.len(), 512);
        assert!(p.contains(&(0, 3)));
        assert!(p.contains(&(0, 0)));
        assert!(p.contains(&(4, 4)));
        print_pix(&p);
    }

    #[test]
    fn test_pixel_idx(){
        let (_a, p) = read_file("advent_day20_test.txt");
        let r = pixel_idx(&p, 2, 2);
        assert_eq!(r, 34);
    }

    #[test]
    fn test_pixel_idx_edge(){
        let (_a, p) = read_file("advent_day20_test.txt");
        let r = pixel_idx_edge(&p, 2, 2, 0..6, 0..6, false);
        assert_eq!(r, 34);
        
        let r = pixel_idx_edge(&p, 0, 0, 0..5, 0..5, false);
        assert_eq!(r, 0b000010010);

        let r = pixel_idx_edge(&p, 0, 4, 0..5, 0..5, false);
        assert_eq!(r, 0b000100000);

        let r = pixel_idx_edge(&p, 4, 4, 0..5, 0..5, false);
        assert_eq!(r, 0b000110000);


        let r = pixel_idx_edge(&p, 0, 0, 0..5, 0..5, true);
        assert_eq!(r, 0b111110110);

        let r = pixel_idx_edge(&p, 0, 4, 0..5, 0..5, true);
        assert_eq!(r, 0b111101001);

        let r = pixel_idx_edge(&p, 4, 4, 0..5, 0..5, true);
        assert_eq!(r, 0b001111111);

        for i in 0..5 {
            for j in 0..5 {
                assert_eq!(pixel_idx(&p, i, j), pixel_idx_edge(&p, i, j, 0..5, 0..5, false), "Erorr! {}, {}", i, j);
            }
        }

    }

    #[test]
    fn test_enhance_test(){
        let (a, p) = read_file("advent_day20_test.txt");
        let p2 = enhance(&p, &a, false);
        assert!(p2.contains(&(2,2)));
        print_pix(&p2);
        println!();
        let p3 = enhance(&p2, &a, false);
        print_pix(&p3);
        assert_eq!(p3.len(), 35);
    }

    #[test]
    fn test_enhance_test_50(){
        let (a, p) = read_file("advent_day20_test.txt");
        let mut p2 = p;
        for _i in 0..50 {
            p2 = enhance(&p2, &a, false);
        }
        assert_eq!(p2.len(), 3351);
    }

    #[test]
    fn test_enhance_small(){
        let (a, p) = read_file("advent_day20_test2.txt");
        assert_eq!(a.len(), 512);
        println!("Last pixel = {}", a[0b111111111 as usize]);
        let max_i = p.iter().map(|x| x.0).max().unwrap();
        assert_eq!(max_i, 4);
        let max_j = p.iter().map(|x| x.1).max().unwrap();
        assert_eq!(max_j, 4);
        let mut p2 = p;

        print_pix(&p2);
        println!();

        p2 = enhance(&p2, &a, false);
        print_pix(&p2);
        println!();
        
        p2 = enhance(&p2, &a, true);
        print_pix(&p2);
        println!("Pixels on  = {}", p2.len());
    }



    #[test]
    fn test_enhance(){
        let (a, p) = read_file("advent_day20.txt");
        assert_eq!(a.len(), 512);
        println!("Last pixel = {}", a[0b111111111 as usize]);
        let max_i = p.iter().map(|x| x.0).max().unwrap();
        assert_eq!(max_i, 99);
        let max_j = p.iter().map(|x| x.1).max().unwrap();
        assert_eq!(max_j, 99);
        let mut p2 = p;
        p2 = enhance(&p2, &a, false);
        //print_pix(&p2);
        //println!("----------------------------------------");
        p2 = enhance(&p2, &a, true);
        print_pix(&p2);
        println!("Pixels on  = {}", p2.len());
    }

    #[test]
    fn test_enhance_50(){
        let (a, p) = read_file("advent_day20.txt");
        let mut p2 = p;
        for i in 0..50 {
            p2 = enhance(&p2, &a, i % 2 == 1);
        }
        println!("Pixels on  = {}", p2.len());
    }
}
