package aoc2019.day8;

import java.io.BufferedReader;import java.io.FileReader;import java.util.ArrayList;import java.util.Arrays;public class Day8Challenge1 {

    private static final String FILE = "src/main/resources/2019_d8c1";
    private static final int WIDTH = 25;
    private static final int HEIGHT = 6;

    public static void main(String[] args) {

        String image = getImage();

        ArrayList<int[]> layers = new ArrayList<int[]>();

        int index = 0;
        while (index < image.length()) {
            int[] layer = new int[WIDTH * HEIGHT];
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    layer[j + (i * WIDTH)] = Integer.parseInt(image.charAt(index) + "");
                    index++;
                }
            }

            layers.add(layer);
        }

        int minZerosLayerIndex = 0;
        int minZeros = Integer.MAX_VALUE;

        for (int i = 0; i < layers.size(); i++) {
            int[] layer = layers.get(i);
            int zeros = 0;
            for (int j = 0; j < layer.length; j++) {
                zeros += layer[j] == 0 ? 1 : 0;
            }

            if (zeros < minZeros) {
                minZeros = zeros;
                minZerosLayerIndex = i;
            }
        }

        int[] minZerosLayer = layers.get(minZerosLayerIndex);
        System.out.println(Arrays.toString(minZerosLayer));

        int ones = 0;
        int twos = 0;

        for (int i = 0; i < minZerosLayer.length; i++) {
            if (minZerosLayer[i] == 1) {
                ones++;
            } else if (minZerosLayer[i] == 2) {
                twos++;
            }
        }

        System.out.println("Ones=" + ones + ", Twos=" + twos);
        System.out.println("Code: " + (ones * twos));
    }

    private static String getImage() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(FILE));
            return reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
