'use strict';

angular.module('past_reservation')
	.component('myPastReservation',{
		templateUrl: '/part/past_reservation/past_reservation.template.html',
        controller: 'pastResController',
        controllerAs: 'pastResVm'
	});
