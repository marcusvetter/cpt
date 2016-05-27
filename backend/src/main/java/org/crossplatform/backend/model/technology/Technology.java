package org.crossplatform.backend.model.technology;

import org.mongojack.Id;

public class Technology {

	@Id
	private String id;
	private String name;
	private String producer;
	private String version;
	private String mechanism;
	private String website;
	private String description;
	private String license;
	private String costs;
	private String devLanguage;
	private TechnologyProperty[] properties;

	public Technology() {

	}
	
	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public String getProducer() {
		return this.producer;
	}

	public String getVersion() {
		return this.version;
	}
	
	public String getMechanism() {
		return this.mechanism;
	}

	public String getWebsite() {
		return website;
	}

	public String getDescription() {
		return description;
	}
	
	public String getLicense() {
		return license;
	}
	
	public String getCosts() {
		return costs;
	}
	
	public String getDevLanguage() {
		return devLanguage;
	}

	public TechnologyProperty[] getProperties() {
		return properties;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setProducer(String producer) {
		this.producer = producer;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setLicense(String license) {
		this.license = license;
	}
	
	public void setCosts(String costs) {
		this.costs = costs;
	}
	
	public void setDevLanguage(String devLanguage) {
		this.devLanguage = devLanguage;
	}

	public void setProperties(TechnologyProperty[] properties) {
		this.properties = properties;
	}

}
