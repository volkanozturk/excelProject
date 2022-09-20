package com.gerimedicas.gerimedica.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * @author volkanozturk
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames="code"))
public class Excel extends Line {

	private String source;
	private String codeListCode;
	private String code;
	private String displayValue;
	private String longDescription;
	private Date fromDate;
	private Date toDate;
	private String sortingPriority;


}
