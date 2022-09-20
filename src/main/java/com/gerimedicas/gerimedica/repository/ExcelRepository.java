package com.gerimedicas.gerimedica.repository;

import com.gerimedicas.gerimedica.entity.Excel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author volkanozturk
 */
@Repository
public interface ExcelRepository extends JpaRepository<Excel, Long> {

	Optional<Excel> findByCode(String code);

}
