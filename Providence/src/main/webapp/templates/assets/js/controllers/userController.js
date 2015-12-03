app.controller('userController', function ($scope) {
	$scope.choose = "";
	$scope.selection  =  ["users","groups"];
	$scope.OpenCourse = function(index) {
		$scope.choose = $scope.selection[index];
	}
});