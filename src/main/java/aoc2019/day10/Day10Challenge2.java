package aoc2019.day10;

import java.io.BufferedReader;import java.io.FileReader;import java.util.Arrays;import java.util.PriorityQueue;public class Day10Challenge2 {

    private static final String FILE = "src/main/resources/2019_d10c1";
    private static final int WIDTH = 36;
    private static final int HEIGHT = 36;
    private static final int STATION_X_POS = 26;
    private static final int STATION_Y_POS = 29;
    private static final int ASTEROIDS_TO_DESTROY = 200;


    public static void main(String[] args) {
        char[][] map = loadMap();
        for (int i = 0; i < map.length; i++) {
            System.out.println(Arrays.toString(map[i]));
        }

        int destroyedAsteroids = 0;
        while (destroyedAsteroids < ASTEROIDS_TO_DESTROY) {
            PriorityQueue<Slope> slopes = new PriorityQueue<Slope>();

            for (int rise = (-HEIGHT + 1); rise < HEIGHT; rise++) {
                for (int run = (-WIDTH + 1); run < WIDTH; run++) {
                    if (canReduce(rise, run)) continue;
                    slopes.add(new Slope(rise, run));
                    // System.out.println("rise/run=" + rise + "/" + run + ", cos=" + Math.cos(rise / Math.sqrt(Math.pow(run, 2) + Math.pow(rise, 2))));
                }
            }

            while (!slopes.isEmpty() && destroyedAsteroids < ASTEROIDS_TO_DESTROY) {
                Slope slope = slopes.poll();
                int rise = slope.rise;
                int run = slope.run;

                int itr = 1;
                while (STATION_Y_POS + (rise * itr) < HEIGHT && STATION_X_POS + (run * itr) < WIDTH && STATION_Y_POS + (rise * itr) >= 0 && STATION_X_POS + (run * itr) >= 0) {
                    // search through slope
                    if (map[STATION_Y_POS + (rise*itr)][STATION_X_POS + (run*itr)] == '#') { // Asteroid Detected
                        destroyedAsteroids++;
                        map[STATION_Y_POS + (rise*itr)][STATION_X_POS + (run*itr)] = '.';
                        System.out.println("Asteroid #" + (destroyedAsteroids) + " destroyed at: (" + (STATION_X_POS + (run*itr)) + ", " + (STATION_Y_POS + (rise*itr)) + ")");
                        break;
                    }
                    itr++;
                }
            }
        }

        System.out.println();
        for (int i = 0; i < map.length; i++) {
            System.out.println(Arrays.toString(map[i]));
        }
    }

    private static boolean canReduce(int num, int denom) {
        int gcd = gcd(num, denom);
        return Math.abs(gcd) != 1;
    }

    private static int gcd(int num, int denom) {
        return denom == 0 ? num : gcd(denom, num % denom);
    }

    private static char[][] loadMap() {
        char[][] map = new char[HEIGHT][WIDTH];
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

    private static class Slope implements Comparable {
        int rise;
        int run;
        int quadrant;

        public Slope(int rise, int run) {
            this.rise = rise;
            this.run = run;
            if (rise <= -1 && run >= 0) {
                this.quadrant = 1;
            } else if (rise >= 0 && run >= 1) {
                this.quadrant = 2;
            } else if (rise >= 1 && run <= 0) {
                this.quadrant = 3;
            } else {
                quadrant = 4;
            }
        }

        public int compareTo(Object o) {
            Slope other = (Slope) o;
            double thisCos = this.cos();
            double otherCos = other.cos();

            if (this.quadrant < other.quadrant) {
                return -1;
            } else if (this.quadrant > other.quadrant) {
                return 1;
            } else {
                if (this.quadrant == 1 || this.quadrant == 3) {
                    return thisCos < otherCos ? -1 : (thisCos > otherCos ? 1 : 0);
                } else if (this.quadrant == 2 || this.quadrant == 4) {
                    return thisCos > otherCos ? -1 : (thisCos < otherCos ? 1 : 0);
                }
            }

            return 0;
        }

        private double cos() {
            return Math.cos(Math.abs(rise) / Math.sqrt(Math.pow(run, 2) + Math.pow(rise, 2)));
        }
    }
}
