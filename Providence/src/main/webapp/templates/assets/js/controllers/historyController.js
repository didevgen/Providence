app.controller('userHistory', function ($scope) {
	$scope.message = 'Everyone come and see how good I look!';
	
	var ws = new WebSocket("ws://localhost:8080/kovaljov/websocket");
	
	ws.onopen = function(){  
        console.log("Socket has been opened!");  
    };
    
    ws.onmessage = function(message) {
        listener(JSON.parse(message.data));
    };
    $scope.names = []; 
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
    	    return items.slice(items.length-10,items.length).reverse();
    	  };
    	});
});