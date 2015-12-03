var app = angular.module('Providence', ['ngRoute','xeditable']);
app.config(function ($routeProvider) {
 
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