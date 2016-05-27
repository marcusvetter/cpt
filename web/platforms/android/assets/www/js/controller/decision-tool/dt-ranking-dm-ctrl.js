// Decision Tool Ranking Direct Manipulation Controller
cptApp.controller('DTRankingDMCtrl', [ '$scope', 'dataService',
	function($scope, dataService) {

		$scope.getUserScoredCriteriaByCriterionId = function(criterionId) {
			var userScoredCriteria = [];
			angular.forEach($scope.userScoredCriteria, function(usc) {
				if (usc.criterionId === criterionId) {
					userScoredCriteria.push(usc);
				}
			});
			return userScoredCriteria;
		};

		// --------------------------------------------------------------------------------
		// User Scored Criteria
		// --------------------------------------------------------------------------------
		function addUserScoredCriterion(criterionValue) {
			criterionValue.usc = {
					strongValue:  criterionValue.scoreType.indexOf("strong") > -1,
					ratedValue: 3 // default value
				};
		}
		
		function removeUserScoredCriterion(criterionValue) {
			criterionValue.usc = undefined;
		}
		
		$scope.toggleUserScoredCriterion = function(criterion, criterionValue) {
			if (criterionValue.usc === undefined) {
				addUserScoredCriterion(criterionValue);
			} else {
				removeUserScoredCriterion(criterionValue);
			}
		};

		// Update the ranking
		$scope.updateRanking = function() {
			$scope.$parent.calculateRanking();
		};

	}
]);