import heyrin.utils.StringUtils;
import org.junit.jupiter.api.Test;

public class StringFormatTest {
    @Test
    public void testSplitString() {
        String input = new String("1048\n" +
                "1051\n" +
                "1054\n" +
                "1057\n" +
                "1243\n" +
                "1246\n" +
                "1249\n" +
                "1252\n" +
                "1531\n" +
                "1534\n" +
                "1561\n" +
                "1564\n" +
                "1567\n" +
                "1570\n" +
                "1606\n" +
                "1609");
        String output = StringUtils.convertToSingleQuote(input);
        System.out.println(output);
    }
}
