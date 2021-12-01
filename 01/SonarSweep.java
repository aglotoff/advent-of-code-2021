import java.util.Scanner;

/**
 * Count the number of times a depth measurement increases.
 */
public class SonarSweep {
  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);

    int previous = inputScanner.nextInt();
    int increaseCount = 0;

    while (inputScanner.hasNextInt()) {
      int next = inputScanner.nextInt();
      if (next > previous) {
        increaseCount++;
      }
      previous = next;
    }
    inputScanner.close();

    System.out.println(increaseCount);
  }
}
