package heyrin.service.dto;

import heyrin.dto.ProductStatus;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Data
public class HrBindingProduct {
    private String productLocation;
    private String imageName;
    private Integer index;
    private List<HrBindingProduct> childImages = new ArrayList<>();
    private List<HrBindingProduct> relateProducts = new ArrayList<>();
    private String productCode;
    private String saleContent;
    private String productPrice;
    private Boolean isSale;
    private ProductStatus productStatus;
    private String productDetailHtmlLocation;
    private Integer productId;
}

