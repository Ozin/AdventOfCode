
use std::fs::read_to_string;

fn read_lines(filename: &str) -> Vec<String> {
    read_to_string(filename)
        .unwrap() // panic on possible file-reading errors
        .lines() // split the string into an iterator of string slices
        .map(String::from) // make each slice into a string
        .collect::<Vec<String>>() // gather them together into a vector
}

fn sum_numbers(lines: &Vec<String>) -> Option<u32> {
    lines
        .iter()
        .map(|line| {
            format!(
                "{}{}",
                line.chars().find(|c| c.is_digit(10)).unwrap(),
                line.chars().rev().find(|c| c.is_digit(10)).unwrap(),
            )
        })
        .map(|n| n.parse::<u32>().unwrap())
        .reduce(|acc, x| acc + x)
}

const WORD_MAP: &[(&str, i32); 10] = &[
    ("zero", 0),
    ("one", 1),
    ("two", 2),
    ("three", 3),
    ("four", 4),
    ("five", 5),
    ("six", 6),
    ("seven", 7),
    ("eight", 8),
    ("nine", 9),
];

fn replace_num_word_at_end(input_string: &String) -> String {
    let mut last_occurrence: Option<(usize, &str, i32)> = None;

    for &(word, num) in WORD_MAP {
        if let Some(index) = input_string.rfind(word) {
            // Check if this occurrence is later than the current last occurrence
            if last_occurrence.map_or(true, |(last_index, _, _)| index > last_index) {
                last_occurrence = Some((index, word, num));
            }
        }
    }

    if let Some((index, word, num)) = last_occurrence {
        let replaced_string = format!(
            "{}{}{}",
            &input_string[..index],
            num,
            &input_string[index + word.len()..]
        );
        replaced_string
    } else {
        input_string.to_string()
    }
}

fn replace_num_word_at_front(input_string: &String) -> String {
    let mut first_occurrence: Option<(usize, &str, i32)> = None;

    for &(word, num) in WORD_MAP {
        if let Some(index) = input_string.find(word) {
            // Check if this occurrence is later than the current last occurrence
            if first_occurrence.map_or(true, |(last_index, _, _)| index < last_index) {
                first_occurrence = Some((index, word, num));
            }
        }
    }

    if let Some((index, word, num)) = first_occurrence {
        let replaced_string = format!(
            "{}{}{}",
            &input_string[..index],
            num,
            &input_string[index + word.len()..]
        );
        replaced_string
    } else {
        input_string.to_string()
    }
}

fn main() {
    let lines = read_lines("src/input.txt");
    let result = sum_numbers(&lines);
    println!("A: {}", result.unwrap());

    let result = sum_numbers(
        &lines
            .iter()
            //.inspect(|l| println!("{}", l))
            .map(|line| {
                format!(
                    "{}{}",
                    replace_num_word_at_front(line),
                    replace_num_word_at_end(line)
                )
            })
            //.inspect(|l| println!("{}", l))
            .collect(),
    );
    println!("B: {}", result.unwrap());
}
