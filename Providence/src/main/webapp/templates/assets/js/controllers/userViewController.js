app.controller('userViewController', function($scope, $filter, $http) {
	$scope.itemsPerPage = 10;
	$scope.currentPage=1;
	$scope.users = [];
	$scope.filteredUsers = $scope.users;
	$scope.searchText ="";
	$scope.removeUser = function(index) {
		$scope.users.splice(index, 1);
	};
	$scope.addUser = function() {
		$scope.inserted = {
			name : '',
			email : '',
			password : ''
		};
		$scope.users.unshift($scope.inserted);
	};
	
	$scope.pageCount = function () {
	    return Math.ceil($scope.users.length / $scope.itemsPerPage);
	  };
	  
	$scope.getFilteredUsers = function() {
		 var begin = (($scope.currentPage - 1) * $scope.itemsPerPage),
	        end = begin + $scope.itemsPerPage;
		 var res = mySearchFilter();
		return mySearchFilter().slice(begin, end);
	}
	$scope.getUserLength = function() {
		 return $scope.users.length;
	}
	$scope.saveUser = function(data, id) {
		angular.extend(data, {
			id : id
		});
		sendRequest("/kovaljov/user/all",{});
	};
	$scope.checkEmail = function(data) {
		if (data == null || data.indexOf('@') < 0) {
			return "Email must contain @ symbol";
		} else if (data.match(/\S+@\S+\.\S+/) == null) {
			return "Please, write valid email";
		}
	};
	$scope.checkName = function(data) {
		if (data == null || data.length < 5) {
			return "Username should be greater than 5";
		}
	};
	$scope.checkCardNumber = function(data) {
		if (data.match(/^[0-9]*$/) == null) {
			return "Please, write valid card number";
		}
	};
	
	function mySearchFilter() {
		var result = [];
		if ($scope.searchText =="") {
			return $scope.users;
		}
		else {
			$scope.users.forEach(function(item,i,arr){
				var counter = 0;
				for (var name in item) {
					  if (item.hasOwnProperty(name)) {
						  var str = item[name].toString();
						 if (str.indexOf($scope.searchText)>-1) {
							 counter++;
						 }
					  }
					}
				if (counter!=0) {
					result.push(item);
				}
				counter=0;
			});
		}
		return result;
	}
	
	
	function sendRequest(urlpattern,dataObj) {
		$http({
			  method: 'POST',
			  url: urlpattern,
			  data: dataObj,
			  headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).then(function successCallback(response) {
			  }, function errorCallback(response) {
			  });
	}
});