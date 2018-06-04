'use strict';

angular.module('core.reservation').service('ReservationService', function($http) {
		this.getUnavailableDates = (id) => {
			return $http.get(`/rest/reservation/unavailable/${id}`);
		};
		this.setUnavailableDates = (data) => {
			return $http.post('/rest/reservation/unavailable', data);
		};
		this.deleteUnavailableDate = (id) => {
			return $http.delete(`/rest/reservation/unavailable/${id}`);
		};
	});