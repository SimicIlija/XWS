'use strict';

angular.module('confirm-reservation').component('myConfirmReservation', {
	templateUrl: '/part/confirm-reservation/confirm-reservation.template.html',
	controller: function(ReservationService, $state, $rootScope) {
		if(!$rootScope.user)
			$state.go('home');
		
		this.getUnconfirmed = () => {
			ReservationService.getUnconfirmed().then( (response) => {
				this.unconfirmed = response.data;
			}, () => {
				this.unconfirmed = undefined;
			});
		};
		
		this.getUnconfirmed();
		
		this.selected = (selected) => {
			this.selected = selected;
			this.startDate = new Date(selected.originalObject.startDate);
			this.endDate = new Date(selected.originalObject.endDate);
		};
		
		this.getDate = (millsec) => {
			return new Date(millsec);
		}
		
		this.send = (id) => {
			ReservationService.confirmReservation(id).then( () => {
				this.selected = undefined;
				this.getUnconfirmed();
			}, (response) => {
				this.status = response.status;
			})
		};
		
	}
});