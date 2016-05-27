cptApp.controller('TechnologiesCtrl', ['$scope', 'dataService',
	function ($scope, dataService) {

		$scope.toggleProperties = function(technology) {
			technology.showProperties = !technology.showProperties;
		};
		
		// Load all technologies
		$scope.$on("INITIALIZED_DATA_SERVICE", function() {
			$scope.technologies = dataService.getTechnologies();
		});
		$scope.technologies = dataService.getTechnologies();

	}
]);