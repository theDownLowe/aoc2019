package aoc2019.day4;

public class Day4Challenge2 {

    // 6 digits
    // Between 245318 and 765747
    // 2 adjacent digits are the same
    // Non-decreasing order

    public static void main(String[] args) {
        int candidates = 0;

        for (int i = 245318; i <= 765747; i++) {
            candidates += isNonDecreasing(i) && containsDoubles(i) ? 1 : 0;
        }

        System.out.println(candidates);
    }

    private static boolean isNonDecreasing(int num) {
        String s = String.valueOf(num);
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) > s.charAt(i+1)) {
                return false;
            }
        }

        return true;
    }

    private static boolean containsDoubles(int num) {
        String s = String.valueOf(num);
        int currentNumCount = 1;
        for (int i = 0; i < s.length() - 1; i++) {


            if (s.charAt(i) == s.charAt(i+1)) {
                currentNumCount++;
            } else {
                if (currentNumCount == 2) {
                    return true;
                }

                currentNumCount = 1;
            }
        }

        if (currentNumCount == 2) {
            return true;
        }

        return false;
    }
}
