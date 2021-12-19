public class LiteralPacket extends Packet {
  private long value;

  public LiteralPacket(int ver, long val) {
    super(ver, Packet.TYPE_LITERAL);
    value = val;
  }

  @Override
  public int getVersionSum() {
    return getVersion();
  }

  @Override
  public long eval() {
    return value;
  }
}
