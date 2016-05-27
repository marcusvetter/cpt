package org.crossplatform.web.test.viewtests;

import org.crossplatform.web.test.config.WebTestConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuTest {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(CriteriaViewTest.class);
	
	private WebDriver driver;

	@Before
	public void prepare() {
		// Init
		driver = new FirefoxDriver();

		// Go to website
		driver.navigate().to(WebTestConfiguration.BASE_URL);
	}

	@After
	public void tearDown() {
		// Close the browser
		driver.quit();
	}
	
	@Test
	public void testMenuButtonTechnologies() {
		LOG.info("Test menu button technologies");
		testMenuButtonById("cpt-test-menu-technologies", WebTestConfiguration.BASE_URL + "/#!/technologies");
		LOG.info("-> Ok.");
	}
	
	@Test
	public void testMenuButtonCriteria() {
		LOG.info("Test menu button criteria");
		testMenuButtonById("cpt-test-menu-criteria", WebTestConfiguration.BASE_URL + "/#!/criteria");
		LOG.info("-> Ok.");
	}
	
	@Test
	public void testMenuButtonDecisionTool() {
		LOG.info("Test menu button decision tool");
		testMenuButtonById("cpt-test-menu-decision-tool", WebTestConfiguration.BASE_URL + "/#!/decision-tool");
		LOG.info("-> Ok.");
	}
	
	private void testMenuButtonById(String idToTest, final String expectedUrl) {
		WebElement element = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id(idToTest)));
		
		element.click();
		
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getCurrentUrl().contains(expectedUrl);
			}
		});
	}

}
