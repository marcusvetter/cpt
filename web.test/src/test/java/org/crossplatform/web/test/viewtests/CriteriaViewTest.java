package org.crossplatform.web.test.viewtests;

import java.util.List;

import org.crossplatform.web.test.config.WebTestConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

public class CriteriaViewTest {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(CriteriaViewTest.class);

	private WebDriver driver;

	@Before
	public void prepare() {
		// Init
		driver = new FirefoxDriver();

		// Go to website
		driver.navigate().to(WebTestConfiguration.CRITERIA_URL);
	}

	@After
	public void tearDown() {
		// Close the browser
		driver.quit();
	}

	@Test
	public void testPresenceOfCriteria() {
		LOG.info("Test presence of criteria");
		
		final Predicate<WebDriver> presenceOfAllCriteria = driver -> WebTestConfiguration.CRITERIA
				.stream().allMatch(c -> driver.getPageSource().contains(c));

		(new WebDriverWait(driver, 10)).until(presenceOfAllCriteria);

		LOG.info("-> Ok.");
	}

	@Test
	public void testPresenceOfCriterionValuesOfTargetPlatform() {
		LOG.info("Test presence of some criterion values of target platform");

		List<WebElement> elements = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
						.className("cpt-criterion-show-further-properties")));
		
		elements.stream().forEach(element -> element.click());

		final Predicate<WebDriver> presenceOfSomeCriterionValues = driver -> WebTestConfiguration.CRITERION_VALUES_OF_TARGET_PLATFORM
				.stream().allMatch(cv -> driver.getPageSource().contains(cv));

		(new WebDriverWait(driver, 10)).until(presenceOfSomeCriterionValues);

		LOG.info("-> Ok.");
	}
}