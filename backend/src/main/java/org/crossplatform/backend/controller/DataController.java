package org.crossplatform.backend.controller;

import java.util.List;

import org.crossplatform.backend.model.criterion.Criterion;
import org.crossplatform.backend.model.datamodel.DefaultDataModel;
import org.crossplatform.backend.model.datamodel.ProfiledDataModelFactory;
import org.crossplatform.backend.model.technology.Technology;
import org.crossplatform.backend.service.DBConnector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataController {

	private DefaultDataModel defaultDataModel;

	public DataController() {
		this.defaultDataModel = new DefaultDataModel();
	}

	@RequestMapping("/")
	public @ResponseBody String index() {
		return "Welcome to the cpt api!";
	}

	@RequestMapping("/api/technologies/{profileName}")
	public @ResponseBody List<Technology> technologies(
			@PathVariable("profileName") String profileName) {
		if (DBConnector.DEFAULT_PROFILE_NAME.equals(profileName)) {
			return this.defaultDataModel.getTechnologies();
		} else {
			return ProfiledDataModelFactory.getProfiledDataModel(profileName).getTechnologies();
		}
	}
	
	@RequestMapping(value = "/api/technologies/{profileName}", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	@ResponseBody
	public boolean updateTechnologies(@PathVariable("profileName") String profileName, @RequestBody List<Technology> technologies) {
		if (DBConnector.DEFAULT_PROFILE_NAME.equals(profileName)) {
			return false;
		} else {
			return ProfiledDataModelFactory.getProfiledDataModel(profileName).updateTechnologies(technologies);
		}
	}

	@RequestMapping("/api/criteria/{profileName}")
	public @ResponseBody List<Criterion> criteria(@PathVariable("profileName") String profileName) {
		if (DBConnector.DEFAULT_PROFILE_NAME.equals(profileName)) {
			return this.defaultDataModel.getCriteria();
		} else {
			return ProfiledDataModelFactory.getProfiledDataModel(profileName).getCriteria();
		}
	}
	
	@RequestMapping(value = "/api/criteria/{profileName}", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	@ResponseBody
	public boolean updateCriteria(@PathVariable("profileName") String profileName, @RequestBody List<Criterion> criteria) {
		if (DBConnector.DEFAULT_PROFILE_NAME.equals(profileName)) {
			return false;
		} else {
			return ProfiledDataModelFactory.getProfiledDataModel(profileName).updateCriteria(criteria);
		}
	}

}