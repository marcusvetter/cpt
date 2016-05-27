cptApp.factory('dataService', ['backendService',
	function (backendService) {

		var profileName = "default";

		// --------------------------------------------------------------------------------
		// Technologies
		// --------------------------------------------------------------------------------
		var technologies = [];
		
		function getTechnologyById(technologyId) {
			var result;
			angular.forEach(technologies, function(technology) {
				if (technology.id === technologyId) {
					result = technology;
				}
			});
			return result;
		}
		
		// --------------------------------------------------------------------------------
		// Criteria
		// --------------------------------------------------------------------------------
		var criteria = [];
		var totalCriteria = 0;
		
		function getCriterionById(criterionId) {
			var result;
			angular.forEach(criteria, function(criterion) {
				if (criterion.id === criterionId) {
					result = criterion;
				}
			});
			return result;
		}

		function getCriterionByPageNumber(pageNumber) {
			var result;
			angular.forEach(criteria, function(criterion) {
				if (pageNumber === criterion.pageNumber) {
					result = criterion;
				}
			});
			return result;
		}
		
		// --------------------------------------------------------------------------------
		// Criterion Values
		// --------------------------------------------------------------------------------
		function getCriterionValuesByCriterionId(criterionId) {
			var result;
			angular.forEach(criteria, function(criterion) {
				if (criterion.id === criterionId) {
					result = criterion.values;
				}
			});
			return result;
		}

		function getCriterionValueByIds(criterionId, criterionValueId) {
			var criterionValues = getCriterionValuesByCriterionId(criterionId);
			var result;
			angular.forEach(criterionValues, function(criterionValue) {
				if (criterionValue.id === criterionValueId) {
					result = criterionValue;
				}
			});
			return result;
		}
		

		// --------------------------------------------------------------------------------
		// Initialization: Load all criteria and technologies
		// --------------------------------------------------------------------------------
		function initialize(callback) {
			criteria = [];
			totalCriteria = 0;
			technologies = [];

			backendService.getCriteria(profileName, function(data) {

				// Sort the criteria (property: sort)
				criteria = data.sort(
					function(a, b) {
						return a.sort > b.sort;
					}
				);
				
				var i = 0;
				angular.forEach(criteria, function(criterion) {
					criterion.showDetails = false;
					criterion.pageNumber = i;
					i++;
					
					// Default userScoredCriteria
					angular.forEach(criterion.values, function(criterionValue) {
						if (criterionValue.id === "android" || criterionValue.id === "ios") {
							criterionValue.usc = {
									strongValue:  criterionValue.scoreType.indexOf("strong") > -1,
									ratedValue: 3 // default value
							};
						}
					});
				});

				totalCriteria = criteria.length;

				backendService.getTechnologies(profileName, function(data) {

					// Sort the technologies (property: name)
					technologies = data.sort(function(a, b){
						if(a.name < b.name) return -1;
						if(a.name > b.name) return 1;
						return 0;
					});

					angular.forEach(technologies, function(technology){
						technology.showProperties = false;

						angular.forEach(technology.properties, function(property) {
							var criterionId = property.criterionId;

							if (getCriterionById(criterionId) !== undefined) {
								property.criterionName = getCriterionById(criterionId).name;
							
								angular.forEach(property.strongValues, function(strongValue) {
									var criterionValueName = getCriterionValueByIds(criterionId, strongValue.id).name;
									strongValue.name = criterionValueName;
								});

								angular.forEach(property.ratedValues, function(ratedValue) {
									var criterionValueName = getCriterionValueByIds(criterionId, ratedValue.id).name;
									ratedValue.name = criterionValueName;
								});
							}

						});
					});

					callback();

				}, function(error) {
					console.error(error);
				});

			}, function(error) {
				console.error(error);
			});
		}
				
		return {
			initialize: function(callback) {
				initialize(callback);
			},
			
			getTechnologies: function() {
				return technologies;
			},
			
			getTechnologyById: function(technologyId) {
				return getTechnologyById(technologyId);
			},
			
			getCriteria: function() {
				return criteria;
			},
			
			getCriterionById: function(criterionId) {
				return getCriterionById(criterionId);
			},
			
			getCriterionValuesByCriterionId: function(criterionId) {
				return getCriterionValuesByCriterionId(criterionId);
			},
			
			getCriterionByPageNumber: function(pageNumber) {
				return getCriterionByPageNumber(pageNumber);
			},
			
			getCriterionValueByIds: function(criterionId, criterionValueId) {
				return getCriterionValueByIds(criterionId, criterionValueId);
			},

			setProfile: function(profileNameToSet, callback) {
				profileName = profileNameToSet;
				initialize(callback);
			},

			getProfileName: function() {
				return profileName;
			},

			setCriteria: function (crit) {
				criteria = crit;
			}

		};
	}
]);