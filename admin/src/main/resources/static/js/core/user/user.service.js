'use strict';

angular.module('core.user')
	.service('UserService', function($http) {
		
		this.prefix = 'http://localhost:8080';
		
		this.getAll = () => {
			return $http.get(this.prefix + '/api/users/');
		};
		this.block = (id) => {
			return $http.put(this.prefix + '/api/users/block_unblock/' + id);
		};
		this.deleteUser = (id) =>{
			return $http.delete(this.prefix + '/api/users/'+ id);
		};
	});
