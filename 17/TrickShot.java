import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;

public class TrickShot {
  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);
    String line = inputScanner.nextLine();
    inputScanner.close();

    String regex = "target area: " +
      "x=(-?\\d+)\\.\\.(-?\\d+), " +
      "y=(-?\\d+)\\.\\.(-?\\d+)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);
    matcher.find();
    int xmin = Integer.parseInt(matcher.group(1));
    int xmax = Integer.parseInt(matcher.group(2));
    int ymin = Integer.parseInt(matcher.group(3));
    int ymax = Integer.parseInt(matcher.group(4));

    int maxHeight = 0;
    int count = 0;
    for (int vx = 0; vx <= xmax; vx++) {
      // Guaranteed undershoot, increase x
      if ((vx * (vx + 1) / 2) < xmin) {
        continue;
      }

      for (int vy = ymin; vy <= -ymin; vy++) {
        int x = 0;
        int y = 0;
        int dx = vx;
        int dy = vy;

        int height = 0;
        boolean hit = false;
        
        while ((x <= xmax) && (y >= ymin)) {
          if (dy == 0) {
            height = y;
          }

          if ((x >= xmin) && (y <= ymax)) {
            if (!hit) {
              hit = true;
              count++;
            }
            maxHeight = Math.max(maxHeight, height);
          }

          x += dx;
          y += dy;

          if (dx != 0) {
            dx--;
          }
          dy--;
        }
      }
    }

    System.out.println(maxHeight);
    System.out.println(count);
  }
}