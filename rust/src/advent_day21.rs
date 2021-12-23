use std::slice::Iter;



pub fn play_game(st1: u8, st2: u8) -> u32 {

    let mut die: u32 = 1;
    let mut rolls: u32 = 0;
    let mut pos1: u32 = st1 as u32;
    let mut pos2: u32 = st2 as u32;
    let mut score1 = 0;
    let mut score2 = 0;

    loop {
        // player 1
        pos1+= 3 * die + 3;
        pos1 = (pos1 - 1) % 10 + 1;
        die += 3;
        rolls += 3;
        score1 += pos1;
        if score1 >= 1000 {
            return rolls * score2;
        } 
        // player 2
        pos2+= 3 * die + 3;
        pos2 = (pos2 - 1) % 10 + 1;
        die += 3;
        rolls += 3;
        score2 += pos2;
        if score2 >= 1000 {
            return rolls * score1;
        } 
    }
}

pub fn try_universes(st1: u8, st2: u8){

    let univ: Vec<u8> = vec![1;3*21];
    let w = play_game_iter(st1, st2, &mut univ.iter());
    println!("Winner player {}", w);
}


pub fn play_game_iter(st1: u8, st2: u8, die_iter: &mut Iter<u8>) -> u8 {

    let mut pos1: u8 = st1;
    let mut pos2: u8 = st2;
    let mut score1 = 0;
    let mut score2 = 0;
    //let mut die_iter = die.iter();
    loop {
        // player 1
        pos1+= die_iter.next().unwrap() +
                die_iter.next().unwrap() +
                die_iter.next().unwrap();
        pos1 = (pos1 - 1) % 10 + 1;
        score1 += pos1;
        if score1 >= 21 {
            println!("WInner 1: {}", score1);
            return 1;
        } 
        // player 2
        pos2+= die_iter.next().unwrap() +
                die_iter.next().unwrap() +
                die_iter.next().unwrap();
        pos2 = (pos2 - 1) % 10 + 1;
        score2 += pos2;
        if score2 >= 21 {
            println!("WInner 2: {}", score2);
            return 1;
        } 
    }
}


#[cfg(test)]
mod test {

    use super::*;

    #[test]
    fn test_play_game_test(){
        assert_eq!(play_game(4, 8), 739785);

    }

    #[test]
    fn test_play_game(){
        println!("Score looser * rolls = {}", play_game(3, 4));
    }

    #[test]
    fn test_try_universes(){
        try_universes(4, 8);
    }
}