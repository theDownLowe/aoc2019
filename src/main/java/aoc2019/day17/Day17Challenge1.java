package aoc2019.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;import java.util.Scanner;

public class Day17Challenge1 {
    private static final String FILE = "src/main/resources/2019_d9c1";

    public static void main(String[] args) {

        /* Address, Data. **/
        IntCodeDataMap data = readData();

        long relativeBase = 0;
        long ip = 0; // Instruction Pointer
        boolean halted = false;

        while (!halted) {
            StringBuffer sb = new StringBuffer();
            sb.append("0000");
            sb.append(Integer.parseInt("" + data.get(ip)));
            String op = sb.toString();
            int opCode = Integer.parseInt("" + op.charAt(op.length() - 2) + op.charAt(op.length() - 1));
            int p1Mode = Integer.parseInt("" + op.charAt(op.length() - 3));
            int p2Mode = Integer.parseInt("" + op.charAt(op.length() - 4));
            int p3Mode = Integer.parseInt("" + op.charAt(op.length() - 5));

            long param1 = p1Mode == 1 ? data.get(ip+1) : (p1Mode == 2 ? data.get(relativeBase + data.get(ip+1)) : data.get(data.get(ip+1)));
            long param2 = p2Mode == 1 ? data.get(ip+2) : (p2Mode == 2 ? data.get(relativeBase + data.get(ip+2)) : data.get(data.get(ip+2)));
            long param3 = p2Mode == 1 ? data.get(ip+3) : (p3Mode == 2 ? data.get(relativeBase + data.get(ip+3)) : data.get(data.get(ip+3)));

            if (opCode == 1) { // Add
                long dest = p3Mode == 2 ? (relativeBase + data.get(ip+3)) : data.get(ip+3);
                data.put(dest, param1 + param2);
                ip+=4;
            } else if (opCode == 2) { // Multiply
                long dest = p3Mode == 2 ? (relativeBase + data.get(ip+3)) : data.get(ip+3);
                data.put(dest, param1 * param2);
                ip+=4;
            } else if (opCode == 3) { // Input
                Scanner in = new Scanner(System.in);
                System.out.print("Input: ");
                long s = in.nextLong();
                data.put(p1Mode == 2 ? relativeBase : param1, s);
                ip+=2;
            } else if (opCode == 4) { // Output
                System.out.println(p1Mode == 2 ? data.get(relativeBase + data.get(ip+1)) : param1);
                ip+=2;
            } else if (opCode == 5) { // Jump if true
                if (param1 != 0) {
                    ip = param2;
                } else {
                    ip+=3;
                }
            } else if (opCode == 6) { // Jump if false
                if (param1 == 0) {
                    ip = param2;
                } else {
                    ip+=3;
                }
            } else if (opCode == 7) { // less than
                long dest = p3Mode == 2 ? (relativeBase + data.get(ip+3)) : data.get(ip+3);
                data.put(dest, new Long(param1 < param2 ? 1 : 0));
                ip+=4;
            } else if (opCode == 8) { // equals
                long dest = p3Mode == 2 ? (relativeBase + data.get(ip+3)) : data.get(ip+3);
                data.put(dest, new Long(param1 == param2 ? 1 : 0));
                ip+=4;
            } else if (opCode == 9) { // Add to Relative Base
                relativeBase += param1;
                ip+=2;
            } else if (opCode == 99) { // Halt
                halted = true;
            }
        }
    }

    private static IntCodeDataMap readData() {
        IntCodeDataMap data = new IntCodeDataMap();

        long address = 0;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(FILE));
            String line = reader.readLine();
            String[] stringData = line.split(",");
            for (int i = 0; i < stringData.length; i++) {
                data.put(address++, new Long(stringData[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private static class IntCodeDataMap extends HashMap<Long, Long> {

        /** Returns 0 if the data does not exist. **/
        public Long get(Long data) {
            Long l = super.get(data);
            return l == null ? 0 : l;
        }
    }
}
