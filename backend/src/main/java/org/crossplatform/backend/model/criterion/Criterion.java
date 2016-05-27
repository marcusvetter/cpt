package org.crossplatform.backend.model.criterion;

import org.mongojack.Id;

public class Criterion {
	
	@Id
	private String id;
	private String name;
	private String sort;
	private String description;
	private CriterionValue[] values;

	public Criterion() {

	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getSort() {
		return sort;
	}
	
	public String getDescription() {
		return description;
	}

	public CriterionValue[] getValues() {
		return values;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setValues(CriterionValue[] values) {
		this.values = values;
	}

}
