package heyrin.repository;

import heyrin.repository.entity.HrProduct;
import heyrin.repository.entity.HrProductPromotionAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HrProductPromotionAssignmentRepository extends JpaRepository<HrProductPromotionAssignment, Integer> {
}
