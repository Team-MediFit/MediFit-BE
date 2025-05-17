package com.medifitbe.jobdata.adapter.out.persistence.repository;

import com.medifitbe.jobdata.adapter.out.persistence.entity.ValidatedJobDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidatedJobDataRepository extends JpaRepository<ValidatedJobDataEntity, Long> {
}
