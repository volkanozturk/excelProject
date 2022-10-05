package com.gerimedicas.gerimedica.service;

import com.gerimedicas.gerimedica.dto.ExcelDto;
import com.gerimedicas.gerimedica.entity.Excel;
import com.gerimedicas.gerimedica.exception.ExcelNotFoundException;
import com.gerimedicas.gerimedica.exception.UnsupportedExcelVersionException;
import com.gerimedicas.gerimedica.mapper.ExcelMapper;
import com.gerimedicas.gerimedica.repository.ExcelRepository;
import com.gerimedicas.gerimedica.shared.GenericResponse;
import com.gerimedicas.gerimedica.utils.excel.ExcelUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.gerimedicas.gerimedica.utils.excel.ExcelUtils.*;


/**
 * @author volkanozturk
 */
@Service
public class ExcelService {
	private static final Logger LOGGER = LogManager.getLogger(ExcelService.class);

	private static final Integer HUNDRED = 100;

	private final ExcelRepository repository;

	@Autowired
	public ExcelService(ExcelRepository repository) {
		this.repository = repository;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void upload(MultipartFile file) throws UnsupportedExcelVersionException {
		List<Map<String, String>> headerValueMapList = ExcelUtils.parse(file);
		List<Excel> excelList = new ArrayList<>();
		for (Map<String, String> row : headerValueMapList) {
			String source = row.get(SOURCE_HEADER);
			String codeListCode = row.get(CODE_LIST_HEADER);
			String code = row.get(CODE_HEADER);
			String displayValue = row.get(DISPLAY_VALUE_HEADER);
			String longDescription = row.get(LONG_DESCRIPTION_HEADER);
			Date fromDate = parseOfDate(row.get(FROM_DATE_HEADER));
			Date toDate = parseOfDate(row.get(TO_DATE_HEADER));
			String sortingPriority = row.get(SORTING_PRIORITY_HEADER);

			excelList.add(new Excel(source, codeListCode, code, displayValue, longDescription, fromDate, toDate, sortingPriority));
		}
		List<List<Excel>> partitions = ListUtils.partition(excelList, HUNDRED);
		partitions.forEach(repository::saveAll);
	}

	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	public List<ExcelDto> getAllExcelInfos(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<Excel> excelList = this.repository.findAll(pageable).getContent();
		return ExcelMapper.toDto(excelList);
	}

	@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
	public ExcelDto getExcelInfoByCode(String code) throws ExcelNotFoundException {
		Optional<Excel> excelOptional = repository.findByCode(code);
		return excelOptional.map(ExcelMapper::toDto).orElseThrow(ExcelNotFoundException::new);
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public GenericResponse deleteAll() {
		List<Excel> allExcelInfos = repository.findAll();
		if (allExcelInfos.isEmpty()) {
			return GenericResponse.failure(HttpStatus.NOT_FOUND.value());
		}
		List<List<Excel>> partitions = ListUtils.partition(allExcelInfos, HUNDRED);
		partitions.forEach(repository::deleteAll);
		return GenericResponse.successful();
	}

	private Date parseOfDate(String date) {
		if (Objects.isNull(date)) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try {
			return formatter.parse(date);
		} catch (ParseException ex) {
			LOGGER.error("Error when parsing excel date: {}", date, ex);
			return null;
		}
	}

}
