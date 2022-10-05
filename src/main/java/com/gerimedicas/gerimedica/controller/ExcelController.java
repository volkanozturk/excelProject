package com.gerimedicas.gerimedica.controller;

import com.gerimedicas.gerimedica.annotation.ApiLogger;
import com.gerimedicas.gerimedica.dto.ExcelDto;
import com.gerimedicas.gerimedica.exception.ExcelNotFoundException;
import com.gerimedicas.gerimedica.exception.UnsupportedExcelVersionException;
import com.gerimedicas.gerimedica.service.ExcelService;
import com.gerimedicas.gerimedica.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author volkanozturk
 */
@RestController
@RequestMapping("/api/v1.0/excels")
public class ExcelController {

	private final ExcelService excelService;

	@Autowired
	public ExcelController(ExcelService excelService) {
		this.excelService = excelService;
	}
	@PostMapping("/upload")
	public GenericResponse uploadFile(@RequestParam("file")  MultipartFile file) throws UnsupportedExcelVersionException {
		excelService.upload(file);
		return GenericResponse.successful();
	}

	@GetMapping(value = "/findAll")
	public GenericResponse<List<ExcelDto>> getAllInfos(@RequestParam(required = false, defaultValue = "0") int page,
													   @RequestParam(required = false, defaultValue = "10") int size) {
		List<ExcelDto> allExcelInfos = excelService.getAllExcelInfos(page, size);
		return GenericResponse.successful(allExcelInfos);
	}
	@ApiLogger
	@GetMapping(value = "/{code}")
	public GenericResponse<ExcelDto> getInfoByCode(@NotNull @PathVariable String code) throws ExcelNotFoundException {
		ExcelDto excelInfoByCode = excelService.getExcelInfoByCode(code);
		return GenericResponse.successful(excelInfoByCode);
	}

	@DeleteMapping
	public GenericResponse deleteAll() {
		return excelService.deleteAll();
	}

}
