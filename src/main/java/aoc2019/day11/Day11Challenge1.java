package aoc2019.day11;

import javax.sound.midi.SysexMessage;import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Day11Challenge1 {
    private static final String FILE = "src/main/resources/2019_d11c1";
    private static final int HULL_SIZE = 85;

    // '.'=Unassigned/Black 'W'=White 'B'=Assigned/Black
    private static char[][] hullGrid = new char[HULL_SIZE][HULL_SIZE];

    public static void main(String[] args) {

        /* Address, Data. **/
        IntCodeDataMap data = readData();

        // Paint all empty
        for (int i = 0; i < hullGrid.length; i++) {
            for (int j = 0; j < hullGrid[0].length; j++) {
                hullGrid[i][j] = '.';
            }
        }

        int robotX = HULL_SIZE / 2;
        int robotY = HULL_SIZE / 2;
        int robotDirection = 1;

        hullGrid[robotY][robotX] = 'W';


        long relativeBase = 0;
        long ip = 0; // Instruction Pointer
        boolean halted = false;
        boolean isColorOutput = true;

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
                long s = hullGrid[robotY][robotX] == 'W' ? 1 : 0;
                data.put(p1Mode == 2 ? relativeBase : data.get(ip+1), s);
                ip+=2;
            } else if (opCode == 4) { // Output
                long output = p1Mode == 2 ? data.get(relativeBase + data.get(ip+1)) : param1;
                if (isColorOutput) {
                    // Print color to grid
                    hullGrid[robotY][robotX] = output == 0 ? ',' : 'W';
                } else {
                    // Turn robot and move 1 space
                    robotDirection = output == 0 ? robotDirection-1 : robotDirection+1;
                    if (robotDirection == -1) robotDirection = 3;
                    if (robotDirection % 4 == 1) {
                        robotY--;
                    } else if (robotDirection % 4 == 2) {
                        robotX++;
                    } else if (robotDirection % 4 == 3) {
                        robotY++;
                    } else {
                        robotX--;
                    }
                }
                isColorOutput = !isColorOutput;
                System.out.println(output);
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

        // Count assigned elements
        int paintedCount = 0;
        for (int i = 0; i < hullGrid.length; i++) {
            System.out.println(Arrays.toString(hullGrid[i]));
            for (int j = 0; j < hullGrid[0].length; j++) {
                if (hullGrid[i][j] != '.') paintedCount++;
            }
        }


        System.out.println("Painted Count: " + paintedCount);
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
