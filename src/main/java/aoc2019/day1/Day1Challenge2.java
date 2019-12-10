package aoc2019.day1;

import java.io.BufferedReader;
import java.io.FileReader;

public class Day1Challenge2 {

    private static final String FILE = "src/main/resources/2019_d1c1";

    public static void main(String[] args) {

        int sum = 0;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(FILE));
            String line = reader.readLine();
            while (line != null) {
                sum += calculateTotalFuel(Integer.parseInt(line));
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(sum);
    }

    private static int calculateTotalFuel(int weight) {
        int sum = 0;

        double fuel = Math.floor(weight / 3) - 2;

        do {
            sum += fuel;
            fuel = Math.floor(fuel / 3) - 2;
        } while (fuel >= 0);

        return sum;
    }
}
