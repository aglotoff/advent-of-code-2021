import java.util.Scanner;

/**
 * Submarine navigation simulation.
 *
 * Read a series of commands from the standard input and follow the planned
 * course. Track the current horizontal position, depth, and aim value.
 *
 * Output the result of multiplying the final horizontal position and the final
 * depth.
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
