package org.crossplatform.backend.model.technology;

import java.util.ArrayList;
import java.util.List;

public class ScoredTechnology {
	
	private String technologyId;
	private int totalScore = 0;
	private List<ScoreElement> scoreElements;
	
	public ScoredTechnology(String technologyId) {
		this.setTechnologyId(technologyId);
		this.scoreElements = new ArrayList<>();
	}
	
	public String getTechnologyId() {
		return technologyId;
	}
	
	public int getTotalScore() {
		return totalScore;
	}
	
	public void setTechnologyId(String technologyId) {
		this.technologyId = technologyId;
	}
	
	private void increaseTotalScore(int points) {
		this.totalScore += points;
	}
	
	public void addScoreElement(String criterionId, String criterionValueId, int points, int maxPoints) {
		increaseTotalScore(points);
		ScoreElement scoreElement = new ScoreElement(criterionId, criterionValueId, points, maxPoints);
		this.scoreElements.add(scoreElement);
	}
	
	public List<ScoreElement> getScoreElements() {
		return this.scoreElements;
	}
	
	static class ScoreElement {
		
		private String criterionId;
		private String criterionValueId;
		private int points;
		private int maxPoints;
		
		public ScoreElement(String criterionId, String criterionValueId,
				int points, int maxPoints) {
			this.setCriterionId(criterionId);
			this.setCriterionValueId(criterionValueId);
			this.setPoints(points);
			this.setMaxPoints(maxPoints);
		}
		
		public ScoreElement() {
			
		}

		public String getCriterionId() {
			return criterionId;
		}

		public String getCriterionValueId() {
			return criterionValueId;
		}

		public int getPoints() {
			return points;
		}
		
		public int getMaxPoints() {
			return maxPoints;
		}

		public void setCriterionId(String criterionId) {
			this.criterionId = criterionId;
		}

		public void setCriterionValueId(String criterionValueId) {
			this.criterionValueId = criterionValueId;
		}

		public void setPoints(int points) {
			this.points = points;
		}
		
		public void setMaxPoints(int maxPoints) {
			this.maxPoints = maxPoints;
		}
		
	}
	
}
