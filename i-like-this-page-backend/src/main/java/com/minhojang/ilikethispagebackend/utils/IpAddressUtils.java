package src.main.java.com.minhojang.ilikethispagebackend.utils;

public class IpAddressUtils {

  public static long ipv4ToLong(String ipAddressV4) {
    long result = 0;
    String[] ipAddressInArray = ipAddressV4.split("\\.");

    for (int i = 3; i >= 0; i--) {
      long ip = Long.parseLong(ipAddressInArray[3 - i]);
      result |= ip << (i * 8);
    }

    return result;
  }

  public static String longToIpv4(long ipv4) {
    StringBuilder result = new StringBuilder(15);

    for (int i = 0; i < 4; i++) {
      result.insert(0, (ipv4 & 0xff));
      if (i < 3) {
        result.insert(0, '.');
      }

      ipv4 = ipv4 >> 8;
    }
    return result.toString();
  }
}
