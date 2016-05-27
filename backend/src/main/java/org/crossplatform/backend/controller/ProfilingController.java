package org.crossplatform.backend.controller;

import org.crossplatform.backend.service.DBConnector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProfilingController {
	
    @RequestMapping(value = "/api/profiling/{profileName}", method = RequestMethod.PUT)
    public @ResponseBody boolean index(@PathVariable("profileName") String profileName) {
    	if (null == profileName) {
    		profileName = DBConnector.DEFAULT_PROFILE_NAME;
    	}
    	return DBConnector.createNewProfile(profileName);
    }

}
