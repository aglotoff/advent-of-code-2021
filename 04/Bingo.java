import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Bingo {
  private static final int BOARD_SIZE = 5;

  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);

    LinkedList<Integer> numbers = new LinkedList<Integer>();

    String line = inputScanner.nextLine();
    for (String num: line.split(",")) {
      numbers.addLast(Integer.parseInt(num));
    }

    LinkedList<Board> boards = new LinkedList<Board>();

    while (inputScanner.hasNextInt()) {
      int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
      for (int i = 0; i < BOARD_SIZE; i++) {
        for (int j = 0; j < BOARD_SIZE; j++) {
          board[i][j] = inputScanner.nextInt();
        }
      }

      boards.addLast(new Board(board));
    }

    inputScanner.close();

    LinkedList<Integer> scores = new LinkedList<Integer>();

    Iterator<Integer> numberIterator = numbers.iterator();
    while (numberIterator.hasNext() && (boards.size() > 0)) {
      int num = numberIterator.next();
      
      Iterator<Board> boardIterator = boards.iterator();
      while (boardIterator.hasNext()) {
        Board board = boardIterator.next();
        board.markNumber(num);

        if (board.isWinner()) {
          boardIterator.remove();

          scores.addLast(board.getScore() * num);
        }
      }
    }

    System.out.println("First: " + scores.getFirst());
    System.out.println("Last: " + scores.getLast());
  }
}
