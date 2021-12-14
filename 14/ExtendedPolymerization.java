import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Find the optimal polymer formula.
 *
 * Read in a polymer template and a list of pair insertion rules from standard
 * input. Repeat the pair insertion process the required number of times and
 * find the most and least common elements in the result. Output the difference
 * between the quantity of the most common element and the quantity of the
 * least common element. The number of steps is passed as a command-line
 * argument.
 */
public class ExtendedPolymerization {
  public static void main(String[] args) {
    int steps = Integer.parseInt(args[0]);

    Pattern pattern = Pattern.compile("([A-Z]+)\\s+->\\s+([A-Z])");
    HashMap<String, Character> rules = new HashMap<String, Character>();
    
    Scanner inputScanner = new Scanner(System.in);

    // Read in the template
    String template = inputScanner.nextLine();

    // Read in the pair insertion rules
    while (inputScanner.hasNextLine()) {
      Matcher m = pattern.matcher(inputScanner.nextLine());
      if (m.find()) {
        rules.put(m.group(1), m.group(2).charAt(0));
      }
    }

    inputScanner.close();

    // For each element, count the number of times it appears in the result.
    HashMap<Character, Long> elemCount = new HashMap<Character, Long>();
    for (int i = 0; i < template.length(); i++) {
      char c = template.charAt(i);
      elemCount.put(c, elemCount.containsKey(c) ? elemCount.get(c) + 1 : 1);
    }

    // Instead of storing the whole polymer, store all different pairs of
    // elements and the number of times each pair appears in the result.
    HashMap<String, Long> pairs = new HashMap<String, Long>();
    for (int i = 0; i < template.length() - 1; i++) {
      String pair = template.substring(i, i + 2);
      pairs.put(pair, pairs.containsKey(pair) ? pairs.get(pair) + 1 : 1);
    }

    // On each step, count the number of elements to be added and the number of
    // times each pair appears in the new polymer.
    for (int t = 0; t < steps; t++) {
      HashMap<String, Long> newPairs = new HashMap<String, Long>();
      for (String pair: pairs.keySet()) {
        char c = rules.get(pair);
        String l = "" + pair.charAt(0) + c;
        String r = "" + c + pair.charAt(1);
        long n = pairs.get(pair);

        elemCount.put(c, elemCount.containsKey(c) ? elemCount.get(c) + n : n);
        newPairs.put(l, newPairs.containsKey(l) ? newPairs.get(l) + n : n);
        newPairs.put(r, newPairs.containsKey(r) ? newPairs.get(r) + n : n);
      }
      pairs = newPairs;
    }

    Long[] values = elemCount.values().toArray(new Long[elemCount.size()]);
    Arrays.sort(values);

    System.out.println(values[values.length - 1] - values[0]);
  } 
}
