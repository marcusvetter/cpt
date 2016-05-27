package org.crossplatform.backend.model.technology;

import org.crossplatform.backend.model.technology.RatedValue;

public class TechnologyProperty {
	private String criterionId;
	private StrongValue[] strongValues;
	private RatedValue[] ratedValues;

	public TechnologyProperty() {

	}

	public String getCriterionId() {
		return criterionId;
	}

	public StrongValue[] getStrongValues() {
		return strongValues;
	}

	public RatedValue[] getRatedValues() {
		return ratedValues;
	}

	public void setCriterionId(String criterionId) {
		this.criterionId = criterionId;
	}

	public void setStrongValues(StrongValue[] strongValues) {
		this.strongValues = strongValues;
	}

	public void setRatedValues(RatedValue[] ratedValues) {
		this.ratedValues = ratedValues;
	}

}
