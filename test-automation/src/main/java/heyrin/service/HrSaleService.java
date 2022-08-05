package heyrin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import heyrin.dto.ProductStatus;
import heyrin.repository.HrProductImageAssignmentRepository;
import heyrin.repository.HrProductPromotionAssignmentRepository;
import heyrin.repository.HrProductRepository;
import heyrin.repository.entity.HrProduct;
import heyrin.repository.entity.HrProductImage;
import heyrin.repository.entity.HrProductImageAssignment;
import heyrin.repository.entity.HrProductPromotionAssignment;
import heyrin.service.dto.HrBindingProduct;
import heyrin.service.dto.ImagePurpose;
import heyrin.utils.DataHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

@Service
public class HrSaleService {
    private static final String HEYRIN_HOME_PAGE_TEMPLATE = "heyrin-product-index.html";
    private static final String HEYRIN_SALE_TEMPLATE = "heyrin-sale-overview.html";
    private static final String HEYRIN_PRODUCT_TEMPLATE = "heyrin-product-catalog.html";
    private static final String HEYRIN_PRODUCT_DETAIL_TEMPLATE = "heyrin-product-detail.html";
    @Autowired
    private HrProductPromotionAssignmentRepository hrProductPromotionAssignment;
    @Autowired
    private HrConfigurationService hrConfigurationService;
    @Autowired
    private HrProductConverter hrProductConverter;
    @Autowired
    private HrProductSorting hrProductSorting;
    @Autowired
    private HrProductImageAssignmentRepository hrProductImageAssignmentRepository;
    @Autowired
    private HrProductRepository hrProductRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    @Transactional
    public void createHomePage() throws IOException {
        StringWriter writer = new StringWriter();
        VelocityContext context = new VelocityContext();

        List<HrProductImageAssignment> mainCoverImages = hrProductImageAssignmentRepository.findByPurpose(ImagePurpose.MAIN_COVER);
        Assert.isTrue(CollectionUtils.isNotEmpty(mainCoverImages), "Cover image is empty");
        HrProductImageAssignment mainCoverImage = mainCoverImages.get(0);
        HrBindingProduct coverProduct = hrProductConverter.convertProductCommon(mainCoverImage.getHrProduct());
        String format = String.format("%s/%s", mainCoverImage.getHrProduct().getFolder(), mainCoverImage.getHrProductImage().getLocation());
        coverProduct.setProductLocation(format);
        context.put("coverProduct", coverProduct);

        List<HrProductImageAssignment> subCovers = hrProductImageAssignmentRepository.findByPurpose(ImagePurpose.SUB_COVER);
        Assert.isTrue(CollectionUtils.isNotEmpty(subCovers), "Cover image is empty");
        List<HrBindingProduct> subCoverProducts = DataHelper.getFieldList(subCovers, e -> {
            HrBindingProduct hrBindingProduct = hrProductConverter.convertProductCommon(e.getHrProduct());
            coverProduct.setProductLocation(String.format("%s/%s", mainCoverImage.getHrProduct().getFolder(), mainCoverImage.getHrProductImage().getLocation()));
            return hrBindingProduct;
        }, e -> e.getHrProduct() != null);
        context.put("subCoverProducts", subCoverProducts);

        List<HrProduct> newArrivalProduct = hrProductRepository.findByStatus(ProductStatus.NEW_ARRIVAL);
        List<HrBindingProduct> hrBindingProducts = DataHelper.getFieldList(newArrivalProduct, hrProductConverter::convertProductCommon, Objects::nonNull);
        context.put("newProducts", hrBindingProducts);

        context.put("newProductLocations", objectMapper.writeValueAsString(DataHelper.getFieldList(hrBindingProducts, HrBindingProduct::getProductLocation)));

        Velocity.mergeTemplate("template" + File.separator + HEYRIN_HOME_PAGE_TEMPLATE, "UTF-8", context, writer);


        Path myObj = createIndexFile();
        Files.write(myObj, writer.toString().getBytes());
    }

    @Transactional
    public void createSalePage() throws IOException {
        StringWriter writer = new StringWriter();
        VelocityContext context = new VelocityContext();
        List<HrProductPromotionAssignment> hrProductPromotionAssignments = hrProductPromotionAssignment.findByDeprecatedTimeIsNull();
        List<HrBindingProduct> hrSaleBindingProducts = DataHelper.getFieldList(hrProductPromotionAssignments, this::createSaleElement, Objects::nonNull);
        hrSaleBindingProducts = hrProductSorting.sortProducts(hrSaleBindingProducts);
        context.put("productSales", hrSaleBindingProducts);
        Velocity.mergeTemplate("template" + File.separator + HEYRIN_SALE_TEMPLATE, "UTF-8", context, writer);


        Path myObj = createSaleFile();
        Files.write(myObj, writer.toString().getBytes());
    }


