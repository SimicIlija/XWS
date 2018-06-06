'use strict';

angular.module('set-unavailable').component('mySetUnavailable', {
	templateUrl: '/part/set-unavailable/set-unavailable.template.html',
	controller: function(AccommodationService, ReservationService, $state, $rootScope) {
		if(!$rootScope.user)
			$state.go('home');
		
		this.dates = [0];
		this.datesData = [[]];
		
		AccommodationService.getAccommodations().then( (response) => {
			this.accommodations = response.data;
		});
		
		this.selected = (selected) => {
			this.selAcc = selected.originalObject;
			this.getUnavailable();
		};
		
		this.getUnavailable = () => {
			ReservationService.getUnavailableDates(this.selAcc.id).then( (response) => {
				this.unavailableDates = [];
				var data = response.data;
				for(var i = 0; i < data.length; i++) {
					var res = data[i];
					this.unavailableDates.push([new Date(res.startDate), new Date(res.endDate), res.id]);
				}
			}, (response) => {
				this.unavailableDates = undefined;
			});
		};
		
		this.addDate = () => {
			this.dates.push(this.dates.length);
		};
		
		this.removeDate = (toRemove) => {
			
			if(toRemove == this.dates.length-1) {
				if(this.datesData[toRemove]) {
					this.datesData.pop();
				}
			} else {
				for (var i = toRemove; i < this.datesData.length-1; i++) {
					this.datesData[i] = this.datesData[i+1];
				}
				this.datesData.pop();
			}
			
			for (var i = toRemove+1; i < this.dates.length; i++) {
				this.dates[i] -= 1;
			}
			this.dates.splice(toRemove,1);
		};
		
		this.send = () => {
			var data = [];
			for (var i = 0; i < this.datesData.length; i++) {
				var fromTo = this.datesData[i];
				if(fromTo[0] > fromTo[1]) {
					this.status = "Invalid dates on " + (i+1) + ". position!"
					return;
				}
				data.push([fromTo[0].getTime(), fromTo[1].getTime()])
			}
			ReservationService.setUnavailableDates({dates: data, accId: this.selAcc.id}).then( () => {
				this.getUnavailable();
				this.dates = [0];
				this.datesData = [[]];
			}, (response) => {
				this.status = response.status;
			});
		};
		
		this.deleteDate = (id) => {
			ReservationService.deleteUnavailableDate(id).then( () => {
				this.getUnavailable();
			}, (response) => {
				this.status = response.status;
			});
		};
	}
});