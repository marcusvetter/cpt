package org.crossplatform.backend.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.crossplatform.backend.model.criterion.UserScoredCriterion;
import org.crossplatform.backend.model.datamodel.DefaultDataModel;
import org.crossplatform.backend.model.technology.ScoredTechnology;
import org.crossplatform.backend.scoreengine.ScoreEngine;
import org.drools.compiler.compiler.DroolsParserException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScoreEngineTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(ScoreEngineTest.class);

	@Rule
	public TestName testName = new TestName();

	private ScoreEngine scoreEngine;
	private DefaultDataModel dataModel;

	@Before
	public void prepare() throws URISyntaxException {
		LOG.debug("----");
		String testMethodName = testName.getMethodName();
		LOG.debug("Run test: " + testMethodName);
		LOG.debug("----");

		this.dataModel = new DefaultDataModel();
		this.scoreEngine = new ScoreEngine(dataModel);
	}

	@Test
	public void noUserScoredCriteria() throws URISyntaxException {

		// Arrange
		List<UserScoredCriterion> userScoredCriteria1 = new ArrayList<>();
		List<UserScoredCriterion> userScoredCriteria2 = null;

		// Act
		List<ScoredTechnology> scoredTechnologies1 = this.scoreEngine
				.score(userScoredCriteria1);
		List<ScoredTechnology> scoredTechnologies2 = this.scoreEngine
				.score(userScoredCriteria2);

		// Assert
		scoredTechnologies1.stream().forEach(
				scoredTech -> assertEquals(0, scoredTech.getTotalScore()));
		scoredTechnologies2.stream().forEach(
				scoredTech -> assertEquals(0, scoredTech.getTotalScore()));

		LOG.debug("Test passed.");
	}

	@Test
	public void noTechProperties() throws URISyntaxException {

		// Arrange
		List<UserScoredCriterion> userScoredCriteria = Arrays
				.asList(createUserScoredCriterionStrongValue("target_os",
						"blackberry"),
						createUserScoredCriterionStrongValue("target_os",
								"windows_phone"));

		// Act
		List<ScoredTechnology> scoredTechnologies = this.scoreEngine
				.score(userScoredCriteria);

		// Prepare assertion
		Optional<ScoredTechnology> optTech1 = scoredTechnologies
				.stream()
				.filter(st -> "tech_no_property_1".equals(st.getTechnologyId()))
				.findAny();
		Optional<ScoredTechnology> optTech2 = scoredTechnologies
				.stream()
				.filter(st -> "tech_no_property_2".equals(st.getTechnologyId()))
				.findAny();

		// Assert
		assertTrue(optTech1.isPresent());
		assertTrue(optTech2.isPresent());

		assertEquals(0, optTech1.get().getTotalScore());
		assertEquals(0, optTech2.get().getTotalScore());

		LOG.debug("Test passed.");
	}

	@Test
	public void simpleStrongValues() throws DroolsParserException, IOException,
			URISyntaxException {

		// Arrange
		List<UserScoredCriterion> userScoredCriteria = Arrays.asList(
				createUserScoredCriterionStrongValue("target_os", "android"),
				createUserScoredCriterionStrongValue("target_os", "ios"));

		// Act
		List<ScoredTechnology> scoredTechnologies = this.scoreEngine
				.score(userScoredCriteria);

		// Prepare assertion
		Optional<ScoredTechnology> optTechiOSAndroid = scoredTechnologies
				.stream()
				.filter(st -> "tech_ios_android".equals(st.getTechnologyId()))
				.findAny();
		Optional<ScoredTechnology> optTechAndroid = scoredTechnologies.stream()
				.filter(st -> "tech_android".equals(st.getTechnologyId()))
				.findAny();
		Optional<ScoredTechnology> optTechNone = scoredTechnologies.stream()
				.filter(st -> "tech_none".equals(st.getTechnologyId()))
				.findAny();

		// Assert
		assertTrue(optTechiOSAndroid.isPresent());
		assertTrue(optTechAndroid.isPresent());
		assertTrue(optTechNone.isPresent());
		
		assertEquals(50, optTechiOSAndroid.get().getTotalScore());
		assertEquals(25, optTechAndroid.get().getTotalScore());
		assertEquals(0, optTechNone.get().getTotalScore());

		LOG.debug("Test passed.");
	}

	@Test
	public void simpleRatedValues() {

		// Arrange
		List<UserScoredCriterion> userScoredCriteria = Arrays
				.asList(createUserScoredCriterionRatedValue("nfr",
						"graphic_performance", 3),
						createUserScoredCriterionRatedValue("nfr",
								"energy_efficiency", 2),
						createUserScoredCriterionRatedValue("nfr",
								"shared_codebase", 1));

		// Act
		List<ScoredTechnology> scoredTechnologies = this.scoreEngine
				.score(userScoredCriteria);

		// Prepare assertion
		Optional<ScoredTechnology> optTechNFREnergy5 = scoredTechnologies
				.stream()
				.filter(st -> "tech_nfr_energy_5".equals(st.getTechnologyId()))
				.findAny();
		Optional<ScoredTechnology> optTechNFRGraphic1 = scoredTechnologies
				.stream()
				.filter(st -> "tech_nfr_graphic_1".equals(st.getTechnologyId()))
				.findAny();
		Optional<ScoredTechnology> optTechNFRGraphic2Energy3 = scoredTechnologies
				.stream()
				.filter(st -> "tech_nfr_graphic_2_energy_3".equals(st
						.getTechnologyId())).findAny();
		Optional<ScoredTechnology> optTechNFRGraphic4 = scoredTechnologies
				.stream()
				.filter(st -> "tech_nfr_graphic_4".equals(st.getTechnologyId()))
				.findAny();
		Optional<ScoredTechnology> optTechNFRNone = scoredTechnologies.stream()
				.filter(st -> "tech_nfr_none".equals(st.getTechnologyId()))
				.findAny();

		// Assert
		assertTrue(optTechNFREnergy5.isPresent());
		assertTrue(optTechNFRGraphic1.isPresent());
		assertTrue(optTechNFRGraphic2Energy3.isPresent());
		assertTrue(optTechNFRGraphic4.isPresent());
		assertTrue(optTechNFRNone.isPresent());

		assertEquals(10, optTechNFREnergy5.get().getTotalScore());
		assertEquals(3, optTechNFRGraphic1.get().getTotalScore());
		assertEquals(12, optTechNFRGraphic2Energy3.get().getTotalScore());
		assertEquals(12, optTechNFRGraphic4.get().getTotalScore());
		assertEquals(0, optTechNFRNone.get().getTotalScore());

		LOG.debug("Test passed.");
	}

	/**
	 * Here we test user scored criteria with rated value, where as technologies
	 * have strong values.
	 */
	@Test
	public void uscRatedValueButTechnolgyPropertyStrong() {

		// Arrange
		List<UserScoredCriterion> userScoredCriteria = Arrays.asList(
				createUserScoredCriterionRatedValue("target_os", "android", 3),
				createUserScoredCriterionRatedValue("target_os", "ios", 1));

		// Act
		List<ScoredTechnology> scoredTechnologies = this.scoreEngine
				.score(userScoredCriteria);

		// Prepare asserion
		Optional<ScoredTechnology> optTechAndroid = scoredTechnologies.stream()
				.filter(st -> "tech_android".equals(st.getTechnologyId()))
				.findAny();
		Optional<ScoredTechnology> optTechiOSAndroid = scoredTechnologies
				.stream()
				.filter(st -> "tech_ios_android".equals(st.getTechnologyId()))
				.findAny();
		Optional<ScoredTechnology> optTechNone = scoredTechnologies.stream()
				.filter(st -> "tech_none".equals(st.getTechnologyId()))
				.findAny();

		// Assert
		assertTrue(optTechAndroid.isPresent());
		assertTrue(optTechiOSAndroid.isPresent());
		assertTrue(optTechNone.isPresent());

		assertEquals(15, optTechAndroid.get().getTotalScore());
		assertEquals(20, optTechiOSAndroid.get().getTotalScore());
		assertEquals(0, optTechNone.get().getTotalScore());

		LOG.debug("Test passed.");
	}

	@Test
	public void complexValues() {

		// Arrange
		List<UserScoredCriterion> userScoredCriteria = Arrays
				.asList(createUserScoredCriterionStrongValue("target_os",
						"android"),
						createUserScoredCriterionStrongValue("target_os", "ios"),
						createUserScoredCriterionRatedValue("target_os",
								"windows_phone", 4),
						createUserScoredCriterionStrongValue("monetization",
								"app_store_availablity"),
						createUserScoredCriterionRatedValue("monetization",
								"time_to_market", 5),
						createUserScoredCriterionRatedValue("nfr",
								"native_feeling", 2),
						createUserScoredCriterionRatedValue("nfr",
								"offline_capability", 1));

		// Act
		List<ScoredTechnology> scoredTechnologies = this.scoreEngine
				.score(userScoredCriteria);

		// Prepare assertion
		Optional<ScoredTechnology> optTechiOSAndroidMonetizationAppStoreTimeToMarket4 = scoredTechnologies
				.stream()
				.filter(st -> "tech_target_ios_android_monetization_appstore_timetomarket_4"
						.equals(st.getTechnologyId())).findAny();
		Optional<ScoredTechnology> optTechWindowsPhoneMonetizationAppStoreNFROffline4Native1 = scoredTechnologies
				.stream()
				.filter(st -> "tech_target_windows_phone_monetization_appstore_nfr_offline_4_native_1"
						.equals(st.getTechnologyId())).findAny();

		// Assert
		assertTrue(optTechiOSAndroidMonetizationAppStoreTimeToMarket4
				.isPresent());
		assertTrue(optTechWindowsPhoneMonetizationAppStoreNFROffline4Native1
				.isPresent());

		assertEquals(95, optTechiOSAndroidMonetizationAppStoreTimeToMarket4
				.get().getTotalScore());
		assertEquals(51,
				optTechWindowsPhoneMonetizationAppStoreNFROffline4Native1.get()
						.getTotalScore());

		LOG.debug("Test passed.");
	}

	// ------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------
	// INTERNAL STUFF: Create user scored criteria
	// ------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------

	private UserScoredCriterion createUserScoredCriterionStrongValue(
			String criterionId, String criterionValueId) {
		return createUserScoredCriterion(criterionId, criterionValueId, true, 0);
	}

	private UserScoredCriterion createUserScoredCriterionRatedValue(
			String criterionId, String criterionValueId, int ratedValue) {
		return createUserScoredCriterion(criterionId, criterionValueId, false,
				ratedValue);
	}

	private UserScoredCriterion createUserScoredCriterion(String criterionId,
			String criterionValueId, boolean strongValue, int ratedValue) {
		UserScoredCriterion usc = new UserScoredCriterion();
		usc.setCriterionId(criterionId);
		usc.setCriterionValueId(criterionValueId);
		usc.setStrongValue(strongValue);
		usc.setRatedValue(ratedValue);

		return usc;
	}
}
