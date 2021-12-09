import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Smoke basins model.
 * 
 * Read in a cave heightmap from the standard input and find all low points.
 * Output the sum of risk levels of these low points.
 * 
 * Then, find the largest three basins and output the product of their sizes.
 */
public class SmokeBasin {
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

        // Convert the input into a heightmap.
        int[][] map = new int[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            String[] heights = lines.get(i).split("");

            map[i] = new int[heights.length];
            for (int j = 0; j < heights.length; j++) {
                map[i][j] = Integer.parseInt(heights[j]);
            }
        }

        ArrayList<Point> lowPoints = new ArrayList<Point>();

        // Find the low points.
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int height = map[i][j];

                if ((i > 0) && (height >= map[i - 1][j])) {
                    continue;
                }
                if ((i < (map.length - 1)) && (height >= map[i + 1][j])) {
                    continue;
                }
                if ((j > 0) && (height >= map[i][j - 1])) {
                    continue;
                }
                if ((j < (map[i].length - 1)) && (height >= map[i][j + 1])) {
                    continue;
                }

                lowPoints.add(new Point(i, j));
            }
        }

        // Determine the sum of risk levels for each low point
        int totalRisk = 0;
        for (Point p: lowPoints) {
            totalRisk += map[p.row][p.col] + 1;
        }
        System.out.println(totalRisk);

        Integer[] basinSizes = new Integer[lowPoints.size()];

        // Use breadth-first search to count all points that belong to the
        // basin associated with each low point
        for (int i = 0; i < lowPoints.size(); i++) {
            Point p = lowPoints.get(i);
            basinSizes[i] = 0;

            LinkedList<Point> q = new LinkedList<Point>();
            boolean[][] visited = new boolean[map.length][map[0].length];

            q.addLast(p);

            while (!q.isEmpty()) {
                Point current = q.removeFirst();
                int row = current.row;
                int col = current.col;

                if (visited[row][col]) {
                    continue;
                }

                visited[row][col] = true;
                basinSizes[i]++;

                if ((row > 0) && !visited[row - 1][col]) {
                    if (map[row - 1][col] < 9) {
                        q.addLast(new Point(row - 1, col));
                    }
                }

                if ((row < (map.length - 1)) && !visited[row + 1][col]) {
                    if (map[row + 1][col] < 9) {
                        q.addLast(new Point(row + 1, col));
                    }
                }

                if ((col > 0) && !visited[row][col - 1]) {
                    if (map[row][col - 1] < 9) {
                        q.addLast(new Point(row, col - 1));
                    }
                }

                if ((col < (map[row].length - 1)) && !visited[row][col + 1]) {
                    if (map[row][col + 1] < 9) {
                        q.addLast(new Point(row, col + 1));
                    }
                }
            }
        }

        Arrays.sort(basinSizes, Collections.reverseOrder());
        System.out.println(basinSizes[0] * basinSizes[1] * basinSizes[2]);
    }
}
