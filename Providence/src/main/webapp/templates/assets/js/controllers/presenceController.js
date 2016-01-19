app.controller('presenceController', function($scope, $http, $routeParams) {
	$scope.roomId = JSON.parse($routeParams.roomId);
	$scope.itemsPerPage = 10;
	$scope.currentPage = 1;
	$scope.rooms = [];
	$scope.filteredRooms = $scope.rooms;
	$scope.searchText = "";
	getAllRooms();
	$scope.pageCount = function() {
		return Math.ceil($scope.rooms.length
				/ $scope.itemsPerPage);
	};
	$scope.getFilteredRooms = function() {
		var begin = (($scope.currentPage - 1) * $scope.itemsPerPage), end = begin
				+ $scope.itemsPerPage;
		var res = mySearchFilter();
		return mySearchFilter().slice(begin, end);
	}
	$scope.getRoomLength = function() {
		return $scope.rooms.length;
	}
	$scope.getNormalTime = function(time) {
		return moment(time).format("DD.MM.YYYY HH:mm:ss");
	}
	function mySearchFilter() {
		var result = [];
		if ($scope.searchText == "") {
			return $scope.rooms;
		} else {
			$scope.rooms
					.forEach(function(item, i, arr) {
						var counter = 0;
						for ( var name in item) {
							if (item.hasOwnProperty(name)) {
								var str = item[name].toString();
								if (str
										.indexOf($scope.searchText) > -1) {
									counter++;
								}
							}
						}
						if (counter != 0) {
							result.push(item);
						}
						counter = 0;
					});
		}
		return result;
	}

	function getAllRooms() {
		var myFunc = function(data) {
			var resp = data.data;
			$scope.rooms = data.data;
		}
		sendRequest("/kovaljov/room/"+$scope.roomId+"/users", {}, myFunc)
	}
	function sendRequest(urlPattern, dataObj, myFunc) {
		$http({
			method : 'POST',
			url : urlPattern,
			data : dataObj,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(function successCallback(response) {
			myFunc(response);
		}, function errorCallback(response) {

		});
	}
});