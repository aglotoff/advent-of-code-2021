public class Board {
  private int size;
  private int[][] numbers;
  private boolean[][] marked;

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

  public void markNumber(int num) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (numbers[i][j] == num) {
          marked[i][j] = true;
        }
      }
    }
  }

  private boolean isRowComplete(int row) {
    for (int j = 0; j < size; j++) {
      if (!marked[row][j]) {
        return false;
      }
    }
    return true;
  }

  private boolean isColumnComplete(int col) {
    for (int i = 0; i < size; i++) {
      if (!marked[i][col]) {
        return false;
      }
    }
    return true;
  }

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
