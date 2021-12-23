import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;

public class Amphipod {
  private static class Position {
    final int x;
    final int y;
    final char type;

    Position(int x, int y, char type) {
      this.x = x;
      this.y = y;
      this.type = type;
    }

    boolean isInPlace() {
      return x == ((type - 'A') + 1) * 2;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      if (this.getClass() != o.getClass()) return false;

      Position p = (Position) o;
      return (p.x == this.x) && (p.y == this.y) && (p.type == this.type);
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash = 31 * hash + x;
      hash = 31 * hash + y;
      hash = 31 * hash + type;
      return hash;
    }
  }

  private static class Situation implements Comparable<Situation> {
    String[] diagram = new String[5];
    int cost = 0;

    @Override
    public int compareTo(Situation s) {
      return cost - s.cost;
    }

    boolean isFinal() {
      for (int i = 0; i < 4; i++) {
        for (int j = 1; j < diagram.length; j++) {
          if (diagram[j].charAt((i + 1) * 2) != ('A' + i)) {
            return false;
          }
        }
      }
      return true;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null) return false;
      if (this.getClass() != o.getClass()) return false;

      Situation s = (Situation) o;
      for (int i = 0; i < diagram.length; i++) {
        if (!s.diagram[i].equals(diagram[i])) {
          return false;
        }
      }
      return true;
    }

    @Override
    public String toString() {
      return String.join("\n", Arrays.asList(diagram));
    }

    @Override
    public int hashCode() {
      return toString().hashCode();
    }
  }

  private static final int[] energy = { 1, 10, 100, 1000 };

  public static void main(String[] args) {
    Situation initialSituation = new Situation();

    Scanner inputScanner = new Scanner(System.in);
    inputScanner.nextLine();
    for (int y = 0; y <= 4; y++) {
      String line = inputScanner.nextLine();
      initialSituation.diagram[y] = line.substring(1, line.length() - 1).replace('#', ' ');
    }
    inputScanner.close();
  
    PriorityQueue<Situation> q = new PriorityQueue<Situation>();
    q.add(initialSituation);

    HashMap<Situation, Integer> costs = new HashMap<Situation, Integer>();
    costs.put(initialSituation, 0);

    while (!q.isEmpty()) {
      Situation s = q.poll();

      if (s.isFinal()) {
        System.out.println(s);
        System.out.println(s.cost);
        break;
      }

      LinkedList<Position> pos = new LinkedList<Position>();
      for (int y = 0; y < s.diagram.length; y++) {
        for (int x = 0; x < s.diagram[y].length(); x++) {
          char c = s.diagram[y].charAt(x);
          if ((c >= 'A') && (c <= 'D')) {
            pos.add(new Position(x, y, c));
          }
        }
      }

      for (Position p: pos) {
        int x = p.x;
        int y = p.y;

        if (y > 0) {
          if (s.diagram[y-1].charAt(x) != '.') {
            continue;
          }

          if (p.isInPlace()) {
            boolean full = true;
            for (int i = y + 1; i < s.diagram.length; i++) {
              if (s.diagram[i].charAt(x) != p.type) {
                full = false;
                break;
              }
            }
            if (full) {
              continue;
            }
          }
        }

        int minX, maxX;
        for (minX = x; minX > 0 && s.diagram[0].charAt(minX-1) == '.'; minX--)
          ;
        for (maxX = x; maxX < 10 && s.diagram[0].charAt(maxX+1) == '.'; maxX++)
          ;

        for (int k = minX; k <= maxX; k++) {
          if (k == x) {
            continue;
          }

          int dy = 0;
          if ((k > 0) && (k < 10) && (k % 2 == 0)) {
            if (k != ((p.type - 'A') + 1) * 2) {
              continue;
            }

            boolean full = true;

            int h;
            for (h = 0; h <= s.diagram.length; h++) {
              if (s.diagram[s.diagram.length - 1 - h].charAt(k) == '.')
                break;
              if (s.diagram[s.diagram.length - 1 - h].charAt(k) != p.type) {
                full = false;
                break;
              }
            }

            if (!full) {
              continue;
            }

            dy = s.diagram.length - 1 - h;
          } else if (y == 0) {
            continue;
          }

          Situation next = new Situation();
          for (int i = 0; i < s.diagram.length; i++) {
            next.diagram[i] = s.diagram[i];
          }

          if (y != dy) {
            char[] newLine = s.diagram[y].toCharArray();
            newLine[x] = '.';
            next.diagram[y] = new String(newLine);
          }

          char[] newLine = s.diagram[dy].toCharArray();
          if (y == dy) {
            newLine[x] = '.';
          }
          newLine[k] = p.type;
          next.diagram[dy] = new String(newLine);

          int newCost = s.cost + (y + Math.abs(k - x) + dy) * energy[p.type - 'A'];
          if (costs.containsKey(next)) {
            if (costs.get(next) < newCost) {
              continue;
            }
            q.remove(next);
          }

          next.cost = newCost;
          q.add(next);
          costs.put(next, newCost);

          if (dy != 0) {
            break;
          }
        }
      }
    }
  }
}