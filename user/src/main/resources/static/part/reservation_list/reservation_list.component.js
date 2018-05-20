'use strict';

angular.module('reservation_list')
	.component('myReservationList',{
		templateUrl: '/part/reservation_list/reservation_list.template.html',
		controller: function($stateParams){
			this.type = $stateParams.type;

//			ReservationService.getAll(this.type)
//				.then( (response) => {
//					this.reservations = response.data;
//				},
//				() => {
//					this.reservations = null;
//				});
//
//			this.cancel = (reservation) => {
//				ReservationService.cancel(reservation.id)
//					.then( () => {
//						let index = this.reservations.indexOf(reservation);
//						this.reservations.splice(index, 1);
//					});
//			};
		}
	});
