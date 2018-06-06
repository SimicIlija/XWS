'use strict';

angular.module('core.message').service('MessageService', function($http) {
		this.getMessage = (id) => {
			return $http.get(`/rest/message/${id}`);
		};
		this.getMessages = () => {
			return $http.get('/rest/message/');
		};
		this.addMessage = (data) => {
			return $http.post('/rest/message/', data);
		};
	});