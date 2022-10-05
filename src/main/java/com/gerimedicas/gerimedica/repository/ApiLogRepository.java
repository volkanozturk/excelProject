package com.gerimedicas.gerimedica.repository;

import com.gerimedicas.gerimedica.entity.ApiLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author volkanozturk
 */
@Repository
public interface ApiLogRepository extends JpaRepository<ApiLog, Long> {
}
