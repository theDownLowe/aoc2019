package aoc2019.day1;

import java.io.BufferedReader;
import java.io.FileReader;

public class Day1Challenge1 {

    private static final String FILE = "src/main/resources/2019_d1c1";

    public static void main(String[] args) {

        int sum = 0;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(FILE));
            String line = reader.readLine();
            while (line != null) {
                sum += Math.floor(Integer.parseInt(line) / 3) - 2;
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(sum);
    }
}
