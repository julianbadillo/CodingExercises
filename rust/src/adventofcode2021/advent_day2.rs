use std::fs;

#[derive(Debug)]
enum Direction {
    Forward,
    Down,
    Up,
}

impl From<&str> for Direction {
    fn from(item: &str) -> Self {
        match item {
            "forward" => Direction::Forward,
            "down" => Direction::Down,
            "up" => Direction::Up,
            _ => panic!("Wrong! value"),
        }
    }
}

#[derive(Debug)]
struct Instruction{
    dir: Direction,
    v: u32,
}

impl Instruction {
    fn from_string(input: &str) -> Instruction {
        let x: Vec<&str> = input.split(" ").collect();
        Instruction{dir: Direction::from(x[0]), v: x[1].parse().unwrap()}
    }
}

fn calculate_final_position(input: &Vec<Instruction>) -> (u32, u32){
    let mut y = 0;
    let mut x = 0;
    for inst in input.iter() {
        match inst {
            Instruction { dir: Direction::Down, v } => y += v,
            Instruction { dir: Direction::Up, v } => y -= v,
            Instruction { dir: Direction::Forward, v } => x += v,
        }
    }
    // return coordinates
    (x, y)
}

fn calculate_final_position_aim(input: &Vec<Instruction>) -> (u32, u32){
    let mut y = 0;
    let mut x = 0;
    let mut aim = 0;
    for inst in input.iter(){
        match inst {
            Instruction { dir: Direction::Down, v } => aim += v,
            Instruction { dir: Direction::Up, v } => aim -= v,
            Instruction { dir: Direction::Forward, v } => {
                x += v;
                y += aim * v;
            },
        }
    }
    // return coordinates
    (x, y)
}

fn main() {
    //let data = fs::read_to_string("advent_day2.txt").expect("Unable to read file");
    let data = fs::read_to_string("advent_day2.txt")
                        .expect("Unable to read file");
    
    println!("{:?}", Instruction::from_string("down 100"));
    let mut input = vec![];
    for line in data.split("\r\n") {
        let ins = Instruction::from_string(line);
        //println!(":{:?}", ins);
        input.push(ins);
    }
    let (x, y) = calculate_final_position(&input);
    println!("{}, {}, {}", x, y, x * y);
    let (x, y) = calculate_final_position_aim(&input);
    println!("{}, {}, {}", x, y, x * y);
}

