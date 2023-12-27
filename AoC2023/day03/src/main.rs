use std::{collections::HashSet, fmt::Display, fs::read_to_string};

fn read_lines() -> Vec<String> {
    read_to_string("src/input.txt")
        .unwrap() // panic on possible file-reading errors
        .lines() // split the string into an iterator of string slices
        .map(String::from)
        .collect()
}

fn find_digit_from_index(line: &String, index: usize) -> Option<(usize, usize, u32)> {
    for (start, c) in line[index..].chars().enumerate() {
        let start = start + index;
        if c.is_digit(10) {
            for (end, c) in line[start..].chars().enumerate() {
                let end = start + end;
                if !c.is_digit(10) {
                    return Some((start, end, line[start..end].parse::<u32>().unwrap()));
                }

                let end = end + 1;
                if c.is_digit(10) && line.len() == end {
                    return Some((start, end, line[start..end].parse::<u32>().unwrap()));
                }
            }
        }
    }

    None
}

struct Number {
    line_index: usize,
    start_index: usize,
    end_index: usize,
    val: u32,
}

impl Display for Number {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        write!(
            f,
            "line_index: {}, start_index: {}, end_index: {}, val: {}",
            self.line_index, self.start_index, self.end_index, self.val
        )
    }
}

fn find_numbers(matrix: &Vec<String>) -> Vec<Number> {
    let mut result = Vec::new();

    for (line_index, line) in matrix.iter().enumerate() {
        let mut index = 0;

        while let Some((start_index, end_index, val)) = find_digit_from_index(line, index) {
            result.push(Number {
                line_index,
                start_index,
                end_index,
                val,
            });

            index = end_index;
        }
    }

    result
}

fn is_engine_part(matrix: &Vec<String>, number: &Number) -> bool {
    let lower_line_index = number.line_index.checked_sub(1).unwrap_or(0);
    let upper_line_index = (number.line_index + 1).min(matrix.len() - 1);

    for line_index in lower_line_index..=upper_line_index {
        let line = matrix.get(line_index).unwrap();

        let lower_char_index = number.start_index.checked_sub(1).unwrap_or(0);
        let upper_char_index = (number.end_index + 1).min(line.len());

        for char_index in lower_char_index..upper_char_index {
            let char = line[char_index..(char_index + 1)].chars().next().unwrap();
            if char.is_digit(10) || char == '.' {
                continue;
            }
            return true;
        }
    }

    false
}

fn b(matrix: &[String]) -> u32 {
    let mut result = Vec::new();

    for (line_index, line) in matrix.iter().enumerate() {
        //println!("line_index {}", line_index);
        let mut index = 0;

        while let Some(asterisk_index) = line[index..].find("*") {
            let asterisk_index = asterisk_index + index;
            //println!("asterisk_index {}", asterisk_index);
            index = asterisk_index + 1;
            let numbers: Vec<u32> = find_surrounding_numbers(matrix, line_index, asterisk_index);

            if numbers.len() != 2 {
                continue;
            }

            result.push(numbers[0] * numbers[1]);
        }
    }

    result.into_iter().reduce(|a, b| a + b).unwrap()
}

fn find_surrounding_numbers(
    matrix: &[String],
    line_index: usize,
    asterisk_index: usize,
) -> Vec<u32> {
    //println!("line_index: {}, asterisk_index: {}", line_index, asterisk_index);
    let mut numbers: Vec<u32> = Vec::new();
    let lower_line_index = line_index.checked_sub(1).unwrap_or(0);
    let upper_line_index = (line_index + 2).min(matrix.len());

    for line_index in lower_line_index..upper_line_index {
        //println!("line_index {}", line_index);
        let mut tuple_set: HashSet<u32> = HashSet::new();
        let line = matrix.get(line_index).unwrap();
        let lower_char_index = asterisk_index.checked_sub(1).unwrap_or(0);
        let upper_char_index = (asterisk_index + 2).min(line.len());

        for char_index in lower_char_index..upper_char_index {
            let c = line.chars().nth(char_index).unwrap();
            //println!("char_index {}, char {}", char_index, c);

            if c.is_digit(10) {
                tuple_set.insert(find_number(matrix, line_index, char_index));
            }
        }

        numbers.extend(tuple_set);
    }
    //println!();
    numbers
}

fn find_number(matrix: &[String], line_index: usize, char_index: usize) -> u32 {
    let line = matrix.get(line_index).unwrap();
    let first_pos = line[..char_index]
        .rfind(|c: char| !c.is_digit(10))
        .map(|i| i + 1)
        .unwrap_or(0);
    let last_pos = line[first_pos..]
        .find(|c: char| !c.is_digit(10))
        .unwrap_or(line[first_pos..].len())
        + first_pos;

    // println!(
    //     "line_index {}, char_index {}: {}..{} => {}",
    //     line_index,
    //     char_index,
    //     firstPos,
    //     lastPos,
    //     &line[firstPos..lastPos]
    // );

    line[first_pos..last_pos].parse::<u32>().unwrap()
}

fn main() {
    let matrix = read_lines();

    let a = find_numbers(&matrix)
        .iter()
        .filter(|n| is_engine_part(&matrix, n))
        .map(|n| n.val)
        .reduce(|acc, ele| acc + ele);

    println!("A: {}", a.unwrap());

    let b = b(&matrix);

    println!("B: {}", b);
}
