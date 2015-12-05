app.controller('groupDetailController', function($scope, $http, $routeParams) {
	$scope.searchText = "";
	$scope.group = JSON.parse($routeParams.group);
	$scope.itemsPerPage = 10;
	$scope.currentPage = 1;
	$scope.users = [];
	$scope.choose ="";
	$scope.filteredUsers = $scope.users;
	$scope.rawUsers =[];
	$scope.user = {};
	getUserGroup();
	
	$scope.getFilteredUsers = function () {
        var begin = (($scope.currentPage - 1) * $scope.itemsPerPage),
            end = begin + $scope.itemsPerPage;
        var res = mySearchFilter();
        return mySearchFilter().slice(begin, end);
    }
	function getUserGroup() {
		var myFunc = function(data) {
			$scope.users = data.data.users;
		}
		sendRequest("/kovaljov/group/"+$scope.group.id, {}, myFunc)
	}
	
	$scope.removeFromGroup = function (index,user) {
		$scope.user = user;
		var myFunc = function(data) {
			getUserGroup();
		}
		sendRequest("/kovaljov/user/"+$scope.user.userId+"/group/"+$scope.group.id, {}, myFunc)
	}
	
	function mySearchFilter() {
		$scope.searchText = $('#searchText').val() == undefined ? "":$('#searchText').val();
        var result = [];
        if ($scope.searchText == "") {
            return $scope.users;
        }
        else {
            $scope.users.forEach(function (item, i, arr) {
                var counter = 0;
                for (var name in item) {
                    if (item.hasOwnProperty(name)) {
                        var str = item[name]==null ? "" :item[name].toString();
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
	            method: 'POST',
	            url: urlPattern,
	            data: dataObj,
	            headers: {
	                'Content-Type': 'application/json'
	            }
	        }).then(function successCallback(response) {
	            myFunc(response);
	        }, function errorCallback(response) {

	        });
	    }
});