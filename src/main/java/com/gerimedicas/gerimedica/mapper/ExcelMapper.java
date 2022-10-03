package com.gerimedicas.gerimedica.mapper;

import com.gerimedicas.gerimedica.dto.ExcelDto;
import com.gerimedicas.gerimedica.entity.Excel;

import java.util.LinkedList;
import java.util.List;

/**
 * @author volkanozturk
 */
public class ExcelMapper {

	public static ExcelDto toDto(Excel excel) {
		return ExcelDto.builder()
				.source(excel.getSource())
				.codeListCode(excel.getCodeListCode())
				.code(excel.getCode())
				.displayValue(excel.getDisplayValue())
				.longDescription(excel.getLongDescription())
				.fromDate(excel.getFromDate()).toDate(excel.getToDate())
				.sortingPriority(excel.getSortingPriority())
				.build();
	}

	public static List<ExcelDto> toDto(List<Excel> excelList) {
		List<ExcelDto> dtos = new LinkedList<>();
		excelList.forEach(excel -> dtos.add(toDto(excel)));
		return dtos;
	}

}
