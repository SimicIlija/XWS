'use strict';

angular.module('core.user').service('UserService', function($http) {
		this.logIn = (data) => {
			return $http.put('/rest/user/', data);
		};
		this.logOut = () => {
			return $http.delete('/rest/user/');
		};
		this.getUser = () => {
			return $http.get('/rest/user/');
		};
	});
