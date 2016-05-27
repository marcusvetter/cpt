package org.crossplatform.backend.model.criterion;

public class CriterionValue {
	private String id;
	private String name;
	private String[] scoreType;

	public CriterionValue() {

	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String[] getScoreType() {
		return scoreType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScoreType(String[] scoreType) {
		this.scoreType = scoreType;
	}
}
