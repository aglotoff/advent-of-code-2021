import java.util.Scanner;

/**
 * Read in the series of binary numbers from the standard input and compute the
 * submarine power consumption.
 */
public class PowerConsumption {
  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);

    // Read in the first number to count the number of bits
    String binary = inputScanner.next();
    int numLength = binary.length();

    int[] ones = new int[numLength];
    for (int i = 0; i < numLength; i++) {
      ones[i] = binary.charAt(i) == '1' ? 1 : 0;
    }

    // Read in the rest of the numbers and count the total number of ones
    // in each position.
    int totalNumbers = 1;
    while (inputScanner.hasNext()) {
      binary = inputScanner.next();
      for (int i = 0; i < numLength; i++) {
        if (binary.charAt(i) == '1') {
          ones[i]++;
        }
      }

      totalNumbers++;
    }

    inputScanner.close();

    // Compute the values of the gamma rate and the epsilon rate. Each bit of
    // the gamma rate is determined by the most common bit in the corresponding
    // position of all input numbers. Similarly, the epsilon rate is calculated
    // using the least common bit for each position.
    int gamma = 0;
    int epsilon = 0;
    for (int i = 0; i < numLength; i++) {
      gamma <<= 1;
      epsilon <<= 1;
      if ((ones[i] * 2) >= totalNumbers) {
        gamma |= 1;
      } else {
        epsilon |= 1;
      }
    }

    System.out.println(gamma * epsilon);
  }
}
