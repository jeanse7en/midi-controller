import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;

import java.io.PrintStream;
import java.math.BigInteger;

public final class IpUtils {


    public static void generateP2pLink(String subnetStr) throws AddressStringException {
        iterateAll(new IPAddressString(subnetStr).toAddress());
    }

    private static void iterateAll(IPAddress subnet) {
        int count = subnet.getCount().intValue();
        for(int i = 0; i < count; i++) {
            System.out.println(subnet.increment(i).withoutPrefixLength().toCanonicalString() + "\t" + subnet.increment(++i).withoutPrefixLength().toCanonicalString());
            switch (subnet.getIPVersion()) {
//                case IPV4:
//                System.out.println(subnet.increment(i).withoutPrefixLength().toCanonicalString() + "\t" + subnet.increment(++i).withoutPrefixLength().toCanonicalString());
//                    break;
//                case IPV6:
//                    System.out.println(subnet.increment(i).withoutPrefixLength().toFullString() + "\t" + subnet.increment(++i).withoutPrefixLength().toFullString());
//                    break;
            }

        }
    }

    public static void iterateSingle(IPAddress subnet) {
        PrintStream out = System.out;
        int count = subnet.getCount().intValue();
        for(IPAddress addr: subnet.getIterable()) {
            System.out.println(addr.toConvertedString().substring(0, addr.toConvertedString().indexOf("/")));
        }
    }
}
