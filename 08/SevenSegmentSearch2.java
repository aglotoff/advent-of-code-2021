import java.util.Arrays;
import java.util.LinkedList;
import java.util.HashMap;

import java.util.Scanner;

/**
 * Seven Segment Search.
 * 
 * Read in the list of entries from the standard input, where each entry
 * consists of ten unique signal patterns and a four-digit value to be printed
 * on a seven-segment display. For each entry, determine which pattern
 * corresponds to which digit. Output the sum of all values.
 */
public class SevenSegmentSearch2 {
    /**
     * Sort letters in the given signal.
     * 
     * @param signal The initial string.
     * @returns The signal with all letters sorted.
     */
    private static String sortLetters(String signal) {
        char[] chars = signal.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    /**
     * Check whether the given signal contains all letters from another signal.
     * 
     * @param signal The signal to be checked.
     * @param letters The letters to search for.
     * @params true if the signal contains all the letters; false otherwise.
     */
    private static boolean hasAllLetters(String signal, String letters) {
        for (int i = 0; i < letters.length(); i++) {
            if (!signal.contains(letters.substring(i, i + 1))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int sum = 0;

        Scanner inputScanner = new Scanner(System.in);
        while (inputScanner.hasNextLine()) {
            String[] line = inputScanner.nextLine().split("\\s+\\|\\s+");
            String[] signals = line[0].split("\\s+");
            String[] value = line[1].split("\\s+");

            HashMap<Integer, String> patterns = new HashMap<Integer, String>();

            LinkedList<String> maybe235 = new LinkedList<String>();
            LinkedList<String> maybe069 = new LinkedList<String>();

            // Based on the signal length, we can immediately determine which
            // ones correspond to digits 1, 4, 7, and 8. The signals of length
            // 5 and 6 require additional analysis.
            for (String signal : signals) {
                switch (signal.length()) {
                    
                    case 2:
                        patterns.put(1, signal);
                        break;
                    case 3:
                        patterns.put(7, signal);
                        break;
                    case 4:
                        patterns.put(4, signal);
                        break;
                    case 7:
                        patterns.put(8, signal);
                        break;
                    case 5:
                        maybe235.add(signal);
                        break;
                    case 6:
                        maybe069.add(signal);
                        break;
                }
            }

            // Knowing the signals for 1, 4, 7, and 8, we can deduce the
            // signals for digits 0, 6, and 9.
            for (String signal: maybe069) {
                if (hasAllLetters(signal, patterns.get(4))) {
                    patterns.put(9, signal);
                } else if (hasAllLetters(signal, patterns.get(7))) {
                    patterns.put(0, signal);
                } else {
                    patterns.put(6, signal);
                }
            }

            // Finally, we can determine the remaining digits.
            for (String signal: maybe235) {
                if (hasAllLetters(signal, patterns.get(7))) {
                    patterns.put(3, signal);
                } else if (hasAllLetters(patterns.get(6), signal)) {
                    patterns.put(5, signal);
                } else {
                    patterns.put(2, signal);
                }
            }

            // Reverse the resulting map for faster lookup of digits.
            HashMap<String, Integer> digits = new HashMap<String, Integer>();
            for (int i = 0; i < 10; i++) {
                digits.put(sortLetters(patterns.get(i)), i);
            }

            // Decode the output value.
            int result = 0;
            for (int i = 0; i < 4; i++) {
                result = result * 10 + digits.get(sortLetters(value[i]));
            }

            sum += result;
        }
        inputScanner.close();

        System.out.println(sum);
    }
}
