cptApp.controller('DTRankingComparisonCtrl', [ '$scope', 'dataService',
	function($scope, dataService) {

		$scope.comparisonChartType = "Bar";

		$scope.compareTechnologies = {
			"first": "",
			"second": "",
			"third": ""
		};

		$scope.$watchCollection("compareTechnologies", function() {
			$scope.draw();
		});

		$scope.getCompareTechnologyLabel = function(scoredTechnology) {
			return $scope.getTechnologyById(scoredTechnology.technologyId).name + ' (' + scoredTechnology.totalScore + ' P.)';
		};

		$scope.getPointsForAccumulatedUSC = function(technologyId, criterionId) {
			var result = 0;
				angular.forEach(dataService.getCriterionValuesByCriterionId(criterionId), function(criterionValue) {
					result += $scope.getPointsForUSC(technologyId, criterionId, criterionValue.id);
				});
			return result;
		};

		var colors = [
			"rgba(0, 51, 102, 0.7)", // #003366
			"rgba(0, 51, 102, 1)",
			"rgba(51, 153, 255, 0.7)", // #3399FF
			"rgba(51, 153, 255, 1)",
			"rgba(255, 153, 102, 0.7)", // #FF9966
			"rgba(255, 153, 102, 1)"
		];

		$scope.draw = function() {

			// Get the labels and datasets
			var labels = [];
			var datasets = [];
			var colorIterator = 0;

			function addDatasets(compareTechnologyId) {

				console.log("add: " + compareTechnologyId);

				if (compareTechnologyId !== "" && compareTechnologyId !== undefined && compareTechnologyId !== null && compareTechnologyId !== "null") {
					var data = [];

					angular.forEach($scope.criteria, function(criterion) {
						var points = $scope.getPointsForAccumulatedUSC(compareTechnologyId, criterion.id);
						data.push(points);

						// Get the label
						if (labels.indexOf(criterion.name) === -1) {
							labels.push(criterion.name);
						}
					});

					datasets.push({
						label: compareTechnologyId,
						fillColor: colors[colorIterator],
						strokeColor: colors[colorIterator + 1],
						pointColor: colors[colorIterator + 1],
						pointStrokeColor: "#fff",
						pointHighlightFill: "#fff",
						pointHighlightStroke: colors[colorIterator + 1],
						data: data
					});
				}
				
				colorIterator += 2;
			}

			addDatasets($scope.compareTechnologies.first);
			addDatasets($scope.compareTechnologies.second);
			addDatasets($scope.compareTechnologies.third);

			// Aggregate the data
			var data = {
				labels: labels,
				datasets: datasets
			};

			$scope.comparisonChart = {"data": data, "options": {bezierCurve: false, showTooltips: false, responsive: false, animation: false }};
			
		};

		// If the ranking changes, update the diagram
		$scope.$watch("ranking", function() {
			$scope.draw();
		});
}]);