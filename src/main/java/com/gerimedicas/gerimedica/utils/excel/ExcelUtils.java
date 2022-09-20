package com.gerimedicas.gerimedica.utils.excel;

import com.gerimedicas.gerimedica.exception.FileUploadException;
import com.gerimedicas.gerimedica.exception.UnsupportedExcelVersionException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @author volkanozturk
 */
public final class ExcelUtils {

	private static final Integer HEADER_ROW_NUM = 0;
	public static final String SUPPORT_EXCEL_VERSION = ".xlsx";
	public static final String SOURCE_HEADER = "source";
	public static final String CODE_LIST_HEADER = "codeListCode";
	public static final String CODE_HEADER = "code";
	public static final String DISPLAY_VALUE_HEADER = "displayValue";
	public static final String LONG_DESCRIPTION_HEADER = "longDescription";
	public static final String FROM_DATE_HEADER = "fromDate";
	public static final String TO_DATE_HEADER = "toDate";
	public static final String SORTING_PRIORITY_HEADER = "sortingPriority";

	private static final List<String> expectedHeaders = Arrays.asList(SOURCE_HEADER, CODE_LIST_HEADER, CODE_HEADER, DISPLAY_VALUE_HEADER, LONG_DESCRIPTION_HEADER, FROM_DATE_HEADER, TO_DATE_HEADER, SORTING_PRIORITY_HEADER);

	private ExcelUtils() {
	}

	public static List<Map<String, String>> parse(MultipartFile file) throws UnsupportedExcelVersionException {
		checkVersion(file);
		List<Map<String, String>> headerValueMapList = new ArrayList<>();

		try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
			XSSFSheet sheet = workbook.getSheetAt(0);

			Map<Integer, String> columnIndexHeaderMap = expectedHeaders.stream()
					.collect(toMap(expectedHeaders::indexOf, identity()));

			sheet.forEach(row -> {
				if (row.getRowNum() != HEADER_ROW_NUM) {
					Map<String, String> rowHolder = process(row, columnIndexHeaderMap);
					headerValueMapList.add(rowHolder);
				}
			});

		} catch (IOException e) {
			throw new FileUploadException(e.getMessage());
		}
		return headerValueMapList;
	}

	private static void checkVersion(MultipartFile file) throws UnsupportedExcelVersionException {
		String filename = file.getOriginalFilename();
		if (!filename.endsWith(SUPPORT_EXCEL_VERSION)) {
			throw new UnsupportedExcelVersionException();
		}
	}

	private static Map<String, String> process(Row row, Map<Integer, String> columnIndexHeaderMap) {
		Map<String, String> rowHolder = new LinkedHashMap<>();
		for (Map.Entry<Integer, String> entry : columnIndexHeaderMap.entrySet()) {
			Integer columnIndex = entry.getKey();
			String header = entry.getValue();
			Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if ("fromDate".equals(header) || "toDate".equals(header)) {
				String dateValue = readDateValue(cell);
				rowHolder.put(header, dateValue);
			} else {
				String cellValue = getCellValue(cell);
				rowHolder.put(header, cellValue);
			}
		}
		return rowHolder;
	}

	private static String getCellValue(Cell cell) {
		String cellValue = null;
		if (Objects.nonNull(cell)) {
			cellValue = readCellValue(cell);
			if (Objects.nonNull(cellValue)) {
				cellValue = cellValue.trim();
			}
			if (cellValue.isEmpty()) {
				cellValue = null;
			}
		}
		return cellValue;
	}

	private static String readCellValue(Cell cell) {
		String cellValue;
		CellType cellType = cell.getCellType();
		if (cellType == CellType.NUMERIC) {
			cellValue = readNumericCellType(cell);
		} else {
			cellValue = cell.getStringCellValue();
		}
		return cellValue;
	}

	private static String readNumericCellType(Cell cell) {
		String cellValue;
		Double numericCellValue = cell.getNumericCellValue();
		if (numericCellValue == numericCellValue.intValue()) {
			cellValue = String.valueOf(numericCellValue.intValue());
		} else {
			cellValue = String.valueOf(numericCellValue);
		}
		return cellValue;
	}

	private static String readDateValue(Cell cell) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String cellValue = null;
		if (Objects.nonNull(cell)) {
			cellValue = formatter.format(cell.getDateCellValue());
		}
		return cellValue;
	}


}
