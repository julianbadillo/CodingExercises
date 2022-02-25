use std::collections::{HashMap, LinkedList, HashSet};

use itertools::Itertools;


pub struct Pos {
    pos: u8,
    adj: Vec<u8>
}


// #############
// #...........#
// ###A#B#C#D###
//   #A#B#C#D#
//   #########

// #############
// #01.4.7.0.34#
// ###2#5#8#1###
//   #3#6#9#2#
//   #########

/// Adj matrix
/// 0 -> 1
/// 1 -> 2, 4
/// 2 -> 3, 4
/// 3 -> 2
/// 4 -> 1, 2, 5, 7
/// 5 -> 4, 6, 7
/// 6 -> 5,
/// 7 -> 4, 5, 8, 10
/// 8 -> 7, 9, 10
/// 9 -> 8
/// 10 -> 7, 8, 10, 13
/// 12 -> 11, 13, 14
/// 12 -> 11
/// 13 -> 10, 11, 14
/// 14 -> 13




/// Target positions
pub fn cave() -> Vec<Pos> {
    // build the cave
    vec![Pos{pos: 0, adj: vec![1]},
        Pos{pos: 1, adj: vec![2, 4]},
        Pos{pos: 2, adj: vec![3, 4]},
        Pos{pos: 3, adj: vec![2]},
        Pos{pos: 4, adj: vec![1, 2, 5, 7]},
        Pos{pos: 5, adj: vec![4, 6, 7]},
        Pos{pos: 6, adj: vec![5,]},
        Pos{pos: 7, adj: vec![4, 5, 8, 10]},
        Pos{pos: 8, adj: vec![7, 9, 10]},
        Pos{pos: 9, adj: vec![8]},
        Pos{pos: 10, adj: vec![7, 8, 10, 13]},
        Pos{pos: 12, adj: vec![11, 13, 14]},
        Pos{pos: 12, adj: vec![11]},
        Pos{pos: 13, adj: vec![10, 11, 14]},
        Pos{pos: 14, adj: vec![13]},
        ]
}

pub fn explore(pods: Vec<u8>) {
    // copy
    let start = pods.to_vec();
    let mut energy: HashMap<Vec<u8>, i32> = HashMap::new();
    let mut queue: LinkedList<Vec<u8>> = LinkedList::new();
    let mut finals: HashSet<Vec<u8>> = HashSet::new();
    let cave = cave();
    queue.push_back(start.clone());
    energy.insert(start, 0);

    // BFS
    while !queue.is_empty() {
        let pods = queue.pop_front().unwrap();
        let e = energy.get(&pods).unwrap();

        // each pod
        for (id, pos) in pods.clone().iter().enumerate() {

            // possible moves
            for dest in cave[*pos as usize].adj.iter() {
                // if it's not already occupied.
                if pods.contains(dest) {
                    continue;
                }
                // TODO else - calculate energy
                let new_energy = e + 10; // Or 20 depending if corner
                // new positions
                let mut new_pods = pods.clone();
                new_pods[id] = *dest;
                // energy
                if !energy.contains_key(&new_pods) {
                    energy.insert(new_pods.clone(), new_energy);
                    queue.push_back(new_pods);
                }   
            }

        }



    }

}


pub fn is_final(pods: Vec<u8>)  -> bool {

    // A A B B C C D D
    // 0 1 2 3 4 5 6 7

    // A: 2, 3
    if pods[0] != 2 && pods[0] != 3 {
        return false;
    }
    if pods[1] != 2 && pods[1] != 3 {
        return false;
    } 

    // B: 5, 6
    if pods[2] != 5 && pods[2] != 6 {
        return false;
    }
    if pods[3] != 5 && pods[3] != 6 {
        return false;
    } 
    // C: 8, 9
    if pods[4] != 8 && pods[4] != 9 {
        return false;
    }
    if pods[5] != 8 && pods[5] != 9 {
        return false;
    } 

    // D: 11, 12
    if pods[6] != 11 && pods[6] != 11 {
        return false;
    }
    if pods[7] != 12 && pods[7] != 12 {
        return false;
    } 
    return true;
}

#[cfg(test)]
mod test{
    use super::*;

    #[test]
    fn test_cave(){
        let cave = cave();
        assert_eq!(cave.len(), 15);
        assert_eq!(cave[1].adj.len(), 2);
        assert_eq!(cave[4].adj.len(), 4);
    }

    
    #[test]
    fn test_is_final(){
        let pods = vec![2, 3, 5, 6, 8, 9, 11, 12]; 
        assert!(is_final(pods));

    }

}