import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class DiracDice {
  private static class Player {
    int position;
    int score;

    Player(int pos) {
      position = pos;
    }

    void move(int steps) {
      position = 1 + (position - 1 + steps) % 10;
      score += position;
    }
  }
  public static void main(String[] args) {
    Player[] players = new Player[2];

    Pattern pattern = Pattern.compile("Player \\d starting position: (\\d)");
    Scanner inputScanner = new Scanner(System.in);

    Matcher m = pattern.matcher(inputScanner.nextLine());
    m.find();
    players[0] = new Player(Integer.parseInt(m.group(1)));

    m = pattern.matcher(inputScanner.nextLine());
    m.find();
    players[1] = new Player(Integer.parseInt(m.group(1)));

    inputScanner.close();

    int dice = 1;
    int p = 1;
    int rolls = 0;
    while (players[p].score < 1000) {
      p = (p + 1) % players.length;

      int steps = 0;
      for (int i = 0; i < 3; i++) {
        steps += dice;
        dice = 1 + (dice % 100);
        rolls++;
      }
      players[p].move(steps);
    }

    System.out.println(players[(p + 1) % 2].score * rolls);
  }
}