import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Helper class to model a cave system.
 */
public class CaveSystem {
  /** Adjacency lists for all caves. */
  private HashMap<String, LinkedList<String>> adj;

  /**
   * Create a cave system model.
   */
  public CaveSystem() {
    adj = new HashMap<String, LinkedList<String>>();
  }

  /**
   * Add the cave u to the list of adjacent caves for v.
   * 
   * @param v The name of the cave to add to.
   * @param u The name of the cave to be added.
   */
  private void addAdjacentCave(String v, String u) {
    if (!adj.containsKey(v)) {
      adj.put(v, new LinkedList<String>());
    }

    LinkedList<String> caves = adj.get(v);
    caves.add(u);
  }

  /**
   * Return the list of adjacent caves for the given cave.
   *
   * @param v The name of the cave.
   * @return The list of adjacent caves.
   */
  public List<String> getAdjacentCaves(String v) {
    return adj.get(v);
  }

  /**
   * Connect two caves.
   *
   * @param v The name of the first cave.
   * @param u The name of the second cave.
   */
  public void connectCaves(String v, String u) {
    addAdjacentCave(v, u);
    addAdjacentCave(u, v);
  }
}
