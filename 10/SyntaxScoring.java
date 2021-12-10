import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Calculate syntax scores.
 * 
 * Read in a sequence of lines from the standard input. Each line contains
 * (possibly nested) chunks. Every chunk opens and closes with one of four
 * legal pairs of matching characters: '(' and ')', '[' and ']', '{' and '}',
 * or '<' and '>'.
 * 
 * For each corrupted line (i.e. line containing a chunk that closes with a
 * wrong character), compute the error score. Output the total syntax error
 * score.
 * 
 * For each incomplete line figure out the sequence of closing characters and
 * compute the completion string score. Sort the resulting scores and output
 * the middle score.
 */
public class SyntaxScoring {
  /**
   * Get error score for a single character.
   *
   * @param c The character.
   * @return The number of error points for this character.
   */
  private static int getCharErrorScore(char c) {
    switch (c) {
      case ')':
        return 3;
      case ']':
        return 57;
      case '}':
        return 1197;
      case '>':
        return 25137;
      default:
        return 0;
    }
  }

  /**
   * Compute the completion string score.
   *
   * @param closingChars The sequence of closing characters.
   * @return The corresponding completion string score.
   */
  private static long getCompletionStringScore(List<Character> closingChars) {
    long score = 0;
    for (char c: closingChars) {
      switch (c) {
        case ')':
          score = score * 5 + 1;
          break;
        case ']':
          score = score * 5 + 2;
          break;
        case '}':
          score = score * 5 + 3;
          break;
        case '>':
          score = score * 5 + 4;
          break;
      }
    }
    return score;
  }

  public static void main(String[] args) {
    int errorScore = 0;

    ArrayList<Long> scores = new ArrayList<Long>();

    Scanner inputScanner = new Scanner(System.in);
    inputLoop: while (inputScanner.hasNextLine()) {
      char[] line = inputScanner.nextLine().toCharArray();

      LinkedList<Character> closingChars = new LinkedList<Character>();

      for (char c: line) {
        switch (c) {
          case '(':
            closingChars.addFirst(')');
            break;
          case '[':
            closingChars.addFirst(']');
            break;
          case '{':
            closingChars.addFirst('}');
            break;
          case '<':
            closingChars.addFirst('>');
            break;
          case ')':
          case ']':
          case '}':
          case '>':
            // Stop at the first illegal character
            if (closingChars.removeFirst() != c) {
              errorScore += getCharErrorScore(c);
              continue inputLoop;
            }
            break;
        }
      }

      if (!closingChars.isEmpty()) {
        scores.add(getCompletionStringScore(closingChars));
      }
    }
    inputScanner.close();

    System.out.println(errorScore);

    Collections.sort(scores);
    System.out.println(scores.get(scores.size() / 2));
  }
}
