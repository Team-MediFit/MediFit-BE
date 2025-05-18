package com.medifitbe.jobdata.adapter.out.persistence.repository;


import com.medifitbe.jobdata.adapter.out.persistence.entity.JobDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDataRepository extends JpaRepository<JobDataEntity, Long> {
    boolean existsByLink(String link);
}
