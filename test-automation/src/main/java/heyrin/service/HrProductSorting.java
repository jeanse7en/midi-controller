package heyrin.service;

import heyrin.repository.entity.HrProduct;
import heyrin.service.dto.HrBindingProduct;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HrProductSorting {

    public List<HrBindingProduct> sortProducts(List<HrBindingProduct> hrProducts) {
        return hrProducts.stream().sorted(Comparator.comparingInt(HrBindingProduct::getIndex)
                        .thenComparing(HrBindingProduct::getProductId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
