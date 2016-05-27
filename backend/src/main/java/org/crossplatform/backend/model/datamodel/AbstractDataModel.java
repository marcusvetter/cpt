package org.crossplatform.backend.model.datamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.crossplatform.backend.model.criterion.Criterion;
import org.crossplatform.backend.model.criterion.CriterionValue;
import org.crossplatform.backend.model.technology.Technology;
import org.crossplatform.backend.model.technology.TechnologyProperty;

public abstract class AbstractDataModel {
	private List<Technology> technologies;
	private List<Criterion> criteria;
	
	public List<Technology> getTechnologies() {
		return technologies;
	}

	public List<Criterion> getCriteria() {
		return criteria;
	}
	
	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}
	
	public void setCriteria(List<Criterion> criteria) {
		this.criteria = criteria;
	}
	
	public List<TechnologyProperty> getTechnologyPropertiesByTechnology(Technology technology) {
		Optional<Technology> opt = this.getTechnologies().stream().filter(t -> t.equals(technology)).findAny();
		return opt.isPresent() ? Arrays.asList(opt.get().getProperties()) : new ArrayList<TechnologyProperty>();
	}
	
	public Criterion getCriterionById(String criterionId) {
		Optional<Criterion> opt = this.getCriteria().stream().filter(c -> c.getId().equals(criterionId)).findAny();
		return opt.isPresent() ? opt.get() : null;
	}
	
	public List<CriterionValue> getCriterionValuesByCriterion(Criterion criterion) {
		Optional<Criterion> opt = this.getCriteria().stream().filter(c -> c.equals(criterion)).findAny();
		return opt.isPresent() ? Arrays.asList(opt.get().getValues()) : new ArrayList<CriterionValue>();
	}
}
