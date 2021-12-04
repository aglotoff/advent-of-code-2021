import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Simulation of a bingo subsystem.
 * 
 * Read in an array of random numbers and a set of bingo boards from the
 * standard input and determine which board will win first and which one will
 * be the last.
 * 
 * Output the corresponding board scores.
 */
public class Bingo {
  private static final int BOARD_SIZE = 5;

  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);

    // Read in the list of numbers
    LinkedList<Integer> numbers = new LinkedList<Integer>();
    String line = inputScanner.nextLine();
    for (String num: line.split(",")) {
      numbers.addLast(Integer.parseInt(num));
    }

    // Read in the boards
    LinkedList<Board> boards = new LinkedList<Board>();
    while (inputScanner.hasNextInt()) {
      int[][] grid = new int[BOARD_SIZE][BOARD_SIZE];
      for (int i = 0; i < BOARD_SIZE; i++) {
        for (int j = 0; j < BOARD_SIZE; j++) {
          grid[i][j] = inputScanner.nextInt();
        }
      }

      boards.addLast(new Board(grid));
    }

    inputScanner.close();

    // Determine the order in which each board wins and keep track of the
    // corresponding final scores.
    LinkedList<Integer> winnerScores = new LinkedList<Integer>();
    Iterator<Integer> numberIterator = numbers.iterator();
    while (numberIterator.hasNext() && (boards.size() > 0)) {
      int num = numberIterator.next();
      
      Iterator<Board> boardIterator = boards.iterator();
      while (boardIterator.hasNext()) {
        Board board = boardIterator.next();
        board.markNumber(num);

        if (board.isWinner()) {
          boardIterator.remove();
          winnerScores.addLast(board.getScore() * num);
        }
      }
    }

    System.out.println("First: " + winnerScores.getFirst());
    System.out.println("Last: " + winnerScores.getLast());
  }
}
