import java.util.ArrayList;
import java.util.Scanner;

public class SnailfishNumber {
  private static abstract class Node {
    public abstract void addLeft(int v);
    public abstract void addRight(int v);
    public abstract int depth();
    public abstract long magnitude();
    public abstract Node clone();
  }

  private static class Pair extends Node {
    private Node left;
    private Node right;

    public Pair(Node l, Node r) {
      left = l;
      right = r;
    }

    @Override
    public String toString() {
      return "[" + left.toString() + "," + right.toString() + "]";
    }

    public ArrayList<Integer> explode(int depth) {
      ArrayList<Integer> ret = new ArrayList<Integer>();

      if (left instanceof Number && right instanceof Number) {
        if (depth > 4) {
          ret.add(((Number) left).value);
          ret.add(((Number) right).value);
          return ret;
        } else {
          return null;
        }
      }

      Integer leftNumber = null;
      Integer rightNumber = null;

      if (left instanceof Pair) {
        Pair leftPair = (Pair) left;
        ArrayList<Integer> explodeResult = leftPair.explode(depth + 1);
        if (explodeResult != null) {
          if (explodeResult.get(0) != null) {
            leftNumber = explodeResult.get(0);
          }
          if (explodeResult.get(1) != null) {
            right.addLeft(explodeResult.get(1));
          }
        }

        if (depth == 4) {
          left = new Number(0);
        }
      }

      if (right instanceof Pair) {
        Pair rightPair = (Pair) right;
        ArrayList<Integer> explodeResult = rightPair.explode(depth + 1);
        if (explodeResult != null) {
          if (explodeResult.get(0) != null) {
            left.addRight(explodeResult.get(0));
          }
          if (explodeResult.get(1) != null) {
            rightNumber = explodeResult.get(1);
          }
        }

        if (depth == 4) {
          right = new Number(0);
        }
      }

      ret.add(leftNumber);
      ret.add(rightNumber);
      return ret;
    }

    public void addLeft(int v) {
      left.addLeft(v);
    }

    public void addRight(int v) {
      right.addRight(v);
    }

    public int depth() {
      return 1 + Math.max(left.depth(), right.depth());
    }

    public void reduce() {
      for (;;) {
        while (depth() > 4) {
          explode(1);
        };

        if (!split()) {
          break;
        }
      }
    }

    public boolean split() {
      if (left instanceof Number) {
        int val = ((Number) left).value;
        if (val >= 10) {
          left = new Pair(new Number(val / 2), new Number(val - val / 2));
          return true;
        }
      } else if (left instanceof Pair) {
        if (((Pair) left).split()) {
          return true;
        }
      }

      if (right instanceof Number) {
        int val = ((Number) right).value;
        if (val >= 10) {
          right = new Pair(new Number(val / 2), new Number(val - val / 2));
          return true;
        }
      } else if (right instanceof Pair) {
        if (((Pair) right).split()) {
          return true;
        }
      }

      return false;
    }

    public long magnitude() {
      return 3 * left.magnitude() + 2 * right.magnitude();
    }

    public Node clone() {
      return new Pair(left.clone(), right.clone());
    }
  }

  private static class Number extends Node {
    private int value;

    public Number(int v) {
      value = v;
    }

    @Override
    public String toString() {
      return Integer.toString(value);
    }

    public Node clone() {
      return new Number(value);
    }

    public void addLeft(int v) {
      value += v;
    }

    public void addRight(int v) {
      value += v;
    }

    public int depth() {
      return 0;
    }

    public long magnitude() {
      return value;
    }
  }

  private static class NumberParser {
    private String s;
    private int pos;

    public NumberParser(String input) {
      s = input;
      pos = 0;
    }

    private char nextChar() {
      return s.charAt(pos);
    }

    private char getChar() {
      return s.charAt(pos++);
    }

    public Number parseNumber() {
      return new Number(getChar() - '0');
    }

    public Pair parsePair() {     
      getChar();  // '['
      Node l = parseNode();
      getChar();  // ','
      Node r = parseNode();
      getChar();  // ']'
      return new Pair(l, r);
    }

    public Node parseNode() {
      if (nextChar() == '[') {
        return parsePair();
      }
      return parseNumber();
    }
  }

  public static void main(String[] args) {
    ArrayList<Pair> list = new ArrayList<Pair>();

    Scanner inputScanner = new Scanner(System.in);
    while (inputScanner.hasNextLine()) {
      NumberParser parser = new NumberParser(inputScanner.nextLine());
      list.add(parser.parsePair());
    }
    inputScanner.close();

    Pair sum = list.get(0);
    for (int i = 1; i < list.size(); i++) {
      sum = new Pair(sum.clone(), list.get(i).clone());
      sum.reduce();
    }
    System.out.println(sum.magnitude());

    long maxMagnitude = 0;
    for (int i = 0; i < list.size(); i++) {
      for (int j = 0; j < list.size(); j++) {
        if (i == j) {
          continue;
        }

        // System.out.println("  " + list.get(i));
        // System.out.println("+ " + list.get(j));
        Pair p = new Pair(list.get(i).clone(), list.get(j).clone());
        p.reduce();
        // System.out.println("= " + list.get(j));
        maxMagnitude = Math.max(maxMagnitude, p.magnitude());
      }
      // System.out.println(list.get(i));
    }
    System.out.println(maxMagnitude);
  }
}