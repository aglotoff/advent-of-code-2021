import java.util.List;

public class OperatorPacket extends Packet {
  private List<Packet> packets;

  public OperatorPacket(int ver, int t, List<Packet> p) {
    super(ver, t);
    packets = p;
  }

  @Override
  public int getVersionSum() {
    int sum = getVersion();
    for (Packet p : packets) {
      sum += p.getVersionSum();
    }
    return sum;
  }

  @Override
  public long eval() {
    long result;

    switch (getType()) {
      case Packet.TYPE_SUM:
        result = packets.get(0).eval();
        for (int i = 1; i < packets.size(); i++) {
          result += packets.get(i).eval();
        }
        return result;

      case Packet.TYPE_PRODUCT:
        result = packets.get(0).eval();
        for (int i = 1; i < packets.size(); i++) {
          result *= packets.get(i).eval();
        }
        return result;

      case Packet.TYPE_MIN:
        result = packets.get(0).eval();
        for (int i = 1; i < packets.size(); i++) {
          result = Math.min(result, packets.get(i).eval());
        }
        return result;

      case Packet.TYPE_MAX:
        result = packets.get(0).eval();
        for (int i = 1; i < packets.size(); i++) {
          result = Math.max(result, packets.get(i).eval());
        }
        return result;

      case Packet.TYPE_GT:
        return packets.get(0).eval() > packets.get(1).eval() ? 1 : 0;

      case Packet.TYPE_LT:
        return packets.get(0).eval() < packets.get(1).eval() ? 1 : 0;

      case Packet.TYPE_EQ:
        return packets.get(0).eval() == packets.get(1).eval() ? 1 : 0;

      default:
        return 0;
    }
  }
}
