cptApp.controller('CriteriaCtrl', [ '$scope', 'dataService',
		function($scope, dataService) {

			// Toggle details
			$scope.toggleDetails = function(criterion) {
				criterion.showDetails = !criterion.showDetails;
			};

			// Just to display the rating stars
			$scope.rateTemplate = 5;

			// Load all criteria
			$scope.$on("INITIALIZED_DATA_SERVICE", function() {
				$scope.criteria = dataService.getCriteria();
			});
			$scope.criteria = dataService.getCriteria();

		} ]);