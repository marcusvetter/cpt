package org.crossplatform.backend.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.crossplatform.backend.model.criterion.Criterion;
import org.crossplatform.backend.model.technology.Technology;
import org.crossplatform.backend.service.FileSystemConnector;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSystemConnectorTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(FileSystemConnectorTest.class);

	@Test
	public void testLoadTechnologies() {
		LOG.debug("Run test: testLoadTechnologies");

		// Arrange and Act
		List<Technology> technologies = FileSystemConnector
				.loadTechnologies(FileSystemConnector.TECHNOLOGIES_DEFAULT_LOCATION);

		// Assert
		assertEquals(12, technologies.size());
		assertTrue(technologies.stream().allMatch(t -> null != t.getId()));

		LOG.debug("Test passed.");
	}

	@Test
	public void testLoadCriteria() {
		LOG.debug("Run test: testLoadCriteria");

		// Arrange and Act
		List<Criterion> criteria = FileSystemConnector
				.loadCriteria(FileSystemConnector.CRITERIA_DEFAULT_LOCATION);

		// Assert
		assertEquals(5, criteria.size());
		assertTrue(criteria.stream().allMatch(
				c -> null != c.getId() && null != c.getName()
						&& null != c.getValues() && 0 < c.getValues().length));

		LOG.debug("Test passed.");
	}

}
