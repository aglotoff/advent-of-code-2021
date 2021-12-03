import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Compute the submarine life support rating.
 */
public class LifeSupportRating {
  private static char getMostCommonBit(List<String> list, int position) {
    int bitCount = 0;
    for (String binary: list) {
      if (binary.charAt(position) == '1') {
        bitCount++;
      }
    }

    return (bitCount * 2 >= list.size()) ? '1' : '0';
  }

  private static char getLeastCommonBit(List<String> list, int position) {
    int bitCount = 0;
    for (String binary: list) {
      if (binary.charAt(position) == '1') {
        bitCount++;
      }
    }

    return (bitCount * 2 >= list.size()) ? '0' : '1';
  }

  private static int getOxygenGeneratorRating(LinkedList<String> allNumbers) {
    int numberLength = allNumbers.getFirst().length();

    @SuppressWarnings("unchecked")
    LinkedList<String> workList = (LinkedList<String>) allNumbers.clone();

    for (int i = 0; (i < numberLength) && (workList.size() > 1); i++) {
      char mostCommonBit = getMostCommonBit(workList, i);

      Iterator<String> iterator = workList.iterator();
      while (iterator.hasNext()) {
        String binary = iterator.next();
        if ((binary.charAt(i) != mostCommonBit) && workList.size() > 1) {
          iterator.remove();
        }
      }
    }

    return Integer.parseInt(workList.getFirst(), 2);
  }

  private static int getCO2ScrubberRating(LinkedList<String> allNumbers) {
    int numberLength = allNumbers.getFirst().length();

    @SuppressWarnings("unchecked")
    LinkedList<String> workList = (LinkedList<String>) allNumbers.clone();

    for (int i = 0; (i < numberLength) && (workList.size() > 1); i++) {
      char leastCommonBit = getLeastCommonBit(workList, i);

      Iterator<String> iterator = workList.iterator();
      while (iterator.hasNext()) {
        String binary = iterator.next();
        if ((binary.charAt(i) != leastCommonBit) && workList.size() > 1) {
          iterator.remove();
        }
      }
    }

    return Integer.parseInt(workList.getFirst(), 2);
  }

  public static void main(String[] args) {
    LinkedList<String> allNumbers = new LinkedList<String>();

    Scanner inputScanner = new Scanner(System.in);
    while (inputScanner.hasNext()) {
      allNumbers.addLast(inputScanner.next());
    }
    inputScanner.close();

    int oxygenGeneratorRating = getOxygenGeneratorRating(allNumbers);
    int co2ScrubberRating = getCO2ScrubberRating(allNumbers);
    
    System.out.println(oxygenGeneratorRating * co2ScrubberRating);
  }
}
