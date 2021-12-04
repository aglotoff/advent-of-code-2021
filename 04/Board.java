/**
 * Bingo board.
 */
public class Board {
  /** Board size. */
  private int size;
  /** Grid to store the numbers. */
  private int[][] numbers;
  /** Grid to keep track which numbers are marked. */
  private boolean[][] marked;

  /**
   * Construct the bingo board.
   * 
   * @param n The grid containing the numbers.
   */
  public Board(int[][] n) {
    size = n.length;

    numbers = new int[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        numbers[i][j] = n[i][j];
      }
    }

    marked = new boolean[size][size];
  }

  /**
   * Find the given number on the board and mark it.
   *
   * @param num The number to be marked.
   */
  public void markNumber(int num) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (numbers[i][j] == num) {
          marked[i][j] = true;
        }
      }
    }
  }

  /**
   * Check whether the given row is complete.
   *
   * @param row The row number.
   * @return true if the row is complete, false otherwise.
   */
  private boolean isRowComplete(int row) {
    for (int j = 0; j < size; j++) {
      if (!marked[row][j]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check whether the given column is complete.
   *
   * @param col The column number.
   * @return true if the column is complete, false otherwise.
   */
  private boolean isColumnComplete(int col) {
    for (int i = 0; i < size; i++) {
      if (!marked[i][col]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check whether the board has a complete column or a complete row.
   *
   * @return true if the board wins, false otherwise.
   */
  public boolean isWinner() {
    for (int row = 0; row < size; row++) {
      if (isRowComplete(row)) {
        return true;
      }
    }

    for (int col = 0; col < size; col++) {
      if (isColumnComplete(col)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Computes the board score as the sum of all unmarked numbers.
   *
   * @return The computed score.
   */
  public int getScore() {
    int sum = 0;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (!marked[i][j]) {
          sum += numbers[i][j];
        }
      }
    }
    return sum;
  }
}
