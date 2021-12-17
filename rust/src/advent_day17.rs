
pub fn simulate(vx: i32, vy: i32, x_min: i32, x_max: i32, y_min: i32, y_max: i32) -> Option<(u32, i32)> {
    let mut x = 0;
    let mut y = 0;
    let mut maxy = 0;
    let mut vx = vx;
    let mut vy = vy;
    let mut t = 0;
    loop {
        // position
        x += vx;
        y += vy;
        if y > maxy {
            maxy = y;
        }
        //println!("({}, {})", x, y);
        // drag
        if vx != 0 {
            vx += if vx > 0 { -1 } else { 1 };
        }
        // gravity
        vy -= 1;
        // time
        t += 1;

        //if within limits
        if x_min <= x && x <= x_max
            && y_min <= y && y <= y_max {
            return Some((t, maxy));
        }
        // if already out of bounds
        if x_max < x || y < y_min {
            return None;
        }

    }
}

pub fn explore(x_min: i32, x_max: i32, y_min: i32, y_max: i32) -> u32 {
    let mut maxy = 0;
    let mut maxvy = 0;
    let mut maxvx = 0;
    let mut count= 0;
    for vx in 1..x_max + 1 {
        for vy in -i32::abs(y_min) - 1.. i32::abs(y_min) + 1 {
            match simulate(vx, vy, x_min, x_max, y_min, y_max) {
                Some((_t, maxy2)) => {
                        count += 1;
                        //println!("v = ({}, {}), maxy={}",vx,vy,maxy);
                    if maxy2 > maxy {
                        maxy = maxy2;
                        maxvx = vx;
                        maxvy = vy;
                        
                    }
                },
                None => continue,
            }
        }
    }
    println!("Max");
    println!("v = ({}, {}), maxy={}",maxvy, maxvx, maxy);
    println!("count = {}", count);
    return count;
}


#[cfg(test)]
mod tests {
    
    use super::*;

    #[test]
    fn test_simulate1() {
        let r = simulate(7, 2, 20, 30, -10, -5);
        assert_eq!(r, Some((7, 3)));
        let r= simulate(6, 3, 20, 30, -10, -5);
        assert_eq!(r, Some((9,6)));
        let r = simulate(9, 0, 20, 30, -10, -5);
        assert_eq!(r, Some((4,0)));
        let r = simulate(17, -4, 20, 30, -10, -5);
        assert_eq!(r, None);
        let r = simulate(23, -10, 20, 30, -10, -5);
        assert!(r.is_some());
        let r = simulate(8, -2, 20, 30, -10, -5);
        assert!(r.is_some());
    }

    #[test]
    fn test_explore_test() {
        let r = explore(20, 30, -10, -5);
        assert_eq!(r, 112);
    }

    #[test]
    fn test_explore() {
        //x=79..137, y=-176..-117
        explore(79, 137, -176, -117);
    }

}