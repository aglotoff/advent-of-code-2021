import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class SeaCucumber {
  private static class Point implements Comparable<Point> {
    int x;
    int y;

    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public String toString() {
      return x + "," + y;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 31 * hash + x;
      hash = 31 * hash + y;
      return hash;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o == null) {
        return false;
      }
      if (!(o instanceof Point)) {
        return false;
      }
      Point b = (Point) o;
      return x == b.x && y == b.y;
    }

    @Override
    public int compareTo(Point p) {
      if (x != p.x) {
        return x - p.x;
      }
      return y - p.y;
    }
  }

  public static void main(String[] args) {
    ArrayList<String> lines = new ArrayList<String>();
  
    Scanner inputScanner = new Scanner(System.in);
    while (inputScanner.hasNextLine()) {
      lines.add(inputScanner.nextLine());
    }
    inputScanner.close();

    LinkedList<Point> eastHerd = new LinkedList<Point>();
    LinkedList<Point> southHerd = new LinkedList<Point>();
    boolean[][] sites = new boolean[lines.size()][lines.get(0).length()];

    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      for (int j = 0; j < line.length(); j++) {
        if (line.charAt(j) == '>') {
          eastHerd.add(new Point(j, i));
          sites[i][j] = true;
        } else if (line.charAt(j) == 'v') {
          southHerd.add(new Point(j, i));
          sites[i][j] = true;
        }
      }
    }

    for (int t = 1; ; t++) {
      LinkedList<Point> moveEast = new LinkedList<Point>();

      for (Point p: eastHerd) {
        if (!sites[p.y][(p.x + 1) % sites[p.y].length]) {
          moveEast.add(p);
        }
      }

      for (Point p: moveEast) {
        sites[p.y][p.x] = false;
        p.x = (p.x + 1) % sites[p.y].length;
        sites[p.y][p.x] = true;
      }

      LinkedList<Point> moveSouth = new LinkedList<Point>();

      for (Point p: southHerd) {
        if (!sites[(p.y + 1) % sites.length][p.x]) {
          moveSouth.add(p);
        }
      }

      for (Point p: moveSouth) {
        sites[p.y][p.x] = false;
        p.y = (p.y + 1) % sites.length;
        sites[p.y][p.x] = true;
      }

      if ((moveEast.size() == 0) && (moveSouth.size() == 0)) {
        System.out.println(t);
        break;
      }
    }
  }
}
