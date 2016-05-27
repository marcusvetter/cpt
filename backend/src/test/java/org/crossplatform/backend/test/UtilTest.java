package org.crossplatform.backend.test;

import static org.junit.Assert.assertEquals;

import org.crossplatform.backend.util.Util;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilTest {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(UtilTest.class);
	
	@Test
	public void testConvertCamelCaseToDashedLowerCase() {
		LOG.debug("----");
		LOG.debug("Run test: testConvertCamelCaseToDashedLowerCase");
		LOG.debug("----");
		
		// Arrange and Act
		String resultA = Util.convertCamelCaseToDashedLowerCase("camelCase");
		String resultB = Util.convertCamelCaseToDashedLowerCase("camel");
		String resultC = Util.convertCamelCaseToDashedLowerCase("caseCamelCamelCamelCase");
		
		// Assert
		assertEquals("camel-case", resultA);
		assertEquals("camel", resultB);
		assertEquals("case-camel-camel-camel-case", resultC);
		
		LOG.debug("Test passed.");
	}

}
