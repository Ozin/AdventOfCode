use std::{collections::HashSet, fs::read_to_string};

fn read_lines() -> Vec<String> {
    read_to_string("src/input.txt")
        .unwrap() // panic on possible file-reading errors
        .lines() // split the string into an iterator of string slices
        .map(String::from)
        .collect()
}

fn to_set(s: &str) -> HashSet<u32> {
    return s
        .split_whitespace()
        .filter_map(|n| n.parse().ok())
        .collect();
}

fn main() {
    let mut points = 0;
    for (index, (winning, actual)) in read_lines()
        .iter()
        .map(|l| l.split_once(":").unwrap().1)
        .map(|l| l.split_once(" | ").unwrap())
        .map(|(winning, actual)| (to_set(winning), to_set(actual)))
        .enumerate()
    {
        let n = winning.intersection(&actual).count();

        if n > 0 {
            points += 1 << n - 1
        }
    }

    println!("A: {}", points);
    // println!("B: {}", b);
}
