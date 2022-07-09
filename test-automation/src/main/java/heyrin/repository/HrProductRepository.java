package heyrin.repository;

import heyrin.repository.entity.HrProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HrProductRepository extends JpaRepository<HrProduct, Integer> {
}
