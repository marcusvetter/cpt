package org.crossplatform.backend.controller;

import java.util.List;

import org.crossplatform.backend.model.criterion.UserScoredCriterion;
import org.crossplatform.backend.model.datamodel.DefaultDataModel;
import org.crossplatform.backend.model.datamodel.ProfiledDataModelFactory;
import org.crossplatform.backend.model.technology.ScoredTechnology;
import org.crossplatform.backend.scoreengine.ScoreEngine;
import org.crossplatform.backend.service.DBConnector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScoreController {

	private DefaultDataModel defaultDataModel;
	private ScoreEngine defaultScoreEngine;

	public ScoreController() {
		this.defaultDataModel = new DefaultDataModel();
		this.defaultScoreEngine = new ScoreEngine(defaultDataModel);
	}

	@RequestMapping(value="/ranking/{profileName}", method=RequestMethod.POST)
	public @ResponseBody List<ScoredTechnology> ranking(@PathVariable("profileName") String profileName,
			@RequestBody List<UserScoredCriterion> userScoredCriteria) {
		if (DBConnector.DEFAULT_PROFILE_NAME.equals(profileName)) {
			return this.defaultScoreEngine.score(userScoredCriteria);
		} else {
			return new ScoreEngine(ProfiledDataModelFactory.getProfiledDataModel(profileName)).score(userScoredCriteria);
		}
	}

}
