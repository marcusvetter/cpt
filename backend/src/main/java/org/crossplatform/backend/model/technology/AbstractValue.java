package org.crossplatform.backend.model.technology;

public abstract class AbstractValue {
	private String id;
	private String referenceType;
	private String referenceLink;
	
	public String getId() {
		return id;
	}
	
	public String getReferenceType() {
		return referenceType;
	}
	
	public String getReferenceLink() {
		return referenceLink;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setReferenceLink(String referenceLink) {
		this.referenceLink = referenceLink;
	}
	
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	

}
