app.controller('userHistory', function ($scope) {
	$scope.message = 'This mode is for real-time looking for activity';
	
	var ws = new WebSocket("ws://localhost:8080/kovaljov/websocket");
	
	ws.onopen = function(){  
        console.log("Socket has been opened!");  
    };
    
    ws.onmessage = function(message) {
        listener(JSON.parse(message.data));
    };
    $scope.names = []; 
    $scope.getLastTenItems = function(){
    	if ($scope.names.length<=10){
    		return $scope.names;
    	}
    	else{
    		return $scope.names.slice(0,10);
    	}
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
    	    return items.slice(items.length-10,items.length).reverse();
    	  };
    	});
});