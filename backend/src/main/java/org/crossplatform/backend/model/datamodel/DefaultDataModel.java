package org.crossplatform.backend.model.datamodel;

import org.crossplatform.backend.service.FileSystemConnector;

public class DefaultDataModel extends AbstractDataModel {
	
	public DefaultDataModel() {
		super.setTechnologies(FileSystemConnector.loadTechnologies(FileSystemConnector.TECHNOLOGIES_DEFAULT_LOCATION));
		super.setCriteria(FileSystemConnector.loadCriteria(FileSystemConnector.CRITERIA_DEFAULT_LOCATION));
	}
	
}
