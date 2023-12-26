use std::fs::read_to_string;

fn read_lines() -> Vec<Game> {
    read_to_string("src/input.txt")
        .unwrap() // panic on possible file-reading errors
        .lines() // split the string into an iterator of string slices
        .map(String::from)
        .map(Game::parse)
        .collect()
}

struct Game {
    id: i32,
    grabs: Vec<[Option<i32>; 3]>,
}

impl Game {
    fn parse(s: String) -> Game {
        let parse_grabs = |grab_s: &str| {
            let (mut red, mut blue, mut green) = (None, None, None);
            grab_s.split(", ").for_each(|cube: &str| {
                let (count, color) = cube.split_once(" ").unwrap();
                let count = count.parse::<i32>().unwrap();
                match color {
                    "red" => red = Some(count),
                    "green" => green = Some(count),
                    "blue" => blue = Some(count),
                    other => panic!("Unknown color: {}", other),
                }
            });

            [red, green, blue]
        };

        let (id, grabs) = s[5..].split_once(": ").unwrap();

        let id = id.parse::<i32>().unwrap();
        let grabs = grabs.split("; ").map(parse_grabs).collect();

        Game { id, grabs }
    }
}

fn a() -> i32 {
    read_lines()
        .iter()
        .filter(|g| {
            g.grabs.iter().all(|cubes| {
                let [red, green, blue] = cubes;
                red.map(|x| x <= 12).unwrap_or(true)
                    && green.map(|x| x <= 13).unwrap_or(true)
                    && blue.map(|x| x <= 14).unwrap_or(true)
            })
        })
        .map(|cube| cube.id)
        .reduce(|acc, ele| acc + ele)
        .unwrap()
}

fn max(a: Option<i32>, b: Option<i32>) -> Option<i32> {
    match (a, b) {
        (Some(va), Some(vb)) => Some(va.max(vb)),
        (None, _) => b,
        (_, None) => a,
    }
}

fn b() -> i32 {
    read_lines()
        .iter()
        .map(|game: &Game| {
            let (mut red, mut green, mut blue) = (None, None, None);
            game.grabs.iter().for_each(|grab| {
                red = max(red, grab[0]);
                green = max(green, grab[1]);
                blue = max(blue, grab[2]);
            });

            red.unwrap_or(1) * green.unwrap_or(1) * blue.unwrap_or(1)
        })
        .reduce(|acc, ele| acc + ele)
        .unwrap()
}

fn main() {
    println!("A: {}", a());
    println!("B: {}", b());
}
