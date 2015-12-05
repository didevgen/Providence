app.controller('groupController', function ($scope,$http) {
	$scope.itemsPerPage = 8;
    $scope.currentPage = 1;
	$scope.groups  = [];
	$scope.filteredGroups = $scope.groups;
	$scope.searchText = "";
	$scope.name="";
	getAllGroups();
	
	$scope.addGroup = function() {
		if ($scope.name.length>5) {
			var group = {};
			group.name = $scope.name;
			var myFunc = function (data) {
				getAllGroups();
	        }
	        sendRequest("/kovaljov/group/create", group, myFunc);
		}
	}
	$scope.deleteGroup = function(group) {
		 var myFunc = function (data) {
			 getAllGroups();
	        }
	        sendRequest("/kovaljov/group/delete/"+group.id, {}, myFunc);
	}
	$scope.getFilteredGroups = function () {
        var begin = (($scope.currentPage - 1) * $scope.itemsPerPage),
            end = begin + $scope.itemsPerPage;
        var res = mySearchFilter();
        return mySearchFilter().slice(begin, end);
    }
	
	function getAllGroups() {
		var myFunc = function (data) {
            var resp = data.data;
            $scope.groups = data.data;
        }
        sendRequest("/kovaljov/group/all", {}, myFunc)
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
	function mySearchFilter() {
        var result = [];
        if ($scope.searchText == "") {
            return $scope.groups;
        }
        else {
            $scope.groups.forEach(function (item, i, arr) {
                var counter = 0;
                for (var name in item) {
                    if (item.hasOwnProperty(name)) {
                        var str = item[name].toString();
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
});