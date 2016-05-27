package org.crossplatform.web.test.config;

import java.util.Arrays;
import java.util.List;

public class WebTestConfiguration {
	// URLS
	public static final String BASE_URL = "http://cross-platform.org";
	public static final String TECHNOLOGIES_URL = BASE_URL + "/#!/technologies";
	public static final String CRITERIA_URL = BASE_URL + "/#!/criteria";
	public static final String DECISION_TOOL_URL = BASE_URL + "/#!/decision-tool";
	
	// Technologies and Criteria
	public static final List<String> TECHNOLOGIES = Arrays.asList("");
	public static final List<String> CRITERIA = Arrays.asList("Device acces",
			"Monetization", "Non-functional requirements", "Target platform", "Technology properties");
	public static final List<String> CRITERION_VALUES_OF_TARGET_PLATFORM = Arrays.asList("Android",
			"iOS", "Windows Phone", "BlackBerry", "Symbian");
	
	// Decision tool
	public static final int DECISION_TOOL_WIZZARD_PAGES = CRITERIA.size() + 0;
	
	
	// Hide public constructor
	private WebTestConfiguration() {
		
	}
}
