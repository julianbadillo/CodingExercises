use std::{slice::Iter, collections::{HashMap, HashSet}, ops::Range, fs, f32::consts::E};
use regex::Regex;

pub struct Step {
    on: bool,
    rx: Range<i32>,
    ry: Range<i32>,
    rz: Range<i32>

}

pub fn read_file(file_name: &str) -> Vec<Step> {
    let data: String = fs::read_to_string(file_name)
                          .expect("Unable to read file");
    let input: Vec<Step> = data.split("\r\n")
                                .map(|l| parse_range(l))
                                .collect();
    return input;
}


pub fn parse_range(line: &str) -> Step {
    //on x=10..10,y=10..10,z=10..10
    let r = Regex::new(r"(on|off) x=(-?\d+)..(-?\d+),y=(-?\d+)..(-?\d+),z=(-?\d+)..(-?\d+)")
                        .expect(format!("Failed on {}", line).as_str());
    let m = r.captures(line)
                        .expect(format!("Failed on {}", line).as_str());
    Step {
        on: m.get(1).unwrap().as_str() == "on",
        rx: Range{start: m.get(2).unwrap().as_str().parse().unwrap(),
                    end: m.get(3).unwrap().as_str().parse::<i32>().unwrap() + 1},
        ry: Range{start: m.get(4).unwrap().as_str().parse().unwrap(),
                        end: m.get(5).unwrap().as_str().parse::<i32>().unwrap() + 1},
        rz: Range{start: m.get(6).unwrap().as_str().parse().unwrap(),
                            end: m.get(7).unwrap().as_str().parse::<i32>().unwrap() + 1},
    } 
}

pub fn mark_on_off(st: Step, cubes: &mut HashSet<(i32, i32, i32)>) {
    
    // skip
    if st.rx.start < -50 || st.rx.end > 51 ||
    st.ry.start < -50 || st.ry.end > 51 ||
    st.rz.start < -50 || st.rz.end > 51 {
        return;
    }

    for x in st.rx.clone() {
        for y in st.ry.clone() {
            for z in st.rz.clone(){
                if st.on {
                    cubes.insert((x, y, z));
                } else {
                    cubes.remove(&(x,y,z));
                }
            }
        }
    }

}

pub fn reboot_count(sts: Vec<Step>) -> u32 {
    let mut cubes: HashSet<(i32, i32, i32)> =  HashSet::new();
    for st in sts.into_iter() {
        mark_on_off(st, &mut cubes);
    }
    return cubes.len() as u32;
}

#[cfg(test)]
mod test {

    use super::*;

    #[test]
    fn test_parse_range(){
        let st = parse_range("on x=11..13,y=-14..-11,z=15..16");
        assert_eq!(st.on, true);
        assert_eq!(st.rx.start, 11);
        assert_eq!(st.rx.end, 14);
        assert_eq!(st.ry.start, -14);
        assert_eq!(st.ry.end, -10);
        assert_eq!(st.rz.start, 15);
        assert_eq!(st.rz.end, 17);
        let st = parse_range("off x=1..3,y=-14..-11,z=25..26");
        assert_eq!(st.on, false);
        assert_eq!(st.rx.start, 1);
        assert_eq!(st.rx.end, 4);
        assert_eq!(st.ry.start, -14);
        assert_eq!(st.ry.end, -10);
        assert_eq!(st.rz.start, 25);
        assert_eq!(st.rz.end, 27);
        
    }

    #[test]
    fn test_read_file(){
        let sts = read_file("advent_day22_test.txt");
        assert_eq!(sts.len(), 4);
    }

    #[test]
    fn test_reboot_count_test(){
        let sts = read_file("advent_day22_test.txt");
        assert_eq!(reboot_count(sts), 39);
    }

    #[test]
    fn test_reboot_count_test2(){
        let sts = read_file("advent_day22_test2.txt");
        assert_eq!(reboot_count(sts), 590784);
    }

    #[test]
    fn test_reboot_count(){
        let sts = read_file("advent_day22.txt");
        println!("Cubes count = {}", reboot_count(sts));
    }
}