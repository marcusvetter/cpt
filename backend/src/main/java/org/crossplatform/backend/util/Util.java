package org.crossplatform.backend.util;

public class Util {
	
	// Hide public constructor (static utility class)
	private Util() {
		
	}
	
	public static String convertCamelCaseToDashedLowerCase(String camelCase) {
		String regex = "([a-z])([A-Z])";
        String replacement = "$1-$2";
        return camelCase.replaceAll(regex, replacement).toLowerCase();
	}

}
