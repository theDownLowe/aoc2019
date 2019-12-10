package aoc2019.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Day5Challenge2 {
    private static final String FILE = "src/main/resources/2019_d5c1";

    public static void main(String[] args) {

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(FILE));
            String line = reader.readLine();
            String[] stringData = line.split(",");
            int[] data = new int[stringData.length];
            for (int i = 0; i < stringData.length; i++) {
                data[i] = Integer.parseInt(stringData[i]);
            }

            for (int i = 0; i < data.length;) {
                StringBuffer sb = new StringBuffer();
                sb.append("0000");
                sb.append(data[i]);
                String op = sb.toString();
                int opCode = Integer.parseInt("" + op.charAt(op.length() - 2) + op.charAt(op.length() - 1));
                int p1Mode = Integer.parseInt("" + op.charAt(op.length() - 3));
                int p2Mode = Integer.parseInt("" + op.charAt(op.length() - 4));
                int p3Mode = Integer.parseInt("" + op.charAt(op.length() - 5));

                if (opCode == 1) { // Add
                    int s1 = p1Mode == 1 ? data[i+1] : data[data[i+1]];
                    int s2 = p2Mode == 1 ? data[i+2] : data[data[i+2]];
                    int dest = data[i+3];
                    data[dest] = s1 + s2;
                    i+=4;
                } else if (opCode == 2) { // Multiply
                    int s1 = p1Mode == 1 ? data[i+1] : data[data[i+1]];
                    int s2 = p2Mode == 1 ? data[i+2] : data[data[i+2]];
                    int dest = data[i+3];
                    data[dest] = s1 * s2;
                    i+=4;
                } else if (opCode == 3) { // Input
                    Scanner input = new Scanner(System.in);
                    System.out.print("Input:");
                    int s = input.nextInt();
                    int dest = data[i+1];
                    data[dest] = s;
                    i+=2;
                } else if (opCode == 4) { // Output
                    System.out.println(data[data[i+1]]);
                    i+=2;
                } else if (opCode == 5) { // Jump if true
                    int p1 = p1Mode == 1 ? data[i+1] : data[data[i+1]];
                    int p2 = p2Mode == 1 ? data[i+2] : data[data[i+2]];
                    if (p1 != 0) {
                        i = p2;
                    } else {
                        i+=3;
                    }
                } else if (opCode == 6) { // Jump if false
                    int p1 = p1Mode == 1 ? data[i+1] : data[data[i+1]];
                    int p2 = p2Mode == 1 ? data[i+2] : data[data[i+2]];
                    if (p1 == 0) {
                        i = p2;
                    } else {
                        i+=3;
                    }
                } else if (opCode == 7) { // less than
                    int p1 = p1Mode == 1 ? data[i+1] : data[data[i+1]];
                    int p2 = p2Mode == 1 ? data[i+2] : data[data[i+2]];
                    data[data[i+3]] = p1 < p2 ? 1 : 0;
                    i+=4;
                } else if (opCode == 8) { // equals
                    int p1 = p1Mode == 1 ? data[i+1] : data[data[i+1]];
                    int p2 = p2Mode == 1 ? data[i+2] : data[data[i+2]];
                    data[data[i+3]] = p1 == p2 ? 1 : 0;
                    i+=4;
                } else if (opCode == 99) { // Halt
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
