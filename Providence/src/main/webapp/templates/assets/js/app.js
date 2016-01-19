var app = angular.module('Providence', [ 'ngRoute', 'xeditable','ui.calendar',
		'ui.bootstrap', 'frapontillo.bootstrap-switch' ]);
app
		.config(function($routeProvider, $httpProvider) {
			$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
			$routeProvider.when("/history", {
				controller : "userHistory",
				templateUrl : "../templates/history.html"
			});
			$routeProvider.when("/users", {
				controller : "userController",
				templateUrl : "../templates/users.html"
			});
			$routeProvider.when("/groups/:group", {
				controller : "groupDetailController",
				templateUrl : "../templates/groups.html"
			});
			$routeProvider.when("/transactions", {
				controller : "transactionController",
				templateUrl : "../templates/transaction.html"
			});
			$routeProvider.when("/userHistory/:cardNumber", {
				controller : "userHistoryController",
				templateUrl : "../templates/userHistory.html"
			});
			$routeProvider.when("/room", {
				controller : "roomController",
				templateUrl : "../templates/room.html"
			});
			$routeProvider.when("/report", {
				controller : "reportController",
				templateUrl : "../templates/report.html"
			});
			$routeProvider.when("/rooms/:roomId", {
				controller : "presenceController",
				templateUrl : "../templates/present.html"
			});
			$routeProvider.when("/calendar/:roomId", {
				controller : "calendarController",
				templateUrl : "../templates/calendar.html"
			});
			$routeProvider.otherwise({
				redirectTo : '/history'
			});
		});

app.run(function(editableOptions) {
	editableOptions.theme = 'bs3';
});

app.directive('bootstrapSwitch', [ function() {
	return {
		restrict : 'A',
		require : '?ngModel',
		link : function(scope, element, attrs, ngModel) {
			element.bootstrapSwitch();

			element.on('switchChange.bootstrapSwitch', function(event, state) {
				if (ngModel) {
					scope.$apply(function() {
						ngModel.$setViewValue(state);
					});
				}
			});

			scope.$watch(attrs.ngModel, function(newValue, oldValue) {
				if (newValue) {
					element.bootstrapSwitch('state', true, true);
				} else {
					element.bootstrapSwitch('state', false, true);
				}
			});
		}
	};
} ]);