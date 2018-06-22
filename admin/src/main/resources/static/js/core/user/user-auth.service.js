'use strict';

angular.module('core.user')
	.service('UserAuthService', function($http) {
		
		this.prefix = 'http://localhost:8080';
		this.register = (data) => {
			return $http.post(this.prefix + '/api/user-auth/', data);
		};
		this.addAgent = (data) => {
			return $http.post(this.prefix + '/api/user-auth/agent', data);
		};
		this.logIn = (data) => {
			return $http.put(this.prefix + '/api/user-auth/', data);
		};
		this.logOut = () => {
			return $http.delete(this.prefix + '/api/user-auth/');
		};
		this.getUser = () => {
			return $http.get(this.prefix + '/api/user-auth/');
		};
		this.editUser = (data) => {
			return $http.put(this.prefix + '/api/user-auth/edit', data);
		};
		this.changeRole = (data) => {
			return $http.put(this.prefix + 'api/user-auth/change-role', data);
		};
	});
