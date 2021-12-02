import java.util.Scanner;

/**
 * After following a planned course, multiply the final horizontal position
 * by the final depth.
 */
public class Dive {
  public static void main(String[] args) {
    int horizontalPosition = 0;
    int depth = 0;

    Scanner inputScanner = new Scanner(System.in);

    while (inputScanner.hasNext()) {
      String direction = inputScanner.next();
      int units = inputScanner.nextInt();

      if (direction.equals("forward")) {
        horizontalPosition += units;
      } else if (direction.equals("down")) {
        depth += units;
      } else if (direction.equals("up")) {
        depth -= units;
      }
    }

    inputScanner.close();

    System.out.println(horizontalPosition * depth);
  }
}
