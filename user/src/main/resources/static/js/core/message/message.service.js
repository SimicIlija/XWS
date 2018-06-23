'use strict';

angular.module('core.message').service('MessageService', function($http) {
		this.prefix = 'http://localhost:8080';
		this.getMessage = (id) => {
			return $http.get(this.prefix + `/api/message/${id}`);
		};
		this.getMessages = () => {
			return $http.get(this.prefix + '/api/message/');
		};
		this.addMessage = (data) => {
			return $http.post(this.prefix + '/api/message/', data);
		};
	});