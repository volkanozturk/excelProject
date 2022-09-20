package com.gerimedicas.gerimedica.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author volkanozturk
 */


@Getter
@Setter
@MappedSuperclass
public abstract class Line {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

}
