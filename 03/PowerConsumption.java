import java.util.Scanner;

/**
 * Compute the submarine power consumption.
 */
public class PowerConsumption {
  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);

    String binary = inputScanner.next();
    int numLength = binary.length();

    int[] bitCounts = new int[numLength];
    for (int i = 0; i < numLength; i++) {
      bitCounts[i] = binary.charAt(i) == '1' ? 1 : 0;
    }

    int totalNumbers = 1;
    while (inputScanner.hasNext()) {
      binary = inputScanner.next();
      for (int i = 0; i < numLength; i++) {
        if (binary.charAt(i) == '1') {
          bitCounts[i]++;
        }
      }

      totalNumbers++;
    }

    inputScanner.close();

    int gamma = 0;
    int epsilon = 0;
    for (int i = 0; i < numLength; i++) {
      gamma <<= 1;
      epsilon <<= 1;
      if (bitCounts[i] > totalNumbers / 2) {
        gamma |= 1;
      } else {
        epsilon |= 1;
      }
    }

    System.out.println(gamma * epsilon);
  }
}