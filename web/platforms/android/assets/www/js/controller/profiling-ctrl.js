cptApp.controller('ProfilingCtrl', [ '$scope', 'dataService', 'backendService',
		function($scope, dataService, backendService) {

			$scope.defaultProfileName = "default";

			$scope.profileName = dataService.getProfileName();
			$scope.showProfilingForm = ($scope.profileName !== $scope.defaultProfileName);

			$scope.$watch("profileName", function(val) {
				if (val !== $scope.defaultProfileName) {
					$scope.$parent.profileNameMenu = val;
				} else {
					$scope.$parent.profileNameMenu = undefined;
				}
			});

			$scope.saveChanges = function() {
				$scope.$broadcast("SAVE_CHANGES");
			};

			$scope.activateProfiling = function(flag) {
				$scope.showProfilingForm = flag;
				$scope.profileNameInput = "";
				if (!flag) {
					$scope.profileName = $scope.defaultProfileName;
					$scope.loadProfile($scope.profileName);
				}
			};

			$scope.loadProfile = function(profileName) {
				dataService.setProfile(profileName, function() {
					$scope.$broadcast("LOAD_TECHNOLOGIES");
					$scope.$broadcast("LOAD_CRITERIA");
					$scope.profileName = profileName;
					$scope.profileNameInput = profileName;
				});
			};

			$scope.createNewProfile = function() {
				backendService.createNewProfile($scope.newProfileName, function(successData) {
					$scope.newProfileStatus = (successData === "true");
					if ($scope.newProfileStatus) {
						$scope.loadProfile($scope.newProfileName);
					}
				}, function(errorData) {
					console.error(errorData);
				});
			};

			if ($scope.profileName !== $scope.defaultProfileName) {
				$scope.loadProfile($scope.profileName);
			}

		}
]);

cptApp.controller('ProfilingTechnologiesCtrl', [ '$scope', 'dataService', 'backendService',
		function($scope, dataService, backendService) {

			$scope.$on("SAVE_CHANGES", function() {
				backendService.updateTechnologies($scope.profileName, JSON.parse($scope.technologies), function(successData) {
					if (successData === "true") {
						dataService.setProfile($scope.profileName, function() {
							loadTechnologies();
						});
					}
				}, function() {
					console.error("Error while updating technologies");
				});
			});

			// Remove all attributes added by angular and the mvc-mechanism
			function loadTechnologies() {
				var result = [];
				angular.forEach(dataService.getTechnologies(), function(t) {
					var technology = angular.copy(t);
					delete technology.showProperties;
					var properties = [];
					angular.forEach(technology.properties, function(prop) {
						delete prop.criterionName;

						// Remove names of strong values
						var strongValues = [];
						angular.forEach(prop.strongValues, function (sVal) {
							delete sVal.name;
							strongValues.push(sVal);
						});
						properties.strongValues = strongValues;

						// Remove names of rated values
						var ratedValues = [];
						angular.forEach(prop.ratedValues, function (rVal) {
							delete rVal.name;
							ratedValues.push(rVal);
						});
						properties.ratedValues = ratedValues;

						// Add the propertie
						properties.push(prop);
					});
					technology.properties = properties;
					result.push(technology);
				});
				$scope.technologies = JSON.stringify(result, null, "\t");
			}

			$scope.$on("LOAD_TECHNOLOGIES", loadTechnologies);
		}
]);

cptApp.controller('ProfilingCriteriaCtrl', [ '$scope', 'dataService', 'backendService',
		function($scope, dataService, backendService) {

			$scope.$on("SAVE_CHANGES", function() {
				backendService.updateCriteria($scope.profileName, JSON.parse($scope.criteria), function(successData) {
					if (successData === "true") {
						dataService.setProfile($scope.profileName, function() {
							loadCriteria();
						});
					}
				}, function() {
					console.error("Error while updating criteria");
				});
			});

			// Remove all attributes added by angular and the mvc-mechanism
			function loadCriteria() {
				var result = [];
				angular.forEach(dataService.getCriteria(), function(c) {
					var criterion = angular.copy(c);
					delete criterion.showDetails;
					delete criterion.pageNumber;
					var values = [];
					angular.forEach(criterion.values, function(value) {
						delete value.usc;
						values.push(value);
					});
					criterion.values = values;
					result.push(criterion);
				});
				$scope.criteria = JSON.stringify(result, null, "\t");
			}

			$scope.$on("LOAD_CRITERIA", loadCriteria);

		}
]);