    @Transactional
    public void createCatalogPage() throws IOException {
        StringWriter writer = new StringWriter();
        VelocityContext context = new VelocityContext();
        List<HrProduct> allProducts = hrProductRepository.findAll();
        List<HrBindingProduct> hrBindingProducts = DataHelper.getFieldList(allProducts, hrProductConverter::convertProductCommon, Objects::nonNull);
        hrBindingProducts = hrProductSorting.sortProducts(hrBindingProducts);
        context.put("productSales", hrBindingProducts);
        Velocity.mergeTemplate("template" + File.separator + HEYRIN_PRODUCT_TEMPLATE, "UTF-8", context, writer);

        Path myObj = createCatalogFile();
        Files.write(myObj, writer.toString().getBytes());
    }

    @Transactional
    public void createDetailProducts() throws IOException {
        List<HrProduct> allProducts = hrProductRepository.findAll();
        List<HrProduct> activeProduct = DataHelper.getFieldList(allProducts, Function.identity(),
                e -> e != null && !e.getStatus().equals(ProductStatus.SOLD_OUT));
        DataHelper.getFieldList(allProducts, e -> createProductDetail(e, activeProduct), Objects::nonNull);
    }


    private Path createSaleFile() throws IOException {
        String rootFolder = hrConfigurationService.getConfiguration("root_folder");
        String saleFolder = hrConfigurationService.getConfiguration("sale_folder");
        Path myObj = Paths.get(String.format("%s/%s/%s", rootFolder, saleFolder, "product_sale.html"));
        return myObj;
    }

    private Path createProductDetailFile(String productFolder) throws IOException {
        String rootFolder = hrConfigurationService.getConfiguration("root_folder");
        File file = new File(String.format("%s/images/%s/%s", rootFolder, productFolder, "productDetail.html"));
        return file.toPath();
    }
    private Path createIndexFile() throws IOException {
        String rootFolder = hrConfigurationService.getConfiguration("root_folder");
        File file = new File(String.format("%s/%s", rootFolder, "index.html"));
//        Path myObj = Paths.get();
        return file.toPath();
    }


    private Path createCatalogFile() throws IOException {
        String rootFolder = hrConfigurationService.getConfiguration("root_folder");
        String saleFolder = hrConfigurationService.getConfiguration("catalog_folder");
        Path myObj = Paths.get(String.format("%s/%s/%s", rootFolder, saleFolder, "catalog.html"));
        return myObj;
    }

    private HrBindingProduct createSaleElement(HrProductPromotionAssignment productPromotionAssignment) {

        try {
            HrBindingProduct hrBindingProduct = hrProductConverter.convertProductCommon(productPromotionAssignment.getHrProduct());
            hrBindingProduct.setSaleContent(productPromotionAssignment.getHrProductPromotion().getName());
            return hrBindingProduct;
        } catch (Exception ex) {
            System.out.println("cannot process product sale assignment id = " + productPromotionAssignment.getId());
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
        return null;
    }



    private HrBindingProduct createProductDetail(HrProduct hrProduct, List<HrProduct> activeProducts) {

        try {
            HrBindingProduct hrBindingProduct = hrProductConverter.convertProductCommon(hrProduct);
            List<HrProduct> randomList = getRandomList(hrProduct, activeProducts);
            List<HrBindingProduct> hrBindingProducts = DataHelper.getFieldList(randomList, hrProductConverter::convertProductCommon);
            hrBindingProduct.setRelateProducts(hrBindingProducts);

            StringWriter writer = new StringWriter();
            VelocityContext context = new VelocityContext();
            context.put("productSale", hrBindingProduct);
            Velocity.mergeTemplate("template" + File.separator + HEYRIN_PRODUCT_DETAIL_TEMPLATE, "UTF-8", context, writer);
            Path myObj = createProductDetailFile(hrProduct.getFolder());
            Files.write(myObj, writer.toString().getBytes());

            return hrBindingProduct;
        } catch (Exception ex) {
            System.out.println("cannot process product sale assignment id = " + hrProduct.getId());
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
        return null;
    }


    List<HrProduct> getRandomList(HrProduct hrProduct, List<HrProduct> hrProducts) {
        Random randNum = new Random();
        Map<Integer, HrProduct> set = new HashMap<>();
        while (set.size() < 8) {
            int i = randNum.nextInt(hrProducts.size());
            if (hrProduct.getId().compareTo(hrProducts.get(i).getId()) == 0) {
                continue;
            }
            set.put(i, hrProducts.get(i));
        }
        return new ArrayList<>(set.values());
    }

}
