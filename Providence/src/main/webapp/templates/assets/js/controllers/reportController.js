app.controller('reportController', 
			function($scope, $http, $filter) {
				$scope.itemsPerPage = 10;
				$scope.currentPage = 1;
				$scope.rooms = [];
				$scope.filteredRooms = $scope.rooms;
				$scope.searchText = "";
				getAllRooms();
				$scope.removeRoom = function(index, roomId) {
					if (roomId == undefined) {
						$scope.rooms.shift();
						return;
					}
					var myFunc = function(data) {
						getAllRooms();
					}
					sendRequest("/kovaljov/room/delete/" + roomId, {},
							myFunc);
				};
				$scope.addRoom = function() {
					$scope.inserted = {
						roomIp : '',
						roomName : '',
						doorState : ''
					};
					$scope.rooms.unshift($scope.inserted);
				};

				$scope.pageCount = function() {
					return Math.ceil($scope.rooms.length
							/ $scope.itemsPerPage);
				};
				$scope.statuses = [ {
					text : 'entrance'
				}, {
					text : 'exit'
				} ];

				$scope.showDoorState = function(room) {
					var selected = $filter('filter')($scope.statuses, {
						text : room.doorState
					});
					return room.doorState;
				};
				$scope.saveRoom = function(data, id) {
					updateRoom(data, id);
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
					sendRequest("/kovaljov/room/users", {}, myFunc)
				}

				function updateRoom(room, id) {
					var myFunc = function(data) {
						getAllRooms();
					}
					sendRequest("/kovaljov/room/update/" + id, room, myFunc);
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
				$scope.toggle = function(user, id) {
					var myFunc = function(data) {
						getAllRooms();
					}
					$scope.isSelected = $scope.isSelected == true ? false : true;
					sendRequest("/kovaljov/room/" + id + "/subscribe", {}, myFunc);
				};

});