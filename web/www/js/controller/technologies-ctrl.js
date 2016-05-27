cptApp.controller('TechnologiesCtrl', ['$scope', 'dataService',
	function ($scope, dataService) {

		$scope.toggleProperties = function(technology) {
			technology.showProperties = !technology.showProperties;
		};

		// Load technologies
		$scope.technologies = dataService.getTechnologies();
		if (undefined === $scope.technologies || 0 === $scope.technologies.length) {
			// Wait for the technologies
			$scope.$on("INITIALIZED_DATA_SERVICE", function() {
				$scope.technologies = dataService.getTechnologies();
				$scope.$emit("HIDE_LOADING_SCREEN");
			});
			$scope.$emit("SHOW_LOADING_SCREEN");
		}

	}
]);