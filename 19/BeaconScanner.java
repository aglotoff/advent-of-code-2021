import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BeaconScanner {
  private static class Point implements Comparable<Point> {
    int x;
    int y;
    int z;

    Point(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    @Override
    public String toString() {
      return x + "," + y + "," + z;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 31 * hash + x;
      hash = 31 * hash + y;
      hash = 31 * hash + z;
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
      return x == b.x && y == b.y && z == b.z;
    }

    @Override
    public int compareTo(Point p) {
      if (x != p.x) {
        return x - p.x;
      }
      if (y != p.y) {
        return y - p.y;
      }
      return z - p.z;
    }

    int distanceTo(Point p) {
      return Math.abs(x - p.x) + Math.abs(y - p.y) + Math.abs(z - p.z);
    }
  }

  private static class Scanner {
    private static final int[][][] rotations = {
      { {  1,  0,  0 }, {  0,  1,  0 }, {  0,  0,  1 } },
      { {  1,  0,  0 }, {  0,  0,  1 }, {  0, -1,  0 } },
      { {  1,  0,  0 }, {  0, -1,  0 }, {  0,  0, -1 } },
      { {  1,  0,  0 }, {  0,  0, -1 }, {  0,  1,  0 } },
  
      { { -1,  0,  0 }, {  0,  1,  0 }, {  0,  0, -1 } },
      { { -1,  0,  0 }, {  0,  0, -1 }, {  0, -1,  0 } },
      { { -1,  0,  0 }, {  0, -1,  0 }, {  0,  0,  1 } },
      { { -1,  0,  0 }, {  0,  0,  1 }, {  0,  1,  0 } },
  
      { {  0,  1,  0 }, {  1,  0,  0 }, {  0,  0, -1 } },
      { {  0,  1,  0 }, {  0,  0, -1 }, { -1,  0,  0 } },
      { {  0,  1,  0 }, { -1,  0,  0 }, {  0,  0,  1 } },
      { {  0,  1,  0 }, {  0,  0,  1 }, {  1,  0,  0 } },
  
      { {  0, -1,  0 }, {  1,  0,  0 }, {  0,  0,  1 } },
      { {  0, -1,  0 }, {  0,  0,  1 }, { -1,  0,  0 } },
      { {  0, -1,  0 }, { -1,  0,  0 }, {  0,  0, -1 } },
      { {  0, -1,  0 }, {  0,  0, -1 }, {  1,  0,  0 } },
  
      { {  0,  0,  1 }, {  1,  0,  0 }, {  0,  1,  0 } },
      { {  0,  0,  1 }, {  0,  1,  0 }, { -1,  0,  0 } },
      { {  0,  0,  1 }, { -1,  0,  0 }, {  0, -1,  0 } },
      { {  0,  0,  1 }, {  0, -1,  0 }, {  1,  0,  0 } },
  
      { {  0,  0, -1 }, {  1,  0,  0 }, {  0, -1,  0 } },
      { {  0,  0, -1 }, {  0, -1,  0 }, { -1,  0,  0 } },
      { {  0,  0, -1 }, { -1,  0,  0 }, {  0,  1,  0 } },
      { {  0,  0, -1 }, {  0,  1,  0 }, {  1,  0,  0 } },
    };

    List<Point> beacons;

    Scanner(List<Point> beacons) {
      this.beacons = new ArrayList<Point>(beacons);
      Collections.sort(this.beacons);
    }

    Scanner rotate(int n) {
      List<Point> list = new LinkedList<Point>();
      for (Point p: beacons) {
        int coords[] = new int[3];
        for (int i = 0; i < 3; i++) {
          coords[i] += p.x * rotations[n][i][0];
          coords[i] += p.y * rotations[n][i][1];
          coords[i] += p.z * rotations[n][i][2];
        }
        list.add(new Point(coords[0], coords[1], coords[2]));
      }
      return new Scanner(list);
    }

    Point distance(Scanner s) {
      HashMap<Point, Integer> d = new HashMap<Point, Integer>();
      for (Point a: beacons) {
        for (Point b: s.beacons) {
          Point p = new Point(a.x - b.x, a.y - b.y, a.z - b.z);
          d.put(p, d.containsKey(p) ? d.get(p) + 1 : 1);
          if (d.get(p) >= 12) {
            return p;
          }
        }
      }
      return null;
    }
  }

  public static void main(String[] args) {
    LinkedList<Scanner> scanners = new LinkedList<Scanner>();

    java.util.Scanner inputScanner = new java.util.Scanner(System.in);
    while (inputScanner.hasNextLine()) {
      inputScanner.nextLine();

      LinkedList<Point> beacons = new LinkedList<Point>();

      while (inputScanner.hasNextLine()) {
        String s = inputScanner.nextLine();
        if (s.length() == 0) {
          break;
        }
        String[] coords = s.split(",");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        int z = Integer.parseInt(coords[2]);
        beacons.add(new Point(x, y, z));
      }

      scanners.addLast(new Scanner(beacons));
    }
    inputScanner.close();

    LinkedList<Scanner> result = new LinkedList<Scanner>();
    ArrayList<Point> dist = new ArrayList<Point>();

    result.addLast(scanners.removeFirst());
    dist.add(new Point(0, 0, 0));

    mainLoop: while (!scanners.isEmpty()) {
      Iterator<Scanner> scannerIterator = scanners.iterator();
      while (scannerIterator.hasNext()) {
        Scanner t = scannerIterator.next();

        for (Scanner s: result) {
          for (int r = 0; r < 24; r++) {
            Scanner o = t.rotate(r);
            Point d = s.distance(o);
            if (d != null) {
              scannerIterator.remove();

              LinkedList<Point> newList = new LinkedList<Point>();
              for (Point c : o.beacons) {
                newList.add(new Point(c.x + d.x, c.y + d.y, c.z + d.z));
              }
              result.addLast(new Scanner(newList));
              dist.add(d);
              continue mainLoop;
            }
          }
        }
      }
    }

    HashSet<Point> points = new HashSet<Point>();
    for (Scanner s: result) {
      for (Point b: s.beacons) {
        points.add(b);
      }
    }

    int maxDist = 0;
    for (int i = 0; i < dist.size(); i++) {
      for (int j = i + 1; j < dist.size(); j++) {
        maxDist = Math.max(maxDist, dist.get(i).distanceTo(dist.get(j)));
      }
    }

    System.out.println(points.size());
    System.out.println(maxDist);
  }
}
