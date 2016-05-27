package org.crossplatform.backend.model.datamodel;

import java.util.HashMap;
import java.util.Map;

public class ProfiledDataModelFactory {

	private static Map<String, ProfiledDataModel> cache = new HashMap<String, ProfiledDataModel>();
	
	public static ProfiledDataModel getProfiledDataModel(String profileName) {
		if (cache.containsKey(profileName)) {
			return cache.get(profileName);
		} else {
			ProfiledDataModel pdm = new ProfiledDataModel(profileName);
			cache.put(profileName, pdm);
			return pdm;
		}
	}
	
	public static boolean updateProfiledDataModel(String profileName) {
		if (cache.containsKey(profileName)) {
			cache.remove(profileName);
			cache.put(profileName, new ProfiledDataModel(profileName));
			return true;
		}
		return false;
	}
}
