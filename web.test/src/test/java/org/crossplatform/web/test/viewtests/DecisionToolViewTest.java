package org.crossplatform.web.test.viewtests;

import static org.junit.Assert.assertEquals;

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

public class DecisionToolViewTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(CriteriaViewTest.class);
	
	private WebDriver driver;

	@Before
	public void prepare() {
		// Init
		driver = new FirefoxDriver();

		// Go to website
		driver.navigate().to(
				WebTestConfiguration.DECISION_TOOL_URL);
	}

	@After
	public void tearDown() {
		// Close the browser
		driver.quit();
	}

	@Test
	public void testWizzardNavigation() {
		LOG.info("Test decision tool wizzard navigation");

		// Variables
		List<WebElement> nextButtons;
		List<WebElement> previousButtons;
		long nextButtonsCount;
		long previousButtonsCount;
		int decisionToolPages = (WebTestConfiguration.DECISION_TOOL_WIZZARD_PAGES * 2) - 2;

		// 0th to 14th step
		for (int i = 0; i <= decisionToolPages; i++) {
			nextButtons = getButtonElementsByClassName("cpt-test-decision-tool-next-button");
			previousButtons = getButtonElementsByClassName("cpt-test-decision-tool-previous-button");
			nextButtonsCount = countVisibleButtons(nextButtons);
			previousButtonsCount = countVisibleButtons(previousButtons);

			assertEquals(2, nextButtons.size());
			assertEquals(1, previousButtons.size());

			assertEquals(i == (decisionToolPages / 2) ? 0 : 1, nextButtonsCount);
			assertEquals(i == 0 || i == decisionToolPages ? 0 : 1, previousButtonsCount);

			if (i < (decisionToolPages / 2)) {
				// Click on the first next button (except of the 6th step ->
				// click second button)
				nextButtons.get(i == (decisionToolPages / 2) - 1 ? 1 : 0).click();
			} else if (i >= (decisionToolPages / 2) && i < decisionToolPages) {
				// Click on the first previous button (except of the 7th step ->
				// click second button)
				previousButtons.get(i == (decisionToolPages / 2) ? 1 : 0).click();
			}

			// Assert Welcome text (0th and 14th step)
			if (i == 0 || i == decisionToolPages) {
				final Predicate<WebDriver> presenceOfWelcome = driver -> driver
						.getPageSource().contains("Welcome");
				(new WebDriverWait(driver, 10)).until(presenceOfWelcome);
			}
		}
		
		LOG.info("-> Ok.");

	}

	private List<WebElement> getButtonElementsByClassName(String className) {
		return (new WebDriverWait(driver, 10)).until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(By.className(className)));
	}

	private long countVisibleButtons(List<WebElement> buttons) {
		return buttons.stream().filter(element -> element.isDisplayed())
				.count();
	}

}
