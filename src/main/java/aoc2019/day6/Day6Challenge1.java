package aoc2019.day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;import java.util.List;

public class Day6Challenge1 {

    private static final String FILE = "src/main/resources/2019_d6c1";
    private static HashMap<String, ArrayList<String>> orbits = new HashMap<String, ArrayList<String>>();
    private static int orbitCount = 0;

    public static void main(String[] args) {

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(FILE));
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split("\\)");
                addOrbit(data[0], data[1]);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String planet : orbits.keySet()) {
            System.out.print(planet + ": ");
            for (String orbiter : orbits.get(planet)) {
                System.out.print(orbiter + ", ");
            }
            System.out.println();
        }

        ArrayList<String> orbitChain = new ArrayList<String>();
        orbitChain.add("COM");
        searchOrbitTree(orbitChain, "COM", 0);

        System.out.println("Orbits: " + orbitCount);
    }

    private static void searchOrbitTree(ArrayList<String> orbitChain, String parent, int currentDepth) {
        if (parent.equals("DGR") || parent.equals("SAN") || parent.equals("YOU")) {
            System.out.println(parent + " depth: " + currentDepth);
        }

        if (orbits.get(parent) != null) {
            for (String child : orbits.get(parent)) {
                ArrayList<String> newChain = (ArrayList<String>)orbitChain.clone();
                newChain.add(child);
                if (child.equals("YOU") || child.equals("SAN")) {
                    printOrbitChain(newChain);
                }
                searchOrbitTree(newChain, child, currentDepth+1);
            }
        }

        orbitCount += currentDepth;
    }

    private static void printOrbitChain(ArrayList<String> orbitChain) {
        for (String planet : orbitChain) {
            System.out.print(planet + " ");
        }
        System.out.println();
    }

    private static void addOrbit(String planet, String orbiter) {
        if (orbits.containsKey(planet)) {
            orbits.get(planet).add(orbiter);
        } else {
            ArrayList<String> orbitList = new ArrayList<String>();
            orbitList.add(orbiter);
            orbits.put(planet, orbitList);
        }
    }
}
