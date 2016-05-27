package org.crossplatform.backend.model.criterion;


public class UserScoredCriterion {
	private String criterionId;
	private String criterionValueId;
	private boolean strongValue;
	private int ratedValue;
	
	
	public UserScoredCriterion() {
		
	}

	public String getCriterionId() {
		return criterionId;
	}

	public String getCriterionValueId() {
		return criterionValueId;
	}

	public int getRatedValue() {
		return ratedValue;
	}

	public boolean isStrongValue() {
		return strongValue;
	}

	public void setCriterionId(String criterionId) {
		this.criterionId = criterionId;
	}

	public void setCriterionValueId(String criterionValueId) {
		this.criterionValueId = criterionValueId;
	}

	public void setRatedValue(int ratedValue) {
		this.ratedValue = ratedValue;
	}

	public void setStrongValue(boolean strongValue) {
		this.strongValue = strongValue;
	}
	
}
