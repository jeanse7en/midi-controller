import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {
    public static void main(String[] args) {
        String input = new String("SGH705XC52\n" +
                "SGH737Y6BP\n" +
                "SGH737Y6CE\n" +
                "SGH705XCA2\n" +
                "SGH706XFM4");
        String output = convertToSingleQuote(input);
        System.out.println(output);
    }

    public static String convertToSingleQuote(String input) {
        String replace = input.trim().replace(" ", "").replace("\n",",");
        String[] splits = replace.split(",");
        List<String> doubleQuotes = new ArrayList<>();
        for (String split : splits) {
            doubleQuotes.add("'" + split + "'");
        }
        return doubleQuotes.stream().collect(Collectors.joining(","));
    }
}
