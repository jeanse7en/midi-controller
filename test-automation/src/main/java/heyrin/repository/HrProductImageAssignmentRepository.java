package heyrin.repository;

import heyrin.repository.entity.HrProductImageAssignment;
import heyrin.repository.entity.HrProductPromotionAssignment;
import heyrin.service.dto.ImagePurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HrProductImageAssignmentRepository extends JpaRepository<HrProductImageAssignment, Integer> {
    List<HrProductImageAssignment> findByPurpose(ImagePurpose imagePurpose);
}
