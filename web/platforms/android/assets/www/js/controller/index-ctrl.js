cptApp.controller('IndexCtrl', [
		'$scope',
		'$swipe',
		'$location',
		'$anchorScroll',
		'dataService',
		function($scope, $swipe, $location, $anchorScroll, dataService) {

			$scope.touchDevice = Modernizr.touch;

			$scope.openURL = function(url) {
				window.open(url,'_blank');
			};

			$scope.updateMenu = function() {
				$scope.activeTechnologies = false;
				$scope.activeCriteria = false;
				$scope.activeDecisionTool = false;
				$scope.activeProfiling = false;

				var path = $location.path();
				if (path.indexOf('technologies') !== -1) {
					$scope.activeTechnologies = true;
				} else if (path.indexOf('criteria') !== -1) {
					$scope.activeCriteria = true;
				} else if (path.indexOf('decision-tool') !== -1) {
					$scope.activeDecisionTool = true;
				} else if (path.indexOf('profiling') !== -1) {
					$scope.activeProfiling = true;
				}
			};

			$scope.activeTechnologies = true;

			$scope.openSlidebar = function() {
				if (touchDevice) {
					slidebar.open('left');
				}
			};

			$scope.closeSlidebar = function() {
				if (touchDevice) {
					slidebar.close();
				}
			};

			$scope.toggleSlidebar = function() {
				if (touchDevice) {
					slidebar.toggle('left');
				}
			};

			$scope.openPartial = function(partialName) {
				$scope.closeSlidebar();
				$location.path('/' + partialName);
				$scope.updateMenu();
			};

			$scope.$watch(function() {
				return $location.path();
			}, function() {
				var currentPath = $location.path();
				var uppercaseTitle = currentPath.charAt(1).toUpperCase() + currentPath.slice(2);
				$scope.pageTitle = uppercaseTitle.split('-').join(' ');
				$scope.updateMenu();
			});
			
			// ---------------------------------------------------------------
			// Enable scrolling to top
			// ---------------------------------------------------------------
			$scope.scrollToTop = function() {
				$location.hash('cpt-top');
				$anchorScroll();
			};
			
			// ---------------------------------------------------------------
			// Initialize data service and show/hide loading screen
			// ---------------------------------------------------------------
			$scope.loadingScreen = true;
			dataService.initialize(function() {
				$scope.$broadcast('INITIALIZED_DATA_SERVICE');
				$scope.loadingScreen = false;
			});
			
		}
]);