cptApp.controller('CriteriaCtrl', [ '$scope', 'dataService',
	function ($scope, dataService) {

		// Toggle details
		$scope.toggleDetails = function(criterion) {
			criterion.showDetails = !criterion.showDetails;
		};

		// Just to display the rating stars
		$scope.rateTemplate = 5;

		// Load criteria
		$scope.criteria = dataService.getCriteria();
		if (undefined === $scope.criteria || 0 === $scope.criteria.length) {
			// Wait for the criteria
			$scope.$on("INITIALIZED_DATA_SERVICE", function() {
				$scope.criteria = dataService.getCriteria();
				$scope.$emit("HIDE_LOADING_SCREEN");
			});
			$scope.$emit("SHOW_LOADING_SCREEN");
		}

	}
]);