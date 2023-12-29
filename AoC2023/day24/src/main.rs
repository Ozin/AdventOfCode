use std::fs::read_to_string;

fn read_lines() -> Vec<String> {
    read_to_string("src/input.txt")
        .unwrap() // panic on possible file-reading errors
        .lines() // split the string into an iterator of string slices
        .map(String::from)
        .collect()
}

fn do_vectors_intersect(v1: Vector, d1: Vector, v2: Vector, d2: Vector) -> bool {
    // Check if the vectors are parallel
    let det = d1.x * d2.y - d1.y * d2.x;
    if det.abs() < 1e-9 {
        return false; // Vectors are parallel
    }

    // Solve the system of equations for t and s
    let t = ((v2.x - v1.x) * d2.y - (v2.y - v1.y) * d2.x) / det;
    let s = ((v2.x - v1.x) * d1.y - (v2.y - v1.y) * d1.x) / det;

    // Check if the intersection point is within the valid range [0, 1]
    (0.0..=1.0).contains(&t) && (0.0..=1.0).contains(&s)
}

struct Vector {
    x: f64,
    y: f64,
    z: f64,
}

struct Hailstone {
    pos: Vector,
    dir: Vector,
}

fn main() {
    read_lines().iter().map(|l| {})
    println!("Hello, world!");
}
