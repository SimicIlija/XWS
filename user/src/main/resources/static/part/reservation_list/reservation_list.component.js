'use strict';

angular.module('reservation_list')
	.component('myReservationList',{
		templateUrl: '/part/reservation_list/reservation_list.template.html',
        controller: 'reservationController',
        controllerAs: 'reservationVm'
	});
