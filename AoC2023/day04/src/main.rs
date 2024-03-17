use std::{collections::HashSet, fs::read_to_string};


fn main() {
    let input = read_to_string("src/input.txt").unwrap();

    let mut p1 = 0;
    let mut copies = vec![1; input.split('\n').count()];
    for (i, l) in input.trim().split('\n').enumerate() {
        let rest = l.split_once(": ").unwrap().1;
        let (wanted, got) = rest.split_once(" | ").unwrap();
        let wanted = wanted
            .split_whitespace()
            .map(|w| w.parse::<usize>().unwrap())
            .collect::<Vec<_>>();
        let won = got
            .split_whitespace()
            .map(|w| w.parse::<usize>().unwrap())
            .filter(|c| wanted.contains(c))
            .count();
        p1 += if won != 0 { 1 << (won - 1) } else { 0 };
        for j in 0..won {
            copies[i + j + 1] += copies[i];
        }
    }
    println!("A: {}", p1);
    println!("B: {}", copies.iter().sum::<i32>());

}
