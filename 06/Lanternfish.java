import java.util.Scanner;

/**
 * Lanternfish growth rate model.
 * 
 * Read in the list of fish ages from the standard input and determine how many
 * lanternfish would be there after N days. The number of days is passed as the
 * command-line parameter.
 */
public class Lanternfish {
  /** Timer reset value. */
  private static final int TIMER_RESET = 6;
  /** Initial timer value for new fish. */
  private static final int TIMER_NEW = 8;

  public static void main(String[] args) {
    // Keep track of the amount of fish for each possible timer value
    long[] counts = new long[TIMER_NEW + 1];
    long totalFish = 0;

    int days = Integer.parseInt(args[0]);

    // Read in the initial list
    Scanner inputScanner = new Scanner(System.in);
    String initialList = inputScanner.nextLine();
    inputScanner.close();

    for (String f : initialList.split(",")) {
      counts[Integer.parseInt(f)]++;
      totalFish++;
    }

    // For each day, update the counters and determine the number of fish to
    // be spawned
    for (int i = 0; i < days; i++) {
      long fishToSpawn = counts[0];

      for (int j = 0; j < TIMER_NEW; j++) {
        counts[j] = counts[j + 1];
      }

      counts[TIMER_RESET] += fishToSpawn;
      counts[TIMER_NEW] = fishToSpawn;

      totalFish += fishToSpawn;
    }

    System.out.println(totalFish);
  }
}
