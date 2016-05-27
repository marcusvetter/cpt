package org.crossplatform.backend.model.datamodel;

import java.util.List;

import org.crossplatform.backend.model.criterion.Criterion;
import org.crossplatform.backend.model.technology.Technology;
import org.crossplatform.backend.service.DBConnector;

public class ProfiledDataModel extends AbstractDataModel {
	
	private String profileName;

	protected ProfiledDataModel(String profileName) {
		this.profileName = profileName;
		super.setTechnologies(DBConnector.getTechnologies(profileName));
		super.setCriteria(DBConnector.getCriteria(profileName));
	}

	public boolean updateTechnologies(List<Technology> technologies) {
		return DBConnector.updateTechnologies(this.profileName, technologies);
	}

	public boolean updateCriteria(List<Criterion> criteria) {
		return DBConnector.updateCriteria(this.profileName, criteria);
	}

}