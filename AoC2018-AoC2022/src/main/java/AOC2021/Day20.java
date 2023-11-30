package AOC2021;


import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import utils.Point;

import java.util.HashMap;
import java.util.Map;

public class Day20 {
    protected String a(final String[] rawInput) throws Exception {
        return enhanceImage(rawInput, 2);
    }

    protected String b(final String[] rawInput) throws Exception {
        return enhanceImage(rawInput, 50);
    }

    private String enhanceImage(String[] rawInput, int numberOfTimes) {
        Input input = Input.parse(rawInput);
        var outputImage = input.inputImage;
        for (int i = 0; i < numberOfTimes; i++) {
            outputImage = enhanceInputImage(
                    outputImage,
                    input.imageEnhancementAlgorithm,
                    i % 2 == 0 ? '.':
                            input.imageEnhancementAlgorithm.get(0)
            );
        }
        return outputImage.pixel.values().stream().filter(Character.valueOf('#')::equals).count() + "";
    }

    Image enhanceInputImage(Image inputImage, Map<Integer, Character> imageEnhancementAlgorithm, char defaultValue) {
        var outputImage = new HashMap<Point, Character>((inputImage.sizeX() + 2) * (inputImage.sizeY() + 2));
        final int newMinX = inputImage.minX() - 1;
        final int newMaxX = inputImage.maxX + 1;
        final int newMinY = inputImage.minY() - 1;
        final int newMaxY = inputImage.maxY() + 1;

        for (int y = newMinY; y < newMaxY; y++) {
            for (int x = newMinX; x < newMaxX; x++) {
                final Point point = new Point(x, y);
                final String binary = EntryStream.of(
                                                         -1, -1,
                                                         0, -1,
                                                         1, -1,
                                                         -1, 0,
                                                         0, 0,
                                                         1, 0,
                                                         -1, +1,
                                                         0, +1,
                                                         1, 1
                                                 )
                                                 .mapKeyValue((dx, dy) -> point.addX(dx).addY(dy))
                                                 .map(p -> inputImage.pixel().getOrDefault(p, defaultValue))
                                                 .joining()
                                                 .replace('.', '0')
                                                 .replace('#', '1');
                final int position = Integer.parseInt(binary, 2);
                final Character value = imageEnhancementAlgorithm.get(position);
                if (value == null) {
                    throw new NullPointerException("Value should not be null: " + position);
                }
                outputImage.put(point, value);
            }
        }

        return new Image(newMinX, newMinY, newMaxX, newMaxY, outputImage);
    }

    record Input(Map<Integer, Character> imageEnhancementAlgorithm, Image inputImage) {
        public static Input parse(String[] rawInput) {
            Map<Point, Character> inputImage = new HashMap<>();
            for (int y = 0; y < rawInput.length - 2; y++) {
                for (int x = 0; x < rawInput[y + 2].length(); x++) {
                    inputImage.put(new Point(x, y), rawInput[y + 2].charAt(x));
                }
            }

            final Map<Integer, Character> algorithm = IntStreamEx.range(rawInput[0].length())
                                                                 .boxed()
                                                                 .zipWith(rawInput[0].chars()
                                                                                     .mapToObj(i -> (char) i))
                                                                 .toMap();
            return new Input(algorithm, new Image(0, 0, rawInput[3].length(), rawInput.length - 2, inputImage));
        }
    }

    record Image(int minX, int minY, int maxX, int maxY, Map<Point, Character> pixel) {
        String printImage() {
            StringBuilder sb = new StringBuilder((maxX - minX + 1) * (maxY - minY));
            for (int y = minY; y < maxY; y++) {
                for (int x = minX; x < maxX; x++) {
                    sb.append(pixel.get(new Point(x, y)).toString());
                }
                sb.append('\n');
            }
            return sb.toString();
        }

        int sizeX() {
            return Math.abs(maxX - minX);
        }

        int sizeY() {
            return Math.abs(maxY - minY);
        }
    }
}
