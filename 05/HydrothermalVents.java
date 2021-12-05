import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hydrothermal Venture.
 *
 * Read in the sequence of vents lines from the standard input and count the
 * number of points where at least two line segments overlap.
 */
public class HydrothermalVents {
  /**
   * Helper class representing 2D points with integer coordinates.
   */
  private static class Point {
    int x;
    int y;

    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      if (this.getClass() != o.getClass()) return false;

      Point p = (Point) o;
      return (p.x == this.x) && (p.y == this.y);
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 31 * hash + x;
      hash = 31 * hash + y;
      return hash;
    }
  }

  /**
   * Returns the value to be used as an increment when iterating from n0 to n1.
   *
   * @param n0 The first number.
   * @param n1 The last number.
   * @return The increment value.
   */
  private static int getRangeStep(int n0, int n1) {
    if (n0 > n1) return -1;
    if (n0 < n1) return 1;
    return 0;
  }

  /**
   * Return the list of all points covered by the given line.
   *
   * @param x0 The starting x-coordinate of the line.
   * @param y0 The starting y-coordinate of the line.
   * @param x1 The ending x-coordinate of the line.
   * @param y1 The ending y-coordinate of the line.
   * @return The list of points on the line.
   */
  private static List<Point> getPointsInLine(int x0, int y0, int x1, int y1) {
    LinkedList<Point> points = new LinkedList<Point>();

    int dx = getRangeStep(x0, x1);
    int dy = getRangeStep(y0, y1);
    
    for (int x = x0, y = y0; ; x += dx, y += dy) {
      points.add(new Point(x, y));

      if ((x == x1) && (y == y1)) {
        break;
      }
    }

    return points;
  }

  private static final String regex = "(\\d+),(\\d+)\\s+->\\s+(\\d+),(\\d+)";

  public static void main(String[] args) {
    HashMap<Point, Integer> pointCounts = new HashMap<Point, Integer>();

    Pattern pattern = Pattern.compile(regex);
    Scanner inputScanner = new Scanner(System.in);

    // For each point count the number of times it is covered by some line.
    while (inputScanner.hasNextLine()) {
      Matcher matcher = pattern.matcher(inputScanner.nextLine());
      if (matcher.find()) {
        int x0 = Integer.parseInt(matcher.group(1));
        int y0 = Integer.parseInt(matcher.group(2));
        int x1 = Integer.parseInt(matcher.group(3));
        int y1 = Integer.parseInt(matcher.group(4));

        // Uncomment this code to consider only horizontal and vertical lines:
        // if ((x0 != x1) && (y0 != y1)) {
        //   continue;
        // }

        for (Point p : getPointsInLine(x0, y0, x1, y1)) {
          Integer count = pointCounts.get(p);
          pointCounts.put(p, count == null ? 1 : count + 1);
        }
      }
    }

    inputScanner.close();

    int overlapCount = 0;
    for (int count : pointCounts.values()) {
      if (count >= 2) {
        overlapCount++;
      }
    }

    System.out.println(overlapCount);
  }
}
