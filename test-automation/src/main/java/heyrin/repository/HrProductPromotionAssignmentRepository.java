package heyrin.repository;

import heyrin.repository.entity.HrProduct;
import heyrin.repository.entity.HrProductPromotionAssignment;
import heyrin.service.dto.HrBindingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HrProductPromotionAssignmentRepository extends JpaRepository<HrProductPromotionAssignment, Integer> {
    List<HrProductPromotionAssignment> findByDeprecatedTimeIsNull();
}
