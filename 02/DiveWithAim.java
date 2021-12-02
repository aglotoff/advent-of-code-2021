import java.util.Scanner;

/**
 * After following a planned course, multiply the final horizontal position
 * by the final depth. Track the aim value.
 */
public class DiveWithAim {
  public static void main(String[] args) {
    int horizontalPosition = 0;
    int depth = 0;
    int aim = 0;

    Scanner inputScanner = new Scanner(System.in);

    while (inputScanner.hasNext()) {
      String direction = inputScanner.next();
      int units = inputScanner.nextInt();

      if (direction.equals("forward")) {
        horizontalPosition += units;
        depth += aim * units;
      } else if (direction.equals("down")) {
        aim += units;
      } else if (direction.equals("up")) {
        aim -= units;
      }
    }

    inputScanner.close();

    System.out.println(horizontalPosition * depth);
  }
}
