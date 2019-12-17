package aoc2019.day10;

import java.io.BufferedReader;import java.io.FileReader;import java.math.BigDecimal;import java.util.Arrays;public class Day10Challenge1 {

    private static final String FILE = "src/main/resources/2019_d10c1";
    private static final int WIDTH = 36;
    private static final int HEIGHT = 36;

    public static void main(String[] args) {
        char[][] map = loadMap();
        for (int i = 0; i < map.length; i++) {
            System.out.println(Arrays.toString(map[i]));
        }

        int[][] losCount = new int[WIDTH][HEIGHT];


        // For each asteroid, scan for other asteroids
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                for (int rise = -HEIGHT; rise < HEIGHT; rise++) {
                    for (int run = -WIDTH; run < WIDTH; run++) {
                        // Skip rise's and runs that can be reduced
                        if (canReduce(rise, run)) continue;
                        if (map[i][j] == '.') continue;

                        int itr = 1;
                        while (i + (rise * itr) < HEIGHT && j + (run * itr) < WIDTH && i + (rise * itr) >= 0 && j + (run * itr) >= 0) {
                            // search through slope
                            if (map[i + (rise*itr)][j + (run*itr)] == '#') {
                                losCount[i][j]++;
                                break;
                            }
                            itr++;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < map.length; i++) {
            System.out.println(Arrays.toString(losCount[i]));
        }

        int maxLosCount = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                maxLosCount = Math.max(maxLosCount, losCount[i][j]);
            }
        }

        System.out.println("Max LOS Count: " + maxLosCount);
    }

    private static boolean canReduce(int num, int denom) {
        int gcd = gcd(num, denom);
        return Math.abs(gcd) != 1;
    }

    private static int gcd(int num, int denom) {
        return denom == 0 ? num : gcd(denom, num % denom);
    }

    private static char[][] loadMap() {
        char[][] map = new char[WIDTH][HEIGHT];
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(FILE));
            for (int i = 0; i < HEIGHT; i++) {
                String line = reader.readLine();
                for (int j = 0; j < WIDTH; j++) {
                    map[i][j] = line.charAt(j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
