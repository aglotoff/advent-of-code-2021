import java.util.Scanner;

/**
 * Seven Segment Search.
 * 
 * Read in the list of entries from the standard input, where each entry
 * consists of ten unique signal patterns and a four-digit value to be printed
 * on a seven-segment display. Count the number of times digits 1, 4, 7, and 8
 * appear.
 */
public class SevenSegmentSearch {
    public static void main(String[] args) {
        int count = 0;

        Scanner inputScanner = new Scanner(System.in);
        while (inputScanner.hasNextLine()) {
            String[] line = inputScanner.nextLine().split("\\s+\\|\\s+");
            // String[] signals = line[0].split("\\s+");
            String[] value = line[1].split("\\s+");

            for (int i = 0; i < 4; i++) {
                int len = value[i].length();
                if ((len == 2) || (len == 4) || (len == 3) || (len == 7)) {
                    count++;
                }
            }
        }
        inputScanner.close();

        System.out.println(count);
    }
}
