cptApp.controller('DTDataCtrl', [ '$scope', 'backendService',
		function($scope, backendService) {
	
		// --------------------------------------------------------------------------------
		// User scored criteria
		// --------------------------------------------------------------------------------
	
		$scope.userScoredCriteria = [];
	
		function addUserScoredCriterion(criterionId, criterionValueId) {
			if (!$scope.isUserScoredCriterionPresent(criterionId, criterionValueId)) {
				var criterionValue = $scope.getCriterionValueByIds(criterionId, criterionValueId);
				var strongValue = criterionValue.scoreType.indexOf("strong") > -1;
				$scope.userScoredCriteria.push({
					criterionId: criterionId,
					criterionValueId: criterionValueId,
					strongValue: strongValue,
					ratedValue: 3 // default value
				});
			}
		}
	
		function deleteUserScoredCriterion(criterionId, criterionValueId) {
			var uscToRemove;
			angular.forEach($scope.userScoredCriteria, function(usc) {
				if (usc.criterionId === criterionId && usc.criterionValueId === criterionValueId) {
					uscToRemove = usc;
				}
			});
	
			var index = $scope.userScoredCriteria.indexOf(uscToRemove);
			if (index > -1) {
				$scope.userScoredCriteria.splice(index, 1);
			}
		}
	
		$scope.isUserScoredCriterionPresent = function(criterionId, criterionValueId) {
			var uscPresent = false;
			angular.forEach($scope.userScoredCriteria, function(userScoredCriterion) {
				if (userScoredCriterion.criterionId === criterionId && userScoredCriterion.criterionValueId === criterionValueId) {
					uscPresent = true;
				}
			});
	
			return uscPresent;
		};
	
		$scope.toggleUserScoredCriterion = function(criterionId, criterionValueId) {
			var uscPreset = $scope.isUserScoredCriterionPresent(criterionId, criterionValueId);
	
			if (uscPreset) {
				deleteUserScoredCriterion(criterionId, criterionValueId);
			} else {
				addUserScoredCriterion(criterionId, criterionValueId);
			}
		};
	
		$scope.getUserScoredCriterion = function (criterionId, criterionValueId) {
			var result;
			angular.forEach($scope.userScoredCriteria, function(userScoredCriterion) {
				if (userScoredCriterion.criterionId === criterionId && userScoredCriterion.criterionValueId === criterionValueId) {
					result = userScoredCriterion;
				}
			});
	
			return result;
		};
		
		
		// --------------------------------------------------------------------------------
		// Register event listener
		// --------------------------------------------------------------------------------
		
		$scope.$on('addUserScoredCriterion', function(_, criterionId, criterionValueId) {
			addUserScoredCriterion(criterionId, criterionValueId);
		});
		
		$scope.$on('deleteUserScoredCriterion', function(_, criterionId, criterionValueId) {
			deleteUserScoredCriterion(criterionId, criterionValueId);
		});
}]);