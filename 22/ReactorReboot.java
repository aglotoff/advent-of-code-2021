import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Scanner;

public class ReactorReboot {
  private static class Cube {
    long x0;
    long x1;
    long y0;
    long y1;
    long z0;
    long z1;
    int sign;

    public Cube(long x0, long x1, long y0, long y1, long z0, long z1, int s) {
      this.x0 = x0;
      this.x1 = x1;
      this.y0 = y0;
      this.y1 = y1;
      this.z0 = z0;
      this.z1 = z1;
      this.sign = s;
    }

    public long getVolume() {
      return sign * (x1 - x0) * (y1 - y0) * (z1 - z0);
    }

    public static Cube intersection(Cube a, Cube b) {
      if ((a.x1 <= b.x0) || (a.x0 >= b.x1)) {
        return null;
      }
      if ((a.y1 <= b.y0) || (a.y0 >= b.y1)) {
        return null;
      }
      if ((a.z1 <= b.z0) || (a.z0 >= b.z1)) {
        return null;
      }

      return new Cube(Math.max(a.x0, b.x0),  Math.min(a.x1, b.x1),
        Math.max(a.y0, b.y0),  Math.min(a.y1, b.y1),
        Math.max(a.z0, b.z0),  Math.min(a.z1, b.z1),
        a.sign * b.sign * (-1));
    }
  }

  public static void main(String[] args) {
    Pattern pattern = Pattern.compile("(on|off) x=(-?\\d+)\\.\\.(-?\\d+)," +
      "y=(-?\\d+)\\.\\.(-?\\d+),z=(-?\\d+)\\.\\.(-?\\d+)");

    Scanner inputScanner = new Scanner(System.in);

    ArrayList<Cube> cubes = new ArrayList<Cube>();

    while (inputScanner.hasNextLine()) {
      Matcher m = pattern.matcher(inputScanner.nextLine());
      if (m.find()) {
        boolean on = m.group(1).equals("on");
        int x0 = Integer.parseInt(m.group(2));
        int x1 = Integer.parseInt(m.group(3)) + 1;
        int y0 = Integer.parseInt(m.group(4));
        int y1 = Integer.parseInt(m.group(5)) + 1;
        int z0 = Integer.parseInt(m.group(6));
        int z1 = Integer.parseInt(m.group(7)) + 1;

        Cube cube = new Cube(x0, x1, y0, y1, z0, z1, 1);
        int ncubes = cubes.size();

        if (on) {
          cubes.add(cube);
        }

        for (int i = 0; i < ncubes; i++) {
          Cube c = Cube.intersection(cubes.get(i), cube);
          if (c != null) {
            cubes.add(c);
          }
        }
      }
    }

    inputScanner.close();

    long sum = 0;
    for (Cube c: cubes) {
      sum += c.getVolume();
    }

    System.out.println(sum);
  }
}
