package com.medifitbe.user.adapter.out.persistence.repository;

import com.medifitbe.user.adapter.out.persistence.entity.SubscriberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberEntity, Long> {
    List<SubscriberEntity> findAll();

}
