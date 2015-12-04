var app = angular.module('Providence', ['ngRoute','xeditable','ui.bootstrap']);
app.config(function ($routeProvider,$httpProvider) {
	$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
    $routeProvider.when("/history", {
        controller: "userHistory",
        templateUrl: "../templates/history.html"
    });
    $routeProvider.when("/users", {
        controller: "userController",
        templateUrl: "../templates/users.html"
    });
    $routeProvider.otherwise({redirectTo:"/history"})
});

app.run(function(editableOptions) {
	  editableOptions.theme = 'bs3'; 
	});