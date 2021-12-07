import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Align crab submarine positions.
 * 
 * Read in the list of initial horizontal positions from the standard input and
 * determine the position that the crabs can align to using the least fuel
 * possible. Output the amount of spent fuel.
 * 
 * Each step costs 1 more unit of fuel than the last one.
 */
public class AlignCrabs2 {
  public static void main(String[] args) {
    LinkedList<Integer> positions = new LinkedList<Integer>();
  
    Scanner inputScanner = new Scanner(System.in);
    for (String pos: inputScanner.nextLine().split(",")) {
      positions.add(Integer.parseInt(pos));
    }
    inputScanner.close();

    int min = Collections.min(positions);
    int max = Collections.max(positions);

    int minCost = Integer.MAX_VALUE;
    for (int i = min; i <= max; i++) {
      int totalCost = 0;
      for (int pos: positions) {
        int diff = Math.abs(pos - i);
        // 1 + 2 + ... + N = N * (N + 1) / 2
        totalCost += (diff * (diff + 1)) / 2;
      }

      if (totalCost < minCost) {
        minCost = totalCost;
      }
    }

    System.out.println(minCost);
  }
}
