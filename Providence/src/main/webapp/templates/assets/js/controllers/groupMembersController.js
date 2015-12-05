app.controller('groupMembersController', function($scope, $http, $routeParams) {
	$scope.currPage = 1;
	$scope.iPerPage = 10;
	$scope.outsiders = [];
	getOutsiders();

	$scope.getFUsers = function() {
		var begin = (($scope.currPage - 1) * $scope.iPerPage), end = begin
				+ $scope.iPerPage;
		return myFilter().slice(begin, end);
	}
	$scope.addUserToGroup = function(index, user) {
		user.groups.push($scope.group);
		var myFunc = function(data) {
			getOutsiders();
			getUserGroup();
		}
		sendRequest("/kovaljov/user/update", user, myFunc);
		refreshGroup();
	}
	function refreshGroup() {
		var myFunc = function(data) {
			$scope.users = data.data.users;
		}
		sendRequest("/kovaljov/group/"+$scope.group.id, {}, myFunc)
	}
	function getOutsiders() {
		var myFunc = function(data) {
			$scope.outsiders = data.data;
		}
		sendRequest("/kovaljov/user/group/" + $scope.group.id, {}, myFunc)
	}
	function myFilter() {
		var searchText = $('#searchUser').val() == undefined ? "" : $(
				'#searchUser').val();
		var result = [];
		if (searchText == "") {
			return $scope.outsiders;
		} else {
			$scope.outsiders.forEach(function(item, i, arr) {
				var counter = 0;
				for ( var name in item) {
					if (item.hasOwnProperty(name)) {
						var str = item[name] == null ? "" : item[name]
								.toString();
						if (str.indexOf($scope.searchText) > -1) {
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