app.controller('userViewController', function ($scope, $filter, $http) {
	$scope.itemsPerPage = 10;
    $scope.currentPage = 1;
    $scope.users = [];
    $scope.isSelected = false;
    $scope.filteredUsers = $scope.users;
    $scope.searchText = "";
    getAllUsers();
    $scope.removeUser = function (index, userId) {
    	if (userId == undefined) {
    		$scope.users.shift();
    		return;
    	}
        var myFunc = function (data) {
            getAllUsers();
        }
        sendRequest("/kovaljov/user/delete/"+userId, {}, myFunc);
    };
    $scope.addUser = function () {
        $scope.inserted = {
        	personName: '',
            email: '',
            password: ''
        };
        $scope.users.unshift($scope.inserted);
    };


    $scope.pageCount = function () {
        return Math.ceil($scope.users.length / $scope.itemsPerPage);
    };

    $scope.getFilteredUsers = function () {
        var begin = (($scope.currentPage - 1) * $scope.itemsPerPage),
            end = begin + $scope.itemsPerPage;
        var res = mySearchFilter();
        return mySearchFilter().slice(begin, end);
    }
    $scope.getUserLength = function () {
        return $scope.users.length;
    }
    $scope.saveUser = function (data, id) {
        angular.extend(data, {
            id: id
        });
        insertUser(data,id);
    };


    // Validation part
    $scope.checkEmail = function (data) {
        if (data == null || data.indexOf('@') < 0) {
            return "Email must contain @ symbol";
        } else if (data.match(/\S+@\S+\.\S+/) == null) {
            return "Please, write valid email";
        }
    };
    $scope.checkName = function (data) {
        if (data == null || data.length < 5) {
            return "Username should be greater than 5";
        }
    };
    $scope.checkCardNumber = function (data) {
        if (data.toString().match(/^[0-9]*$/) == null) {
            return "Please, write valid card number";
        }
    };
    //Validation ends

    function mySearchFilter() {
        var result = [];
        if ($scope.searchText == "") {
            return $scope.users;
        }
        else {
            $scope.users.forEach(function (item, i, arr) {
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

    function insertUser(user,userId) {
        if (userId != undefined) {
        	user.personId = userId;
            delete user.id;
            updateUser(user);
            return;
        }
        var myFunc = function (data) {
            getAllUsers();
        }
        sendRequest("/kovaljov/user/create", user, myFunc);
    }

    function getAllUsers() {
        var myFunc = function (data) {
            var resp = data.data;
            $scope.users = data.data;
        }
        sendRequest("/kovaljov/user/all", {}, myFunc)
    }

    function updateUser(user) {
        var myFunc = function (data) {
            getAllUsers();
        }
        sendRequest("/kovaljov/user/update", user, myFunc);
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
    $scope.toggle = function(id) {
        $scope.isSelected = $scope.isSelected ==true ? false : true;
      };
});