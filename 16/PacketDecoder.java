import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Decoder for Buoyancy Interchange Transmission System (BITS) packets.
 */
public class PacketDecoder {
  /** Binary representation of the packet. */
  private String binary;
  /** Current position in the binary packet representation. */
  private int pos = 0;
  
  /**
   * Read the next n-bit value from the packet.
   *
   * @param length The number of bits to read.
   * @return The value read.
   */
  private int next(int length) {
    String s = binary.substring(pos, pos + length);
    pos = Math.min(pos + length, binary.length());
    return Integer.parseInt(s, 2);
  }

  /**
   * Parse the contents of a literal value packet.
   *
   * @return The value stored in the packet.
   */
  private long parseLiteral() {
    long value = 0;
    int block;

    do {
      block = next(5);
      value = value * 16 + (block & 0xF);
    } while ((block & 0x10) != 0);

    return value;
  }

  /**
   * Parse subpackets of an operator packet.
   *
   * @return The list of subpackets.
   */
  private ArrayList<Packet> parseSubpackets() {
    ArrayList<Packet> packets = new ArrayList<Packet>();

    int lengthId = next(1);
    if (lengthId == 0) {
      int totalBits = next(15);
      int currentPos = pos;

      while (pos - currentPos < totalBits) {
        packets.add(parsePacket());
      }
    } else {
      int totalPackets = next(11);

      for (int i = 0; i < totalPackets; i++) {
        packets.add(parsePacket());
      }
    }

    return packets;
  }

  /**
   * Parse the packet contained in the decoder.
   *
   * @return The decoded packet object.
   */
  public Packet parsePacket() {
    int version = next(3);
    int type = next(3);

    if (type == Packet.TYPE_LITERAL) {
      return new LiteralPacket(version, parseLiteral());
    }

    return new OperatorPacket(version, type, parseSubpackets());
  }

  /**
   * Create a new packet decoder.
   *
   * @param hex The hexadecimal representation of the packet to be decoded.
   */
  public PacketDecoder(String hex) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < hex.length(); i++) {
      switch (hex.charAt(i)) {
        case '0': sb.append("0000"); break;
        case '1': sb.append("0001"); break;
        case '2': sb.append("0010"); break;
        case '3': sb.append("0011"); break;
        case '4': sb.append("0100"); break;
        case '5': sb.append("0101"); break;
        case '6': sb.append("0110"); break;
        case '7': sb.append("0111"); break;
        case '8': sb.append("1000"); break;
        case '9': sb.append("1001"); break;
        case 'A': sb.append("1010"); break;
        case 'B': sb.append("1011"); break;
        case 'C': sb.append("1100"); break;
        case 'D': sb.append("1101"); break;
        case 'E': sb.append("1110"); break;
        case 'F': sb.append("1111"); break;
      }
    }

    binary = sb.toString();
  }
  
  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);
    String data = inputScanner.nextLine();
    inputScanner.close();

    PacketDecoder pd = new PacketDecoder(data);
    Packet p = pd.parsePacket();
    System.out.println(p.getVersionSum());
    System.out.println(p.eval());
  }
}
