use std::{fs, vec, collections::HashSet, hash::Hash, ops::Index};

pub fn read_file(file_name: &str) -> (HashSet<(u32, u32)>, Vec<String>) {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");

    let mut coords: HashSet<(u32, u32)> = HashSet::new();
    let mut inst: Vec<String> = vec![];
    for line in  data.split("\r\n") {
        if line.contains(",") {
            let p:Vec<&str> = line.splitn(2, ",").collect();
            coords.insert((p[0].parse::<u32>().unwrap(), 
                            p[1].parse::<u32>().unwrap()));

        } else if !line.is_empty() {
            inst.push(String::from(line));
        }
    }    
    return (coords, inst);
}

pub fn fold(p: (u32, u32), x: Option<u32>, y: Option<u32>) -> (u32, u32) {
    if x.is_some() && p.0 > x.unwrap() {
        return (x.unwrap()*2 - p.0, p.1);
    }
    if y.is_some() && p.1 > y.unwrap() {
        return (p.0, y.unwrap()*2 - p.1);
    }
    p
}

pub fn fold_all(points: HashSet<(u32, u32)>, inst: Vec<String>) -> HashSet<(u32, u32)> {
    let mut points2 = points;
    for i in inst.iter() {
        let mut x = None;
        let mut y = None;
        if i.contains("fold along y="){
            y = Some(i[i.find("=").unwrap() + 1..].parse::<u32>().unwrap());
        }
        if i.contains("fold along x="){
            x = Some(i[i.find("=").unwrap() + 1..].parse::<u32>().unwrap());
        }
        points2 =  points2.iter().map(|p| fold(*p, x, y)).collect();
    }
    return points2
}

pub fn print_points(points: HashSet<(u32, u32)>){
    let max_x = points.iter().map(|p| p.0).max().unwrap();
    let max_y = points.iter().map(|p| p.1).max().unwrap();
    for y in 0..max_y+1{
        for x in 0..max_x+1{
            if points.contains(&(x, y)) {
                print!("#");
            } else {
                print!(" ");
            }
        }
        println!("");
    }
}


#[cfg(test)]
mod tests {
    
    use super::*;

    #[test]
    fn test_read_file(){
        let (coords, inst) = read_file("advent_day13_test.txt");
        assert_eq!(inst, vec!["fold along y=7", "fold along x=5"]);
        assert!(coords.contains(&(6,10)));
        assert!(coords.contains(&(0,14)));
        assert!(coords.contains(&(9,10)));
        assert!(coords.contains(&(0,3)));
        assert!(coords.contains(&(10,4)));
        assert!(coords.contains(&(4,11)));
        assert!(coords.contains(&(6,0)));
        assert!(coords.contains(&(6,12)));
        assert!(coords.contains(&(4,1)));
        assert!(coords.contains(&(0,13)));
        assert!(coords.contains(&(10,12)));
    }

    #[test]
    fn test_fold_y(){
        assert_eq!(fold((0, 14), None, Some(7)), (0, 0));
        assert_eq!(fold((0, 13), None, Some(7)), (0, 1));
        assert_eq!(fold((0, 2), None, Some(7)), (0, 2));
    }

    #[test]
    fn test_fold_x(){
        
        assert_eq!(fold((6, 0), Some(5), None), (4, 0));
        assert_eq!(fold((10, 4), Some(5), None), (0, 4));
        assert_eq!(fold((0, 4), Some(5), None), (0, 4));
    }

    #[test]
    fn test_fold_all_test(){
        let (coords, inst) = read_file("advent_day13_test.txt");
        let coords2 = fold_all(coords, inst);
        print_points(coords2);
    }

    #[test]
    fn test_fold_all(){
        let (coords, inst) = read_file("advent_day13.txt");
        let coords2 = fold_all(coords, inst);
        print_points(coords2);
    }
}