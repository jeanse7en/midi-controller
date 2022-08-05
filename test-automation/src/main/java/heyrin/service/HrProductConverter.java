package heyrin.service;

import heyrin.repository.entity.*;
import heyrin.service.dto.HrBindingProduct;
import heyrin.service.dto.ImagePurpose;
import heyrin.utils.DataHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Service
public class HrProductConverter {



    @Transactional(readOnly = true)
    public Integer getProductIndex(HrProduct hrProduct) {
        if (CollectionUtils.isEmpty(hrProduct.getHrProductArrangements())) {
            return Integer.MAX_VALUE;
        }

        return hrProduct.getHrProductArrangements().stream()
                .findAny().map(HrProductArrangement::getOrder).orElse(Integer.MAX_VALUE);
    }

    public HrBindingProduct convertProductCommon(HrProduct hrProduct) {

        try {
            List<HrProductImageAssignment> mainProductImages = DataHelper.getFieldList(hrProduct.getHrProductImageAssignments(), Function.identity(), e -> e.getPurpose().equals(ImagePurpose.MAIN));
            Assert.isTrue(CollectionUtils.isNotEmpty(mainProductImages), "Product must have main picture");
            HrProductImage hrProductImage = mainProductImages.get(DataHelper.FIRST_INDEX).getHrProductImage();

            HrBindingProduct hrSaleBindingProduct = new HrBindingProduct();
            hrSaleBindingProduct.setProductId(hrProduct.getId());
            hrSaleBindingProduct.setProductCode(hrProduct.getCode());
            hrSaleBindingProduct.setProductLocation(String.format("%s/%s", hrProduct.getFolder(), hrProductImage.getLocation()));
            hrSaleBindingProduct.setProductDetailHtmlLocation(String.format("%s/productDetail.html", hrProduct.getFolder()));
            hrSaleBindingProduct.setProductPrice(hrProduct.getHrProductPrices().get(0).getPrice().intValue() + "");
            hrSaleBindingProduct.setProductStatus(hrProduct.getStatus());
            hrSaleBindingProduct.setImageName(hrProductImage.getLocation());
            hrSaleBindingProduct.setIndex(getProductIndex(hrProduct));

            List<HrProductPromotionAssignment> promotionAssignments = DataHelper.getFieldList(hrProduct.getHrProductPromotionAssignments(), Function.identity(), e -> e.getDeprecatedTime() == null);
            if (CollectionUtils.isNotEmpty(promotionAssignments)) {
                hrSaleBindingProduct.setIsSale(true);
            } else {
                hrSaleBindingProduct.setIsSale(false);
            }
            hrSaleBindingProduct = createProductDetail(hrProduct, hrSaleBindingProduct);
            return hrSaleBindingProduct;
        } catch (Exception ex) {
            System.out.println("cannot convert product id = " + hrProduct.getId());
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
        return null;
    }

//    public HrBindingProduct convertParentProduct(HrProduct hrProduct) {
//        try {
//            HrBindingProduct hrBindingProduct = convertProductCommon(hrProduct);
//            return hrBindingProduct;
//        } catch (Exception ex) {
//            System.out.println("cannot convert product id = " + hrProduct.getId());
//            System.out.println(ex.getMessage());
//            System.out.println(ex.getStackTrace());
//        }
//        return null;
//    }

    private HrBindingProduct createProductDetail(HrProduct hrProduct, HrBindingProduct hrSaleBindingProduct) throws IOException {
        List<HrProductImageAssignment> subPictures = DataHelper.getFieldList(hrProduct.getHrProductImageAssignments(),
                Function.identity(), e -> e.getPurpose().equals(ImagePurpose.SUB));
        if (CollectionUtils.isNotEmpty(subPictures)) {
            for (int i = 0; i < subPictures.size(); i++) {
                HrBindingProduct hrBindingProduct = new HrBindingProduct();
                hrBindingProduct.setImageName(subPictures.get(i).getHrProductImage().getLocation());
                hrBindingProduct.setIndex(i);
                hrSaleBindingProduct.getChildImages().add(hrBindingProduct);
            }
        }

        return hrSaleBindingProduct;
    }





}
