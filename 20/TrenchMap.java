import java.util.ArrayList;
import java.util.Scanner;

/**
 * Image enhancement algorithm.
 * 
 * Read in the an algorithm string and the initial image from the standard
 * input and enhance the image the required number of times. The number of
 * steps is passed as a command-line argument. Output the total number of lit
 * pixels in the resulting image.
 */
public class TrenchMap {
  // Helper array to generate coordinates for 9 pixels to be considered for
  // each pixel to be converted. 
  private static final int[][] dir = {
    { -1, -1 },
    { -1, -2 },
    { -1, -3 },
    { -2, -1 },
    { -2, -2 },
    { -2, -3 },
    { -3, -1 },
    { -3, -2 },
    { -3, -3 },
  };

  public static void main(String[] args) {
    int steps = Integer.parseInt(args[0]);

    Scanner inputScanner = new Scanner(System.in);

    // Read in the image enhancement algorithm string, followed by an empty
    // line.
    String algorithm = inputScanner.nextLine();
    inputScanner.nextLine();

    // Read in the input image.
    ArrayList<String> lines = new ArrayList<String>();
    while (inputScanner.hasNextLine()) {
      lines.add(inputScanner.nextLine());
    }

    inputScanner.close();

    // Generate the initial image bitmap.
    char[][] image = new char[lines.size()][];
    for (int i = 0; i < lines.size(); i++) {
      image[i] = lines.get(i).toCharArray();
    }

    // Whether the pixels outside the current boundary are lit?
    char outsidePixel = '.';

    for (int t = 0; t < steps; t++) {
      // On each step, the boundary needs to be increased by 2 for each side to
      // include pixels that will be affected by picels in the old boundary.
      // All other pixels don't depend on the contents of the old boundary.
      char[][] result = new char[image.length + 4][image.length + 4];

      for (int i = 0; i < result.length; i++) {
        for (int j = 0; j < result[i].length; j++) {
          int bitmap = 0;
          for (int k = 0; k < 9; k++) {
            int y = i + dir[k][0];
            int x = j + dir[k][1];

            if (y < 0 || y >= image.length || x < 0 || x >= image.length) {
              if (outsidePixel == '#') {
                bitmap |= (1 << k);
              }
            } else if (image[y][x] == '#') {
              bitmap |= (1 << k);
            }
          }

          result[i][j] = algorithm.charAt(bitmap);
        }
      }

      // For all pixels outside the new boundary, the value will always be
      // either 0 or 511.
      outsidePixel = algorithm.charAt(outsidePixel == '.' ? 0 : 511);

      image = result;
    }

    int count = 0;
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        if (image[i][j] == '#') {
          count++;
        }
      }
    }
    System.out.println(count);
  }
}
