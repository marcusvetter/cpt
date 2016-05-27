cptApp.controller('DecisionToolCtrl', [ '$scope',
		function($scope) {
			
			// States := 'STARTPAGE' | 'WIZZARD' | 'RANKING_LIST' | 'RANKING_TABLE'
			$scope.state = "STARTPAGE";
			$scope.changeStateTo = function(newState) {
				$scope.state = newState;
			};
			
			// DEBUG
			$scope.debugGoToRanking = function() {
				$scope.state = "RANKING";
			};
			
		}
]);