'use strict';

angular.module('search')
	.component('mySearch',{
		templateUrl: '/part/search/search.template.html',
		controller: function($stateParams){

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
