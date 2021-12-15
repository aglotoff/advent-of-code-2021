import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Find a path through the cave with the lowest total risk.
 * 
 * Read in the map of risk level throughout the cave and find a path from the
 * top left to the bottom right with the lowest total risk. Output the risk
 * value.
 * 
 * Then find a path with the lowest total risk in a map five-times-as-large as
 * the original cave.
 */
public class Chiton {
  /**
   * Helper class representing 2D points with integer coordinates and an
   * associated cost.
   */
  private static class Point implements Comparable<Point> {
    int x;
    int y;
    int cost;

    Point(int x, int y, int cost) {
      this.x = x;
      this.y = y;
      this.cost = cost;
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
      hash = 31 * hash + cost;
      return hash;
    }

    @Override
    public int compareTo(Point p) {
      return this.cost - p.cost;
    }
  }

  /**
   * Find the path with the lowest total risk using the Dijkstra's algorithm.
   *
   * @param risk The map of risk levels for each position.
   * @return The lowest total risk for any path.
   */
  private static int shortestPath(int[][] risk) {
    // Initialize the array of distances from the source node.
    int[][] dist = new int[risk.length][risk[0].length];
    for (int i = 0; i < risk.length; i++) {
      for (int j = 0; j < risk[i].length; j++) {
        dist[i][j] = Integer.MAX_VALUE;
      }
    }

    PriorityQueue<Point> q = new PriorityQueue<Point>();
    boolean[][] visited = new boolean[risk.length][risk[0].length];

    int[] dx = { 0, -1, 0, +1 };
    int[] dy = { -1, 0, +1, 0 };

    q.add(new Point(0, 0, 0));
    dist[0][0] = 0;

    while (!q.isEmpty()) {
      // Pop the node with the smallest distance, that is not already visited.
      Point u = q.poll();

      if (visited[u.y][u.x]) {
        continue;
      }
      visited[u.y][u.x] = true;

      // Update minimal distances of adjacent nodes.
      for (int i = 0; i < 4; i++) {
        int y = u.y + dy[i];
        int x = u.x + dx[i];

        if ((y < 0) || (y >= risk.length) || (x < 0) || (x >= risk.length)) {
          continue;
        }

        int cost = u.cost + risk[y][x];
        if (cost < dist[y][x]) {
          q.remove(new Point(x, y, risk[y][x]));
          dist[y][x] = cost;
          q.add(new Point(x, y, cost));
        }
      }
    }

    return dist[risk.length - 1][risk[0].length - 1];
  }

  public static void main(String[] args) {
    ArrayList<String> lines = new ArrayList<String>();

    // Read in the input lines.
    Scanner inputScanner = new Scanner(System.in);
    while (inputScanner.hasNextLine()) {
      lines.add(inputScanner.nextLine());
    }
    inputScanner.close();

    // Convert the input into a risk level map.
    int[][] map = new int[lines.size()][];
    for (int i = 0; i < lines.size(); i++) {
      String[] row = lines.get(i).split("");
      map[i] = new int[row.length];
      for (int j = 0; j < row.length; j++) {
        map[i][j] = Integer.parseInt(row[j]);
      }
    }

    System.out.println(shortestPath(map));

    // Create the extended risk value map.
    int[][] extendedMap = new int[map.length * 5][];
    for (int i = 0; i < lines.size(); i++) {
      for (int k = 0; k < 5; k++) {
        extendedMap[i + k * lines.size()] = new int[map[i].length * 5];
        for (int j = 0; j < map[i].length; j++) {
          for (int l = 0; l < 5; l++) {
            int risk = 1 + (map[i][j] + k + l - 1) % 9;
            extendedMap[i + k * map.length][j + l * map[i].length] = risk;
          }
        }
      }
    }

    System.out.println(shortestPath(extendedMap));
  }
}
