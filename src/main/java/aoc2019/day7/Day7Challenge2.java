package aoc2019.day7;

import java.io.BufferedReader;import java.io.FileReader;import java.util.ArrayList;public class Day7Challenge2 {

    private static final String FILE = "src/main/resources/2019_d7c1";

    public static void main(String[] args) {

        int maxThrust = 0;

        for (int ps1 = 5; ps1 < 10; ps1++) {
            for (int ps2 = 5; ps2 < 10; ps2++) {
                if (ps2 == ps1) continue;
                for (int ps3 = 5; ps3 < 10; ps3++) {
                    if (ps3 == ps2 || ps3 == ps1) continue;
                    for (int ps4 = 5; ps4 < 10; ps4++) {
                        if (ps4 == ps3 || ps4 == ps2 || ps4 == ps1) continue;
                        for (int ps5 = 5; ps5 < 10; ps5++) {
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

        ArrayList<int[]> ampData = new ArrayList<int[]>();

        BufferedReader reader;
        try {
            reader= new BufferedReader(new FileReader(FILE));
            String line = reader.readLine();
            String[] stringData = line.split(",");
            int[] data = new int[stringData.length];
            for (int i = 0; i < stringData.length; i++) {
                data[i] = Integer.parseInt(stringData[i]);
            }
            ampData.add(data.clone());
            ampData.add(data.clone());
            ampData.add(data.clone());
            ampData.add(data.clone());
            ampData.add(data.clone());

        } catch (Exception e) {e.printStackTrace();}

        int output = 0;
        boolean a1In = true;
        boolean a2In = true;
        boolean a3In = true;
        boolean a4In = true;
        boolean a5In = true;

        int a1IP = 0;
        int a2IP = 0;
        int a3IP = 0;
        int a4IP = 0;
        int a5IP = 0;

        boolean hasHalted = false;

        while (!hasHalted) {
            for (int amp = 1; amp <= 5; amp++) {
                int[] data = ampData.get(amp-1);

                int i = 0;
                if (amp == 1) {
                    i = a1IP;
                } else if (amp == 2) {
                    i = a2IP;
                } else if (amp == 3) {
                    i = a3IP;
                } else if (amp == 4) {
                    i = a4IP;
                } else if (amp == 5) {
                    i = a5IP;
                }

                for (; i < data.length;) {


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

                        if (amp == 1 && a1In) {
                            a1In = false;
                            s = ps1;
                        } else if (amp == 2 && a2In) {
                            a2In = false;
                            s = ps2;
                        } else if (amp == 3 && a3In) {
                            a3In = false;
                            s = ps3;
                        } else if (amp == 4 && a4In) {
                            a4In = false;
                            s = ps4;
                        } else if (amp == 5 && a5In) {
                            a5In = false;
                            s = ps5;
                        } else {
                            s = output;
                        }
                        int dest = data[i+1];
                        data[dest] = s;
                        i+=2;
                    } else if (opCode == 4) { // Output
                        output = data[data[i+1]];
                        if (amp == 1) {
                            a1IP = i+2;
                        } else if (amp == 2) {
                            a2IP = i+2;
                        } else if (amp == 3) {
                            a3IP = i+2;
                        } else if (amp == 4) {
                            a4IP = i+2;
                        } else if (amp == 5) {
                            a5IP = i+2;
                        }
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
                        hasHalted = true;
                        break;
                    }
                }
            }
        }

        return output;
    }

}
