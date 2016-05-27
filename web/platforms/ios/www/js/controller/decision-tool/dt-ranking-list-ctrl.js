cptApp.controller('DTRankingListCtrl', [ '$scope',
	function($scope) {

		// Return values:
		// relativePoints(p) = 0 (required usc): -1
		// relativePoints(p) = 0 (non-required usc): 0
		// relativePoints(p) < 33: 1
		// relativePoints(p) < 66: 2
		// relativePoints(p) >= 66: 3
		$scope.getFragementForUSC = function(techonologyId, criterionId, criterionValueId) {
			var points = 0;
			var maxPoints = 1;
			var strongCV = false;

			// Get the points and max points for the given technology and criterion value
			angular.forEach($scope.ranking, function(technology) {
				if (technology.technologyId === techonologyId) {
					angular.forEach(technology.scoreElements, function(scoreElement) {
						if (scoreElement.criterionId === criterionId && scoreElement.criterionValueId === criterionValueId) {
							points = scoreElement.points;
							maxPoints = scoreElement.maxPoints;
						}
					});
				}
			});

			// Check if the discovered criterion value is a strong user requirement
			angular.forEach($scope.userScoredCriteria, function(usc) {
				if (usc.criterionId === criterionId && usc.criterionValueId === criterionValueId && usc.strongValue) {
					strongCV = true;
				}
			});

			var relativePoints = Math.round(parseInt(points, 10) / parseInt(maxPoints, 10) * 100);
			return relativePoints === 0 ? (strongCV ? -1 : 0) : relativePoints > 66 ? 3 : relativePoints > 33 ? 2 : 1;
		};
	
		$scope.reqViolationFilter = false;

}]);