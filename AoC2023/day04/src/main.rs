use std::{
    collections::HashSet,
    fs::read_to_string,
};

fn read_lines() -> Vec<String> {
    read_to_string("src/input.txt")
        .unwrap() // panic on possible file-reading errors
        .lines() // split the string into an iterator of string slices
        .map(String::from)
        .collect()
}

fn main() {
    let a = read_lines()
        .iter()
        .map(|l| l[8..].split_once(" | ").unwrap())
        .map(|(winning, actual)| {
            let winning: HashSet<u32> = winning
                .split_whitespace()
                .filter_map(|n| n.parse().ok())
                .collect();
            let actual: HashSet<u32> = actual
                .split_whitespace()
                .filter_map(|n| n.parse().ok())
                .collect();

            winning.intersection(&actual).count()
        })
        .filter(|v| *v != 0)
        .map(|count| 1 << count - 1)
        //.inspect(|v| println!("{}", v))
        .sum::<u32>();

    println!("A: {}", a);
    // println!("B: {}", b);
}
