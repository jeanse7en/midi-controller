import heyrin.utils.IpUtils;
import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import org.junit.jupiter.api.Test;

public class IpGenerateTest {
    @Test
    public void generateP2pLink() throws AddressStringException {
        IpUtils.generateP2pLink("10.112.128.0/24");
    }


    @Test
    public void generateRouterIp() throws AddressStringException {
        IPAddress ipAddress = new IPAddressString("10.112.128.0/24").toAddress();
        IpUtils.iterateSingle(ipAddress);
    }

    @Test
    public void generateIpDoubleStep() throws AddressStringException {
        IPAddress ipAddress = new IPAddressString("10.131.0.0/17").toAddress();
//        IPAddress maxAddress = new IPAddressString("FD00:0:3000:0::f0/127").toAddress();
        IPAddress counter = ipAddress;
        Integer step = 14;
        while (step > 0) {
            System.out.println(counter.toCanonicalString());
            counter = counter.increment(8);
            step = step - 1;
        }
    }
    @Test
    public void increaseType5Vni() throws AddressStringException {
        for (int i = 3900000; i <= 3900999 ; i ++) {
            System.out.println(i);
        }
    }
}
