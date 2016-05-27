package org.crossplatform.web.test.viewtests;

import org.crossplatform.web.test.config.WebTestConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

public class TechnologyViewTest {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(CriteriaViewTest.class);

	private WebDriver driver;

	@Before
	public void prepare() {
		// Init
		driver = new FirefoxDriver();

		// Go to website
		driver.navigate()
				.to(WebTestConfiguration.TECHNOLOGIES_URL);
	}

	@After
	public void tearDown() {
		// Close the browser
		driver.quit();
	}

	@Test
	public void testPresenceOfTechnologies() {
		LOG.info("Test presence of technologies");

		final Predicate<WebDriver> presenceOfAllTechnologies = driver -> WebTestConfiguration.TECHNOLOGIES
				.stream().allMatch(t -> driver.getPageSource().contains(t));

		(new WebDriverWait(driver, 10)).until(presenceOfAllTechnologies);

		LOG.info("-> Ok.");
	}
}