package AOC2021;


import utils.Point3D;
import utils.Tuple2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day22 {
    static Pattern inputLinePattern = Pattern.compile(
            "(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");

    protected String a(final String[] rawInput) throws Exception {
        final var instructions = Arrays.stream(rawInput)
                                       .map(Instruction::parseInputLine)
                                       .toList();

        var smallInstructions = instructions.subList(0, Math.min(instructions.size(), 20));

        HashSet<Point3D> cuboids = new HashSet<>();
        for (Instruction i : smallInstructions) {
            if (i.isOn()) {
                cuboids.addAll(i.stream().collect(Collectors.toSet()));
            } else {
                cuboids.removeAll(i.stream().collect(Collectors.toSet()));
            }
        }

        return cuboids.size() + "";
    }

    protected String b(final String[] rawInput) throws Exception {
        final var instructions = Arrays.stream(rawInput)
                                       .map(Instruction::parseInputLine)
                                       .toList();

        instructions.stream()
                    .map(Instruction::size)
                    .forEach(System.out::println);
        System.out.println(
        );
        System.out.println(Integer.MAX_VALUE);

        var smallInstructions = instructions.subList(0, Math.min(instructions.size(), 20));
        var bigInstructions = instructions.subList(Math.min(instructions.size(), 20), instructions.size());
        return null;
    }

    record Instruction(boolean isOn, Cuboid cuboid) {
        static Instruction parseInputLine(String line) {
            final Matcher matcher = inputLinePattern.matcher(line);
            matcher.find();

            final boolean isOn = matcher.group(1).equals("on");
            var x1 = Integer.parseInt(matcher.group(2));
            var x2 = Integer.parseInt(matcher.group(3));
            var y1 = Integer.parseInt(matcher.group(4));
            var y2 = Integer.parseInt(matcher.group(5));
            var z1 = Integer.parseInt(matcher.group(6));
            var z2 = Integer.parseInt(matcher.group(7));
            return new Instruction(isOn, new Point3D(x1, y1, z1), new Point3D(x2, y2, z2));
        }

        Stream<Point3D> stream() {
            return start.streamCuboid(end);
        }

        long size() {
            return (long) Math.abs(end().getX() - start().getX()) *
                   Math.abs(end().getY() - start().getY()) *
                   Math.abs(end().getZ() - start().getZ())
                    ;
        }
    }

    /*
    function gcd(a, b)
    while b ≠ 0
        t := b
        b := a mod b
        a := t
    return a
     */
    public int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public Optional<Integer> gcd(List<Instruction> l, Function<Point3D, Integer> function) {
        return l.stream()
                .flatMap(i -> Stream.of(i.start, i.end))
                .map(function)
                .reduce(this::gcd);
    }

    record Cuboid(Point3D start, Point3D end) {
        Cuboid {
            start = start.withX(Math.min(start.getX(), end.getX()))
                         .withY(Math.min(start.getY(), end.getY()))
                         .withZ(Math.min(start.getZ(), end.getZ()));
            end = end.withX(Math.max(start.getX(), end.getX()))
                     .withY(Math.max(start.getY(), end.getY()))
                     .withZ(Math.max(start.getZ(), end.getZ()));
        }

        Optional<Cuboid> overlap(Cuboid other) {
            Optional<Tuple2<Integer, Integer>> xs = overlap(Point3D::getX, other);
            if(xs.isEmpty()) return Optional.empty();

            Optional<Tuple2<Integer, Integer>> ys = overlap(Point3D::getY, other);
            if(ys.isEmpty()) return Optional.empty();

            Optional<Tuple2<Integer, Integer>> zs = overlap(Point3D::getZ, other);
            if(zs.isEmpty()) return Optional.empty();
            
        }


        Optional<Tuple2<Integer, Integer>> overlap(Function<Point3D, Integer> f, Cuboid other) {
            var aStart = f.apply(start);
            var aEnd = f.apply(end);
            var bStart = f.apply(other.start);
            var bEnd = f.apply(other.end);

            if (aStart > bStart) {
                bStart = aStart;
                aEnd = bEnd;
            }

            if (bStart < aEnd) {
                return Optional.of(new Tuple2<>(bStart, aEnd));
            }


            return Optional.empty()
        }
    }
}
