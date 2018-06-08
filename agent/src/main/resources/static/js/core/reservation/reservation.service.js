'use strict';

angular.module('core.reservation').service('ReservationService', function($http) {
		this.getUnavailableDates = (id) => {
			return $http.get(`/rest/reservation/unavailable/${id}`);
		};
		this.getUnconfirmed = () => {
			return $http.get('/rest/reservation');
		};
		this.setUnavailableDates = (data) => {
			return $http.post('/rest/reservation/unavailable', data);
		};
		this.confirmReservation = (id) => {
			return $http.put(`/rest/reservation/confirm/${id}`);
		};
		this.deleteUnavailableDate = (id) => {
			return $http.delete(`/rest/reservation/unavailable/${id}`);
		};
	});