var app = angular.module("app", ["ngRoute","ngCookies","ngResource"])
  .constant("apiUrl", "http://localhost:9000\:9000/api")
	.config(["$routeProvider", function($routeProvider) {
      // WARNING!
      // Never use a route starting with "/views/" or "/api/" or "/assets/"
      // For templateUrl, always start with "/views/"
      return $routeProvider.when("/", {
        templateUrl: "/views/index",
        controller: "LoginController",
        controllerAs: 'vm'
      }).when("/app", {
        templateUrl: "/app"
      }).otherwise({
        redirectTo: "/"
      });
    }
  ]).config([
    "$locationProvider", function($locationProvider) {
      return $locationProvider.html5Mode(true).hashPrefix("!");
    }
  ]).run($rootScope, $location, $cookieStore, $http) {
             $rootScope.globals = $cookieStore.get('globals') || {};
             if ($rootScope.globals.currentUser) {
                 $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
             }
             $rootScope.$on('$locationChangeStart', function (event, next, current) {
                 // redirect to login page if not logged in and trying to access a restricted page
                 var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
                 var loggedIn = $rootScope.globals.currentUser;
                 if (restrictedPage && !loggedIn) {
                     $location.path('/login');
                 }
             });
         }

     });
