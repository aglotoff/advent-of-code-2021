import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Model octopus energy levels and flashes of light.
 * 
 * Read in the initial energy levels for each octopus from the standard input
 * and output the first step number during which all octopuses flash.
 */
public class DumboOctopus2 {
  /**
   * Helper class to represent 2D points with integer coordinates.
   */
  private static class Point {
    int row;
    int col;

    Point(int x, int y) {
      row = x;
      col = y;
    }
  }

  public static void main(String[] args) {
    ArrayList<String> lines = new ArrayList<String>();

    // Read in the input lines.
    Scanner inputScanner = new Scanner(System.in);
    while (inputScanner.hasNextLine()) {
      lines.add(inputScanner.nextLine());
    }
    inputScanner.close();

    // Convert the input into an energy level map.
    int[][] energy = new int[lines.size()][];
    for (int i = 0; i < lines.size(); i++) {
      String[] row = lines.get(i).split("");

      energy[i] = new int[row.length];
      for (int j = 0; j < row.length; j++) {
        energy[i][j] = Integer.parseInt(row[j]);
      }
    }
   
    int totalOctopuses = energy.length * energy[0].length;

    // Use breadth-first search to count the number of flashes
    for (int t = 1; ; t++) {
      int flashes = 0;
      boolean[][] flashed = new boolean[energy.length][energy[0].length];
      LinkedList<Point> q = new LinkedList<Point>();

      // Increase the energy level of each octopus by 1. Add all octopuses with
      // energy level greater than 9 to the queue.
      for (int i = 0; i < energy.length; i++) {
        for (int j = 0; j < energy[i].length; j++) {
          energy[i][j]++;
          if (energy[i][j] > 9) {
            q.addLast(new Point(i, j));
          }
        }
      }

      while (!q.isEmpty()) {
        Point p = q.removeFirst();
        
        if (flashed[p.row][p.col]) {
          continue;
        }

        // For all octopuses that flashed during this step, reset the energy
        // level to 0.
        flashed[p.row][p.col] = true;
        energy[p.row][p.col] = 0;
        flashes++;

        // Increase the energy level of all adjacent octopuses.
        // If this causes an octopus to have an energy level greater than 9,
        // add it to the queue.
        int minRow = Math.max(p.row - 1, 0);
        int maxRow = Math.min(p.row + 1, energy.length - 1);
        for (int i = minRow; i <= maxRow; i++) {
          int minCol = Math.max(p.col - 1, 0);
          int maxCol = Math.min(p.col + 1, energy[i].length - 1);

          for (int j = minCol; j <= maxCol; j++) {
            if (!flashed[i][j]) {
              energy[i][j]++;
              if (energy[i][j] > 9) {
                q.addLast(new Point(i, j));
              }
            }
          }
        }
      }

      if (flashes == totalOctopuses) {
        System.out.println(t);
        break;
      }
    }
  }
}
