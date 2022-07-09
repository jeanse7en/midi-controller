package heyrin.service;

import heyrin.repository.HrProductImageAssignmentRepository;
import heyrin.repository.HrProductPromotionAssignmentRepository;
import heyrin.repository.entity.HrProductImage;
import heyrin.repository.entity.HrProductImageAssignment;
import heyrin.repository.entity.HrProductPromotionAssignment;
import heyrin.service.dto.ImagePurpose;
import heyrin.utils.DataHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class HrSaleService {
    public static final String HOME_UBUNTU_DOCUMENTS_HEY_RIN_STORE_IMAGES = "/home/ubuntu/Documents/hey-rin-store/images/";
    @Autowired
    private HrProductPromotionAssignmentRepository hrProductPromotionAssignment;
    @Autowired
    private HrConfigurationService hrConfigurationService;
    @Autowired
    private HrProductImageAssignmentRepository hrProductImageAssignmentRepository;
    @Transactional
    public void createSalePage() throws IOException {
        List<HrProductPromotionAssignment> hrProductPromotionAssignments = hrProductPromotionAssignment.findAll();
        String saleElementOverviewElementTemplate = new String(Files.readAllBytes(Paths.get("./src/main/resources/template/heyrin-sale-overview-element.html")));
        StringBuilder saleElementHtmls = new StringBuilder();
        for (HrProductPromotionAssignment productPromotionAssignment : hrProductPromotionAssignments) {
            String elementHtml = createElementHtml(saleElementOverviewElementTemplate, productPromotionAssignment);
            saleElementHtmls.append(elementHtml);
        }

        String saleElementOverviewTemplate = new String(Files.readAllBytes(Paths.get("./src/main/resources/template/heyrin-sale-overview.html")));
        String replace = saleElementOverviewTemplate.replace("<heyrin-sale-overview-element/>", saleElementHtmls.toString());


        Path myObj = createSaleFile();
        Files.write(myObj, replace.getBytes());
    }

    private Path createSaleFile() throws IOException {
        String rootFolder = hrConfigurationService.getConfiguration("root_folder");
        String saleFolder = hrConfigurationService.getConfiguration("sale_folder");
        Path myObj = Paths.get(String.format("%s/%s/%s", rootFolder, saleFolder, "product_sale.html"));
        return myObj;
    }

    private String createElementHtml(String saleElementOverview, HrProductPromotionAssignment productPromotionAssignment) {

        try {
            List<HrProductImageAssignment> hrProductImageAssignments = productPromotionAssignment.getHrProduct().getHrProductImageAssignments();
            Map<Object, List<HrProductImageAssignment>> imagePurposeSetMap =
                    DataHelper.buildMapGroup(hrProductImageAssignments, e -> e.getPurpose(), Function.identity());

            List<HrProductImageAssignment> mainPictures = imagePurposeSetMap.get(ImagePurpose.MAIN);
            if (CollectionUtils.isEmpty(mainPictures)) {
                return saleElementOverview;
            }
            HrProductImage hrProductImage = mainPictures.get(DataHelper.FIRST_INDEX).getHrProductImage();
            String replace = saleElementOverview
                    .replace("<product_location/>", String.format("%s/%s", productPromotionAssignment.getHrProduct().getFolder(), hrProductImage.getLocation()))
                    .replace("<product_code/>", productPromotionAssignment.getHrProduct().getCode())
                    .replace("<sale_content/>", productPromotionAssignment.getHrProductPromotion().getName())
                    .replace("<product_price/>",
                            productPromotionAssignment.getHrProduct().getHrProductPrices().get(0).getPrice().intValue() + "");
            return replace;
        } catch (Exception ex) {
            System.out.println("cannot process product sale assignment id = " + productPromotionAssignment.getId());
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
        return "";
    }


}
