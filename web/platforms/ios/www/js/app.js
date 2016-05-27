var cptApp = angular.module('cptApp', [
	'ngTouch',
	'ngRoute',
	'ui.bootstrap'
]);

// Configure the routing (assign templates and controllers)
cptApp.config(['$routeProvider', '$locationProvider',
	function ($routeProvider, $location) {

		// Set the hashbang prefix (SEO)
		$location.hashPrefix('!');

		// Routing
		$routeProvider.
		when('/technologies', {
			templateUrl: 'partials/technologies.html',
			controller: 'TechnologiesCtrl'
		})
			.
		when('/criteria', {
			templateUrl: 'partials/criteria.html',
			controller: 'CriteriaCtrl'
		})
			.
		when('/decision-tool', {
			templateUrl: 'partials/decision-tool/dl-mainframe.html',
			controller: 'DecisionToolCtrl'
		})
			.
		when('/profiling', {
			templateUrl: 'partials/profiling/profiling.html',
			controller: 'ProfilingCtrl'
		})
			.
		when('/home', {
			templateUrl: 'partials/home.html',
		})	
			.
		when('/imprint', {
			templateUrl: 'partials/imprint.html',
		})
			.
		otherwise({
			redirectTo: '/home'
		});

		// Show the gui
		$('body')
			.css('display', 'block');
	}
]);