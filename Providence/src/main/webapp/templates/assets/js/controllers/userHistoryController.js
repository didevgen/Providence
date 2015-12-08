app.controller('userHistoryController', function ($scope,$http,$routeParams) {
	$scope.cardNumber = JSON.parse($routeParams.cardNumber);
	$scope.itemsPerPage = 20;
    $scope.currentPage = 1;
	$scope.transactions  = [];
	$scope.filteredTransactions= $scope.transactions.reverse();
	$scope.searchText = "";
	
	getAllTransactions();
	$scope.getFilteredTransactions = function () {
        var begin = (($scope.currentPage - 1) * $scope.itemsPerPage),
            end = begin + $scope.itemsPerPage;
        var res = mySearchFilter();
        return mySearchFilter().slice(begin, end);
    }
	$scope.getNormalTime = function(time) {
		return moment(time).format("DD.MM.YYYY HH:mm:ss");
	}
	function getAllTransactions() {
		var myFunc = function (data) {
            var resp = data.data;
            $scope.transactions = data.data.reverse();
        }
        sendRequest("/kovaljov//transaction/get/"+$scope.cardNumber, {}, myFunc)
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
            return $scope.transactions;
        }
        else {
            $scope.transactions.forEach(function (item, i, arr) {
                var counter = 0;
                if (item.user.userName.toString().indexOf($scope.searchText) > -1) {
                	counter++;
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