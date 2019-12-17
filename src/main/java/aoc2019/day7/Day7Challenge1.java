package aoc2019.day7;

import java.io.BufferedReader;import java.io.FileReader;import java.util.Scanner;public class Day7Challenge1 {

    private static final String FILE = "src/main/resources/2019_d7c1";

    public static void main(String[] args) {

        int maxThrust = 0;

        for (int ps1 = 0; ps1 < 5; ps1++) {
            for (int ps2 = 0; ps2 < 5; ps2++) {
                if (ps2 == ps1) continue;
                for (int ps3 = 0; ps3 < 5; ps3++) {
                    if (ps3 == ps2 || ps3 == ps1) continue;
                    for (int ps4 = 0; ps4 < 5; ps4++) {
                        if (ps4 == ps3 || ps4 == ps2 || ps4 == ps1) continue;
                        for (int ps5 = 0; ps5 < 5; ps5++) {
                            if (ps5 == ps4 || ps5 == ps3 || ps5 == ps2 || ps5 == ps1) continue;

                            int thrust = runAmplifiers(ps1, ps2, ps3, ps4, ps5);
                            System.out.println(ps1 + "" + ps2 + "" + ps3 + "" + ps4 + "" + ps5 + ": " + thrust);
                            if (thrust > maxThrust) {
                                maxThrust = thrust;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Max Thrust: " + maxThrust);
    }

    private static int runAmplifiers(int ps1, int ps2, int ps3, int ps4, int ps5) {

        int output = 0;

        for (int amp = 1; amp <= 5; amp++) {
            boolean firstInput = true;
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
                        int s = 0;
                        if (firstInput) {
                            firstInput = false;
                            if (amp == 1) {
                                s = ps1;
                            } else if (amp == 2) {
                                s = ps2;
                            } else if (amp == 3) {
                                s = ps3;
                            } else if (amp == 4) {
                                s = ps4;
                            } else if (amp == 5) {
                                s = ps5;
                            }
                        } else {
                            s = output;
                        }
                        int dest = data[i+1];
                        data[dest] = s;
                        i+=2;
                    } else if (opCode == 4) { // Output
                        output = data[data[i+1]];
                        i+=2;
                        break;
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

        return output;
    }

}
