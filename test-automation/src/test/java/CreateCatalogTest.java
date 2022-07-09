import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import heyrin.utils.DataHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CreateCatalogTest {
    public static final String HOME_UBUNTU_DOCUMENTS_HEY_RIN_STORE_IMAGES = "/home/ubuntu/Documents/hey-rin-store/images/";

    @Test
    public void createProductCatalog() throws IOException {

        String defaultText = String.format("<div class=\"col-4 pro\">\n" +
                "                 <a href=\"<detail_url>\">\n" +
                "                <img class=\"<product_status>\" src=\"<location>\">\n" +
                "                <div class=\"des\">\n" +
                "                    <span>Heyrin</span>\n" +
                "                    <h5><product_code></h5>\n" +
                "                    <i class=\"fa fa-tag tag-price\" aria-hidden=\"true\">\n" +
                "                        <span class=\"tag-price\"> <product_price>k</span>\n" +
                "                        <span class=\"<product_status>\"></span>\n" +
                "                    </i>\n" +
                "                   \n" +
                "                </div>\n" +
                "            </div>");
        List<HeyrinDetailProduct> heyrinDetailProducts = getPayLoadProducts();
        StringBuilder output = new StringBuilder();
        for (HeyrinDetailProduct heyrinDetailProduct : heyrinDetailProducts) {
            output.append(defaultText
                    .replace("<location>", String.format("../images/%s/%s", heyrinDetailProduct.getFolderName(), heyrinDetailProduct.getLocation()))
                    .replace("<product_code>", heyrinDetailProduct.getProductCode())
                    .replace("<product_price>", heyrinDetailProduct.getPrice().toString())
                    .replace("<product_status>", heyrinDetailProduct.getProductStatus().getStyleClass())
                    .replace("<detail_url>", String.format("../images/%s/productDetail.html", heyrinDetailProduct.getFolderName()))
                );
        }
        System.out.println(output);
    }

    @Test
    public void createProductDetail() throws IOException {
        List<HeyrinDetailProduct> heyrinDetailProducts = getPayLoadProducts();
        String detailImage = new String(Files.readAllBytes(Paths.get("./src/test/java/productDetailElement.html")));
        String productDetail = new String(Files.readAllBytes(Paths.get("./src/test/java/productDetail.html")));

        for (HeyrinDetailProduct heyrinDetailProduct : heyrinDetailProducts) {
            List<String> detailImageLocations = DataHelper.asString(heyrinDetailProduct.getImageDetailLocation(), "\n");
            StringBuilder output = new StringBuilder();
            Path detailHtmlFile = Paths.get(String.format(HOME_UBUNTU_DOCUMENTS_HEY_RIN_STORE_IMAGES + "%s/%s", heyrinDetailProduct.getFolderName(), "/productDetail.html"));
            for (String detailImageLocation : detailImageLocations) {
                output.append(detailImage.replace("<image_detail>",
                        String.format(detailImageLocation)));
            }
            String productDetailHtml = productDetail.replace("<image-detail/>", output.toString())
                    .replace("<main_image>", heyrinDetailProduct.getLocation())
                    .replace("<product_code/>", heyrinDetailProduct.getProductCode())
                    .replace("<product_price/>", heyrinDetailProduct.getPrice().toString());
            Files.write(detailHtmlFile, productDetailHtml.getBytes());
        }
    }

    private List<HeyrinDetailProduct> getPayLoadProducts() throws IOException {
        ObjectMapper gson = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String payload = new String(Files.readAllBytes(Paths.get("./src/test/java/payload.txt")));
//        List<HeyrinDetailProduct> heyrinDetailProducts = gson.readValue(payload, new TypeReference<List<HeyrinDetailProduct>>() {});
        return null;
    }
}
