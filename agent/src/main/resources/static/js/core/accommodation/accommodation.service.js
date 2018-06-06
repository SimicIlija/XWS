'use strict';

angular.module('core.accommodation').service('AccommodationService', function($http) {
		this.getAccommodations = () => {
			return $http.get('/rest/accommodation/');
		};
		this.getAccommodation = (id) => {
			return $http.get(`/rest/accommodation/${id}`);
		};
		this.addAccommodation = (data) => {
			return $http.post('/rest/accommodation/', data);
		};
	});