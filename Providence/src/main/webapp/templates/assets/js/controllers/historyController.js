app.controller('userHistory', function($scope) {
	$scope.message = 'This mode is for real-time looking for activity';
	$scope.isConnected = false;

	var ws = new WebSocket("ws://" + location.hostname + ":" + location.port
			+ "/kovaljov/websocket");

	ws.onopen = function() {
		console.log("Socket has been opened!");
	};

	ws.onmessage = function(message) {
		if (message.data === "connected") {
			$scope.$apply(function() {
				$scope.isConnected = true;
			})
			
		} else if (message.data === "disconnected") {
			$scope.$apply(function() {
				$scope.isConnected = false;
			})
		} else {
			listener(JSON.parse(message.data));
		}
	};
	$scope.names = [];
	$scope.getLastTenItems = function() {
		if ($scope.names.length <= 10) {
			return $scope.names.reverse();
		} else {
			return $scope.names.reverse().slice(0, 10);
		}
	}
	$scope.formatTime = function(time) {
		return moment(time).format('DD.MM.YYYY HH:mm:ss');
	}
	function listener(data) {
		var messageObj = data;
		console.log("Received data from websocket: ", messageObj);
		$scope.$apply(function() {
			data.forEach(function(item, i, object) {
				$scope.names.push(item);
			});
		})
	}
	app.filter('reverse', function() {
		return function(items) {
			return items.slice(items.length - 10, items.length).reverse();
		};
	});
});