package org.crossplatform.backend.scoreengine;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.crossplatform.backend.model.criterion.UserScoredCriterion;
import org.crossplatform.backend.model.datamodel.AbstractDataModel;
import org.crossplatform.backend.model.technology.ScoredTechnology;
import org.drools.compiler.compiler.DroolsError;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.compiler.compiler.PackageBuilderErrors;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;
import org.drools.core.rule.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScoreEngine {

	private static final Logger LOG = LoggerFactory
			.getLogger(ScoreEngine.class);

	private AbstractDataModel dataModel;
	private RuleBase ruleBase;

	public ScoreEngine(AbstractDataModel dataModel) {
		this.dataModel = dataModel;

		// Initialize
		try {
			ruleBase = this.initializeDrools();
		} catch (DroolsParserException | IOException e) {
			LOG.error(e.getMessage());
		}

	}

	public List<ScoredTechnology> score(
			List<UserScoredCriterion> givenUserScoredCriteria) {
		List<UserScoredCriterion> userScoredCriteria = givenUserScoredCriteria;
		if (null == userScoredCriteria) {
			userScoredCriteria = new ArrayList<>();
		}

		// The working memory
		WorkingMemory workingMemory = ruleBase.newStatefulSession();

		// Insert the user scored criteria
		userScoredCriteria.stream().forEach(usc -> workingMemory.insert(usc));

		// Insert all criteria and their criterion values
		this.dataModel
				.getCriteria()
				.stream()
				.forEach(
						c -> {
							workingMemory.insert(c);
							this.dataModel.getCriterionValuesByCriterion(c)
									.stream()
									.forEach(cv -> workingMemory.insert(cv));
						});

		// Insert all technologies, their technology properties (and related
		// rated values) and instantiate a new scored technology for each
		// technology
		List<ScoredTechnology> scoredTechnologies = new ArrayList<>();
		this.dataModel
				.getTechnologies()
				.stream()
				.forEach(
						t -> {
							workingMemory.insert(t);
							this.dataModel
									.getTechnologyPropertiesByTechnology(t)
									.stream()
									.forEach(
											prop -> {
												workingMemory.insert(prop);
												if (null != prop
														.getRatedValues()) {
													Arrays.asList(
															prop.getRatedValues())
															.stream()
															.forEach(
																	rv -> workingMemory
																			.insert(rv));
												}
												if (null != prop
														.getStrongValues()) {
													Arrays.asList(
															prop.getStrongValues())
															.stream()
															.forEach(
																	sv -> workingMemory
																			.insert(sv));
												}
											});
							ScoredTechnology scoredTech = new ScoredTechnology(
									t.getId());
							scoredTechnologies.add(scoredTech);
							workingMemory.insert(scoredTech);
						});

		// Execute
		workingMemory.fireAllRules();

		return scoredTechnologies;
	}

	/**
	 * INTERNAL: Initialize the rule base (drools)
	 * 
	 * @return
	 * @throws IOException
	 * @throws DroolsParserException
	 */
	private RuleBase initializeDrools() throws IOException,
			DroolsParserException {

		// Read rule files
		PackageBuilder packageBuilder = new PackageBuilder();

		String ruleFile = "/drools/scoring.drl";
		InputStream resourceAsStream = this.getClass().getResourceAsStream(
				ruleFile);
		Reader reader = new InputStreamReader(resourceAsStream);
		packageBuilder.addPackageFromDrl(reader);

		// Check rule files (parse errors)
		PackageBuilderErrors errors = packageBuilder.getErrors();

		if (errors.getErrors().length > 0) {
			StringBuilder errorMessages = new StringBuilder();
			errorMessages.append("Found errors in package builder\n");
			for (int i = 0; i < errors.getErrors().length; i++) {
				DroolsError errorMessage = errors.getErrors()[i];
				errorMessages.append(errorMessage);
				errorMessages.append("\n");
			}
			errorMessages.append("Could not parse knowledge");

			throw new IllegalArgumentException(errorMessages.toString());
		}

		// Add rules to working memory
		RuleBase newRuleBase = RuleBaseFactory.newRuleBase();
		Package rulesPackage = packageBuilder.getPackage();
		newRuleBase.addPackage(rulesPackage);

		return newRuleBase;
	}

}
