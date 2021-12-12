import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Find a path through the system of caves.
 * 
 * Read the map of caves from standard input and find the number of distinct
 * paths from the cave named start to the cave named end.
 * 
 * The optional command-line argument specifies whether we can visit a single
 * small cave twice.
 */
public class PassagePathing {
  /** The model of the cave system. */
  private CaveSystem caves;
  /** The number of times each cave has been visited. */
  private HashMap<String, Integer> visited;
  /** The total number of paths found. */
  private int pathCount;

  /**
   * Find the number of distinct paths in the cave system.
   * 
   * @param c The cave system to explore.
   * @param canVisitTwice Whether we can visit one small cave twice.
   */
  public PassagePathing(CaveSystem c, boolean canVisitTwice) {
    caves = c;
    visited = new HashMap<String, Integer>();
    search("start", "end", canVisitTwice);
  }

  /**
   * Return the number of paths found.
   * 
   * @return The number of paths.
   */
  public int getPathCount() {
    return pathCount;
  }

  /**
   * Find the number of distinct paths from u to v.
   * 
   * @param u The name of the cave where the search begins.
   * @param v The name of the cave where the search ends.
   * @param canVisitTwice Whether we can visit one small cave twice.
   */
  private void search(String u, String v, boolean canVisitTwice) {
    visited.put(u, visited.containsKey(u) ? visited.get(u) + 1 : 1);

    if (u.equals(v)) {
      pathCount++;
    } else {
      for (String x: caves.getAdjacentCaves(u)) {
        if (x.equals("start")) {
          continue;
        }

        if (Character.isUpperCase(x.charAt(0))) {
          search(x, v, canVisitTwice);
        } else {
          int visitCount = visited.containsKey(x) ? visited.get(x) : 0;
          if ((canVisitTwice && (visitCount == 1)) || (visitCount == 0)) {
            search(x, v, (visitCount == 0) && canVisitTwice);
          }
        }
      }
    }

    visited.put(u, visited.get(u) - 1);
  }

  public static void main(String[] args) {
    CaveSystem caves = new CaveSystem();

    boolean canVisitTwice = (args.length > 0)
      ? Boolean.parseBoolean(args[0])
      : false;

    Pattern pattern = Pattern.compile("([A-Za-z]+)-([A-Za-z]+)");
    Scanner inputScanner = new Scanner(System.in);

    while (inputScanner.hasNextLine()) {
      Matcher matcher = pattern.matcher(inputScanner.nextLine());
      if (matcher.find()) {
        String u = matcher.group(1);
        String v = matcher.group(2);
        caves.connectCaves(u, v);
      }
    }
    inputScanner.close();

    PassagePathing p = new PassagePathing(caves, canVisitTwice);
    System.out.println(p.getPathCount());
  }
}
