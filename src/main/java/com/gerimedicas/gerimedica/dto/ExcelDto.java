package com.gerimedicas.gerimedica.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author volkanozturk
 */
@Getter
@Setter
@Builder
@ToString
public class ExcelDto implements Serializable {

	private String source;
	private String codeListCode;
	private String code;
	private String displayValue;
	private String longDescription;
	private Date fromDate;
	private Date toDate;
	private String sortingPriority;


}
