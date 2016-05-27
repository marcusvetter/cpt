cptApp.factory('backendService', ['$http',
	function ($http) {

		var baseUrl = "http://cross-platform.org/api/";

		return {
			// Get all technologies
			getTechnologies: function (profileName, successCallback, errorCallback) {
				$http.get(baseUrl + 'technologies/' + profileName)
					.success(successCallback)
					.error(errorCallback);
			},

			getCriteria: function (profileName, successCallback, errorCallback) {
				$http.get(baseUrl + 'criteria/' + profileName)
					.success(successCallback)
					.error(errorCallback);
			},

			calculateRanking: function(userScoredCriteria, profileName, successCallback, errorCallback) {
				$http.post(baseUrl + 'ranking/' + profileName, userScoredCriteria)
					.success(successCallback)
					.error(errorCallback);
			},

			updateTechnologies: function(profileName, data, successCallback, errorCallback) {
				$http.post(baseUrl + 'technologies/' + profileName, data)
					.success(successCallback)
					.error(errorCallback);
			},

			updateCriteria: function(profileName, data, successCallback, errorCallback) {
				$http.post(baseUrl + 'criteria/' + profileName, data)
					.success(successCallback)
					.error(errorCallback);
			},

			createNewProfile: function(profileName, successCallback, errorCallback) {
				$http.put(baseUrl + 'profiling/' + profileName)
					.success(successCallback)
					.error(errorCallback);
			}
		};
	}
]);