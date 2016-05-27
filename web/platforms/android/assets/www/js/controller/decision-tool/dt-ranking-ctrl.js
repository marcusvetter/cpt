cptApp.controller('DTRankingCtrl', [ '$scope', 'dataService', 'backendService',
	function($scope, dataService, backendService) {

		// --------------------------------------------------------------------------------
		// Data: Technologies, Criteria and Criterion Values
		// --------------------------------------------------------------------------------
		$scope.criteria = dataService.getCriteria();
		$scope.technoloies = dataService.getTechnologies();
		$scope.userScoredCriteria = [];

		$scope.getTechnologyById = function(technologyId) {
			return dataService.getTechnologyById(technologyId);
		};
	
		$scope.getCriterionById = function(criterionId) {
			return dataService.getCriterionById(criterionId);
		};

		$scope.getCriterionValueByIds = function(criterionId, criterionValueId) {
			return dataService.getCriterionValueByIds(criterionId, criterionValueId);
		};

		// --------------------------------------------------------------------------------
		// Ranking methods
		// --------------------------------------------------------------------------------
		$scope.ranking = [];
		function updateUserScoredCriteria() {
			$scope.userScoredCriteria = [];
			angular.forEach($scope.criteria, function(criterion) {
				angular.forEach(dataService.getCriterionValuesByCriterionId(criterion.id), function(criterionValue) {
					if (criterionValue.usc !== undefined) {
						$scope.userScoredCriteria.push({
							criterionId: criterion.id,
							criterionValueId: criterionValue.id,
							strongValue:  criterionValue.usc.strongValue,
							ratedValue: criterionValue.usc.ratedValue
						});
					}
				});
			});
		}
	
		$scope.calculateRanking = function() {
			
			updateUserScoredCriteria();

			$scope.anyTechnologyViolatesACriterion = false;

			backendService.calculateRanking(angular.toJson($scope.userScoredCriteria), function(data) {
				$scope.ranking = data.sort(function(a, b) {
					return b.totalScore - a.totalScore;
				});

				// Check if any requirement was violated
				angular.forEach($scope.ranking, function(scoredTechnology) {
					scoredTechnology.anyRequirementViolated = false;

					angular.forEach($scope.userScoredCriteria, function(usc) {
						var scoreElementPresent = false;
						angular.forEach(scoredTechnology.scoreElements, function(scoreElement) {
							if (usc.criterionId === scoreElement.criterionId && usc.criterionValueId === scoreElement.criterionValueId) {
								scoreElementPresent = true;
							}
						});
						if (usc.strongValue && !scoreElementPresent) {
							scoredTechnology.anyRequirementViolated = true;
							$scope.anyTechnologyViolatesACriterion = true;
						}
					});
				});

			}, function(error) {
				console.error(error);
			});
		};
	
		$scope.getPointsForUSC = function(techonologyId, criterionId, criterionValueId) {
			var result;
			angular.forEach($scope.ranking, function(technology) {
				if (technology.technologyId === techonologyId) {
					angular.forEach(technology.scoreElements, function(scoreElement) {
						if (scoreElement.criterionId === criterionId && scoreElement.criterionValueId === criterionValueId) {
							result = scoreElement.points;
						}
					});
				}
			});
			return result === undefined ? 0 : result;
		};
	
		// Calculate the ranking as soon as the controller becomes bootstrapped
		$scope.calculateRanking();

}]);