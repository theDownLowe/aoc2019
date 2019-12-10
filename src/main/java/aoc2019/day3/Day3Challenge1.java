package aoc2019.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class Day3Challenge1 {
    private static final String FILE = "src/main/resources/2019_d3c1";
    public static final int SIZE = 40000;

    public static void main(String[] args) {

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(FILE));
            String line1 = reader.readLine();
            String line2 = reader.readLine();

            String[] red = line1.split(",");
            String[] blue = line2.split(",");

            char[][] grid = new char[SIZE][SIZE];
            int currentX = SIZE / 2;
            int currentY = SIZE / 2;
            grid[currentX][currentY] = 'o';

            for (int i = 0; i < red.length; i++) {
                int distance = Integer.parseInt(red[i].substring(1));

                if (red[i].charAt(0) == 'L') {
                    for (int j = currentX - 1; j >= currentX - distance; j--) {
                        grid[j][currentY] = 'R';
                    }
                    currentX -= distance;
                } else if (red[i].charAt(0) == 'R') {
                    for (int j = currentX + 1; j <= currentX + distance; j++) {
                        grid[j][currentY] = 'R';
                    }
                    currentX += distance;
                } else if (red[i].charAt(0) == 'U') {
                    for (int j = currentY - 1; j >= currentY - distance; j--) {
                        grid[currentX][j] = 'R';
                    }
                    currentY -= distance;
                } else if (red[i].charAt(0) == 'D') {
                    for (int j = currentY + 1; j <= currentY + distance; j++) {
                        grid[currentX][j] = 'R';
                    }
                    currentY += distance;
                }
            }

            currentX = SIZE / 2;
            currentY = SIZE / 2;

            for (int i = 0; i < blue.length; i++) {
                int distance = Integer.parseInt(red[i].substring(1));

                if (blue[i].charAt(0) == 'L') {
                    for (int j = currentX - 1; j >= currentX - distance; j--) {
                        grid[j][currentY] = grid[j][currentY] == 'R' || grid[j][currentY] == 'X' ? 'X' : 'B';
                    }
                    currentX -= distance;
                } else if (blue[i].charAt(0) == 'R') {
                    for (int j = currentX + 1; j <= currentX + distance; j++) {
                        grid[j][currentY] = grid[j][currentY] == 'R' || grid[j][currentY] == 'X' ? 'X' : 'B';
                    }
                    currentX += distance;
                } else if (blue[i].charAt(0) == 'U') {
                    for (int j = currentY - 1; j >= currentY - distance; j--) {
                        grid[currentX][j] = grid[currentX][j] == 'R' || grid[currentX][j] == 'X' ? 'X' : 'B';
                    }
                    currentY -= distance;
                } else if (blue[i].charAt(0) == 'D') {
                    for (int j = currentY + 1; j <= currentY + distance; j++) {
                        grid[currentX][j] = grid[currentX][j] == 'R' || grid[currentX][j] == 'X' ? 'X' : 'B';
                    }
                    currentY += distance;
                }
            }

            int minDist = Integer.MAX_VALUE;

            System.out.println("Determining final answer...");

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[j][i] == 'X') {
                        System.out.println("x=" + j + "y=" + i);
                        int dist = Math.abs(j - (SIZE / 2)) + Math.abs(i - (SIZE / 2));
                        System.out.println("x=" + j + ", y=" + i + ", dist=" + dist);
                        if (dist < minDist) {
                            minDist = dist;
                        }
                    }
                }
            }

            PrintWriter writer = new PrintWriter("src/main/resources/temp", "UTF-8");


            for (int i = (SIZE/2) - 1000; i < (SIZE/2) + 1000; i++) {
                StringBuffer sb = new StringBuffer();
                for (int j = (SIZE/2) - 1000; j < (SIZE/2) + 1000; j++) {
                    sb.append(grid[j][i] + " ");
                }
                writer.println(sb.toString());
            }

            writer.close();

            /*
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    System.out.print(grid[j][i] + "  ");
                }
                System.out.println();
            }
            */

            System.out.println("Min Distance: " + minDist);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
