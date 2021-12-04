import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Read in the series of binary numbers from the standard input and compute the
 * submarine life support rating.
 */
public class LifeSupportRating {
  /**
   * Given a list of numbers, determine the most common bit at a paerticular
   * position.
   * 
   * @param values The list of binary numbers represented as strings.
   * @param position The bit position to check.
   * @return The most common bit at this position.
   */
  private static char getMostCommonBit(List<String> values, int position) {
    int onesCount = 0;

    for (String binary: values) {
      if (binary.charAt(position) == '1') {
        onesCount++;
      }
    }

    return ((onesCount * 2) >= values.size()) ? '1' : '0';
  }

  /**
   * Given a list of numbers, determine the least common bit at a paerticular
   * position.
   * 
   * @param values The list of binary numbers represented as strings.
   * @param position The bit position to check.
   * @return The least common bit at this position.
   */
  private static char getLeastCommonBit(List<String> values, int position) {
    int onesCount = 0;

    for (String binary: values) {
      if (binary.charAt(position) == '1') {
        onesCount++;
      }
    }

    return ((onesCount * 2) >= values.size()) ? '0' : '1';
  }

  /**
   * Calculate the oxygen generator rating.
   *
   * @param values The list of binary numbers represented as strings.
   * @return The value of the oxygen generator rating.
   */
  private static int getOxygenGeneratorRating(LinkedList<String> values) {
    int numberLength = values.getFirst().length();

    @SuppressWarnings("unchecked")
    LinkedList<String> workList = (LinkedList<String>) values.clone();

    // For each bit position, discard all values that do not match the most
    // common bit criteria, until only one value remains.
    for (int i = 0; (i < numberLength) && (workList.size() > 1); i++) {
      char mostCommonBit = getMostCommonBit(workList, i);

      Iterator<String> iterator = workList.iterator();
      while (iterator.hasNext()) {
        String binary = iterator.next();
        if ((binary.charAt(i) != mostCommonBit) && (workList.size() > 1)) {
          iterator.remove();
        }
      }
    }

    return Integer.parseInt(workList.getFirst(), 2);
  }

  /**
   * Calculate the CO2 scrubber rating.
   *
   * @param values The list of binary numbers represented as strings.
   * @return The value of the CO2 scrubber rating.
   */
  private static int getCO2ScrubberRating(LinkedList<String> values) {
    int numberLength = values.getFirst().length();

    @SuppressWarnings("unchecked")
    LinkedList<String> workList = (LinkedList<String>) values.clone();

    // For each bit position, discard all values that do not match the least
    // common bit criteria, until only one value remains.
    for (int i = 0; (i < numberLength) && (workList.size() > 1); i++) {
      char leastCommonBit = getLeastCommonBit(workList, i);

      Iterator<String> iterator = workList.iterator();
      while (iterator.hasNext()) {
        String binary = iterator.next();
        if ((binary.charAt(i) != leastCommonBit) && (workList.size() > 1)) {
          iterator.remove();
        }
      }
    }

    return Integer.parseInt(workList.getFirst(), 2);
  }

  public static void main(String[] args) {
    LinkedList<String> values = new LinkedList<String>();

    Scanner inputScanner = new Scanner(System.in);
    while (inputScanner.hasNext()) {
      values.addLast(inputScanner.next());
    }
    inputScanner.close();

    int oxygenGeneratorRating = getOxygenGeneratorRating(values);
    int co2ScrubberRating = getCO2ScrubberRating(values);
    
    // Multiply the two numbers to get the final rating value.
    System.out.println(oxygenGeneratorRating * co2ScrubberRating);
  }
}
