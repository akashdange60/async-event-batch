package com.async_event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.async_event.model.MetricsMailAudit;
@Repository
public interface MetricsMailAuditRepository extends JpaRepository<MetricsMailAudit, Long>{

}
