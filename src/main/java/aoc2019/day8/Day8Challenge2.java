package aoc2019.day8;

import java.io.BufferedReader;import java.io.FileReader;import java.util.ArrayList;import java.util.Arrays;public class Day8Challenge2 {

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

        int[] output = new int[WIDTH * HEIGHT];
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < layers.size(); j++) {
                int[] layer = layers.get(j);
                if (layer[i] == 0) {
                    output[i] = 0;
                    break;
                } else if (layer[i] == 1) {
                    output[i] = 1;
                    break;
                } else if (layer[i] == 2) {
                    output[i] = 2;
                    continue;
                }
            }
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                int pixel = output[j + (i * WIDTH)];
                System.out.print(pixel == 1 ? "O" : " ");
            }

            System.out.println();
        }
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
