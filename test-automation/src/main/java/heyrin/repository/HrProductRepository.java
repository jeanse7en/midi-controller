package heyrin.repository;

import heyrin.dto.ProductStatus;
import heyrin.repository.entity.HrProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HrProductRepository extends JpaRepository<HrProduct, Integer> {
    List<HrProduct> findByStatus(ProductStatus status);
}
