import java.util.Scanner;

/**
 * Read the list of depth measurements from the standard input using a
 * three-measurement sliding window and count the number of times the sum of 
 * values in this sliding window increases.
 */
public class SonarSweepSlidingWindow {
  private static final int WINDOW_SIZE = 3;

  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);

    // Read in the first three measurements.
    int[] window = new int[WINDOW_SIZE];
    int previousSum = 0;
    for (int i = 0; i < WINDOW_SIZE; i++) {
      window[i] = inputScanner.nextInt();
      previousSum += window[i];
    }

    // Read in the remaining measurements, shifting the "window" by one number
    // at a time and count the number of times the sum increases.
    int increaseCount = 0;
    while (inputScanner.hasNextInt()) {
      int nextSum = previousSum - window[0];

      for (int i = 0; i < WINDOW_SIZE - 1; i++) {
        window[i] = window[i + 1];
      }

      window[WINDOW_SIZE - 1] = inputScanner.nextInt();
      nextSum += window[WINDOW_SIZE - 1];

      if (nextSum > previousSum) {
        increaseCount++;
      }
      
      previousSum = nextSum;
    }

    inputScanner.close();

    System.out.println(increaseCount);
  }
}
