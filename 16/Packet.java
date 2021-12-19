/**
 * Common base for all packets.
 */
abstract class Packet {
  public static final int TYPE_LITERAL = 4;
  public static final int TYPE_SUM = 0;
  public static final int TYPE_PRODUCT = 1;
  public static final int TYPE_MIN = 2;
  public static final int TYPE_MAX = 3;
  public static final int TYPE_GT = 5;
  public static final int TYPE_LT = 6;
  public static final int TYPE_EQ = 7;

  private int version;
  private int type;

  /**
   * Construct a packet object.
   *
   * @param ver The packet version.
   * @param t The packet type.
   */
  public Packet(int ver, int t) {
    version = ver;
    type = t;
  }

  /**
   * Get the packet version number.
   *
   * @return The version number.
   */
  public int getVersion() {
    return version;
  }

   /**
   * Get the packet type ID.
   *
   * @return The type ID.
   */
  public int getType() {
    return type;
  }

  /**
   * 
   * @return The version sum for this packets and any subpackets.
   */
  abstract public int getVersionSum();

  /**
   * 
   * @return The evaluation result.
   */
  abstract public long eval();
}
