package heyrin.repository;

import heyrin.repository.entity.HrProductImageAssignment;
import heyrin.repository.entity.HrProductPromotionAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HrProductImageAssignmentRepository extends JpaRepository<HrProductImageAssignment, Integer> {
}
