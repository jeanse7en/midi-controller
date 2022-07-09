package heyrin.repository;

import heyrin.repository.entity.HrConfigruation;
import heyrin.service.HrConfigurationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HrConfigurationRepository extends JpaRepository<HrConfigruation, Integer> {
    HrConfigruation findOneByKey(String key);
}
