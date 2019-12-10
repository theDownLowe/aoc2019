package aoc2019.day2;

import java.io.BufferedReader;
import java.io.FileReader;

public class Day2Challenge2 {
    private static final String FILE = "src/main/resources/2019_d2c1";

    public static void main(String[] args) {

        boolean found = false;

        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                System.out.println("Testing: " + noun + ", " + verb);

                BufferedReader reader;
                try {
                    reader = new BufferedReader(new FileReader(FILE));
                    String line = reader.readLine();
                    String[] stringData = line.split(",");
                    int[] data = new int[stringData.length];
                    for (int i = 0; i < stringData.length; i++) {
                        data[i] = Integer.parseInt(stringData[i]);
                    }

                    data[1] = noun;
                    data[2] = verb;

                    for (int i = 0; i < data.length; i += 4) {
                        int op = data[i];
                        int source1 = data[i + 1];
                        int source2 = data[i + 2];
                        int dest = data[i + 3];

                        if (op == 1) {
                            data[dest] = data[source1] + data[source2];
                        } else if (op == 2) {
                            data[dest] = data[source1] * data[source2];
                        } else if (op == 99) {
                            if (data[0] == 19690720) {
                                System.out.println(100 * noun + verb);
                                found = true;
                            }

                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (found) {
                    break;
                }
            }

            if (found) {
                break;
            }
        }
    }
}
