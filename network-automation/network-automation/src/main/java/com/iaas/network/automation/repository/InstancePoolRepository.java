package com.iaas.network.automation.repository;

import com.iaas.network.automation.repository.entity.InstancePoolEntity;
import com.iaas.network.automation.repository.entity.PoolVniEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstancePoolRepository extends JpaRepository<InstancePoolEntity, Long> {
    @Query(value = "select pv.* from instance_pool pv \n" +
            "where pv.resource_pool_id = :resourcePoolId and pv.is_used IN (:isUsed) \n" +
            "and (:startValue is null or pv.value > :startValue) ", nativeQuery = true)
    List<InstancePoolEntity> findResource(Integer resourcePoolId, String startValue, List<Integer> isUsed, Pageable of);

    @Query(value = "select pv.* from instance_pool pv \n" +
            "where pv.resource_pool_id = :resourcePoolId and pv.is_used IN (:isUsed) \n" +
            "and (:startValue is null or pv.value < :startValue) ", nativeQuery = true)
    List<InstancePoolEntity> findResourceInvert(Integer resourcePoolId, String startValue, List<Integer> isUsed, Pageable of);

    Optional<InstancePoolEntity> findFirstByValueAndIsUsedIn(String id, List<Integer> status);
}
