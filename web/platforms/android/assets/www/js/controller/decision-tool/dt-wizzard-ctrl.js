cptApp.controller('DTWizzardCtrl', [ '$scope', 'dataService', function($scope, dataService) {

	// --------------------------------------------------------------------------------
	// Criteria
	// --------------------------------------------------------------------------------
	$scope.criteria = dataService.getCriteria();
	$scope.totalCriteria = $scope.criteria.length;

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
	
	// --------------------------------------------------------------------------------
	// Toolbar
	// --------------------------------------------------------------------------------
	function checkAll(check) {
		angular.forEach($scope.criteria, function(criterion) {
			if (criterion.pageNumber === $scope.currentPage) {
				angular.forEach(dataService.getCriterionValuesByCriterionId(criterion.id), function(criterionValue) {
					if (check && criterionValue.usc === undefined) {
						addUserScoredCriterion(criterionValue);
					} else if (!check && criterionValue.usc !== undefined) {
						removeUserScoredCriterion(criterionValue);
					}
				});
			}
		});
	}

	$scope.allChecked = false;
	$scope.toggleCheckAll = function() {
		checkAll(!$scope.allChecked);
		$scope.allChecked = !$scope.allChecked;
	};

	// Filter
	$scope.visibleCriterionValueFilterValue = "";
	
	// --------------------------------------------------------------------------------
	// Page management
	// --------------------------------------------------------------------------------
	$scope.currentPage = 0;

	$scope.nextPage = function() {
		if ($scope.currentPage === $scope.totalCriteria - 1) {
			$scope.changeStateTo("RANKING");
		} else {
			$scope.currentPage++;
			$scope.updatePage();
		}
	};

	$scope.previousPage = function() {
		if ($scope.currentPage === 0) {
			$scope.changeStateTo("STARTPAGE");
		} else {
			$scope.currentPage--;
			$scope.updatePage();
		}
	};

	$scope.updatePage = function() {
	
		// Clear the toolbar
		$scope.visibleCriterionValueFilterValue = "";
		$scope.allChecked = false;

		// Scroll to top
		$scope.scrollToTop();
	};
	
	$scope.updatePage();

} ]);