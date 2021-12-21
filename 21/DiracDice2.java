import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Scanner;

public class DiracDice2 {
  private static class Universe {
    int pos1;
    int pos2;
    int score1;
    int score2;

    Universe(int p1, int s1, int p2, int s2) {
      pos1 = p1;
      pos2 = p2;
      score1 = s1;
      score2 = s2;
    }

    @Override
    public int hashCode() {
      int hash = 7;
      hash += 31 * pos1;
      hash += 31 * score1;
      hash += 31 * pos2;
      hash += 31 * score2;
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
      if (!(o instanceof Universe)) {
        return false;
      }
      Universe u = (Universe) o;
      return (pos1 == u.pos1 &&
        pos2 == u.pos2 &&
        score1 == u.score1 &&
        score2 == u.score2);
    }
  }

  private static int[][] counts = {
    { 3, 1 },
    { 4, 3 },
    { 5, 6 },
    { 6, 7 },
    { 7, 6 },
    { 8, 3 },
    { 9, 1 },
  };

  private static void search(Universe u, HashMap<Universe, Long> wins,
    HashMap<Universe, Long> looses) {

    if (u.score1 >= 21) {
      wins.put(u, 1L);
      looses.put(u, 0L);
    } else if (u.score2 >= 21) {
      wins.put(u, 0L);
      looses.put(u, 1L);
    } else {
      long w = 0;
      long l = 0;

      for (int i = 0; i < 7; i++) {
        int newPosition = 1 + (u.pos1 - 1 + counts[i][0]) % 10;
        int newScore = u.score1 + newPosition;
        Universe v = new Universe(u.pos2, u.score2, newPosition, newScore);

        if (!wins.containsKey(v) || !looses.containsKey(v)) {
          search(v, wins, looses);
        }

        w += counts[i][1] * looses.get(v);
        l += counts[i][1] * wins.get(v);
      }

      wins.put(u, w);
      looses.put(u, l);
    }
  }

  public static void main(String[] args) {
    Pattern pattern = Pattern.compile("Player \\d starting position: (\\d)");
    Scanner inputScanner = new Scanner(System.in);

    Matcher m = pattern.matcher(inputScanner.nextLine());
    m.find();
    int p1 = Integer.parseInt(m.group(1));

    m = pattern.matcher(inputScanner.nextLine());
    m.find();
    int p2 = Integer.parseInt(m.group(1));

    inputScanner.close();

    Universe u = new Universe(p1, 0, p2, 0);
    HashMap<Universe, Long> wins1 = new HashMap<Universe, Long>();
    HashMap<Universe, Long> wins2 = new HashMap<Universe, Long>();

    search(u, wins1, wins2);

    System.out.println(wins1.get(u));
    System.out.println(wins2.get(u));
  }
}