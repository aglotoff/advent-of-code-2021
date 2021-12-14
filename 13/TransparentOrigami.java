import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Fold transparent paper.
 * 
 * Read in the list of dots on the paper and a list of fold instructions from
 * the standard input. Output the number of visible dots after completing each
 * instruction and the resulting eight-letter code.
 */
public class TransparentOrigami {
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

  public static void main(String[] args) {
    HashSet<Point> dots = new HashSet<Point>();
    int width = 0;
    int height = 0;

    Scanner inputScanner = new Scanner(System.in);
  
    // Read in the list of initial dots on the paper
    while (inputScanner.hasNextLine()) {
      String[] coords = inputScanner.nextLine().split(",");

      if (coords.length != 2) {
        break;
      }

      int x = Integer.parseInt(coords[0]);
      int y = Integer.parseInt(coords[1]);

      width = Math.max(width, x + 1);
      height = Math.max(height, y + 1);

      dots.add(new Point(y, x));
    }

    // Display the initial number of dots.
    System.out.println("0: " + dots.size());

    // Read in the list of instructions and fold the paper.
    for (int t = 1; inputScanner.hasNextLine(); t++) {
      Pattern pattern = Pattern.compile("fold along (x|y)=(\\d+)");
      Matcher matcher = pattern.matcher(inputScanner.nextLine());
      if (matcher.find()) {
        String direction = matcher.group(1);
        int line = Integer.parseInt(matcher.group(2));

        if (direction.equals("y")) {
          // Fold up
          for (int j = 0; j < width; j++) {
            for (int i = line + 1; i < height; i++) {
              Point p = new Point(i, j);
              if (dots.contains(p)) {
                dots.remove(p);
                dots.add(new Point(line - (i - line), j));
              }
            }
          }
          height = line;
        } else {
          // Fold left
          for (int i = 0; i < height; i++) {
            for (int j = line + 1; j < width; j++) {
              Point p = new Point(i, j);
              if (dots.contains(p)) {
                dots.remove(p);
                dots.add(new Point(i, line - (j - line)));
              }
            }
          }
          width = line;
        }
      }

      // Display the number of visible dots after each iteration.
      System.out.println(t + ": " + dots.size());
    }

    // Display the final letters.
    System.out.println();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Point p = new Point(i, j);
        System.out.print(dots.contains(p) ? '#' : '-');
      }
      System.out.println();
    }

    inputScanner.close();
  }
}
