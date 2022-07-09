import com.fasterxml.jackson.annotation.JsonProperty;
import heyrin.dto.ProductStatus;
import lombok.Data;

@Data
public class HeyrinDetailProduct {
    @JsonProperty("product_id")
    private Integer id;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("folder_name")
    private String folderName;
    @JsonProperty("img_main_location")
    private String location;
    @JsonProperty("img_detail_location")
    private String imageDetailLocation;
    @JsonProperty("product_price")
    private Integer price;
    @JsonProperty("product_status")
    private ProductStatus productStatus;
}
