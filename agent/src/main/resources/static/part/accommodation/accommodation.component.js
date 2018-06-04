'use strict';

angular.module('accommodation').component('myAccommodationForm', {
	templateUrl: '/part/accommodation/accommodation.form.template.html',
	controller: function(AccommodationService, CatalogService, $state, $rootScope) {
		if(!$rootScope.user)
			$state.go('home');
		
		this.accommodation = {};
		this.accommodation.priceByMonth = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
		this.accommodation.badNumber = 1;
		
		CatalogService.getCatalogs('TYPE').then( (response) => {
			this.types = response.data;
		});
		
		CatalogService.getCatalogs('CATEGORY').then( (response) => {
			this.categories = response.data;
		});
		
		CatalogService.getCatalogs('ADDITIONAL_SERVICES').then( (response) => {
			this.add_services = response.data;
		});
		
		this.send = () => {
			var files = $('#images')[0].files;
			if(files.length < 1)
				return;
			var images = [];
			var left = files.length;
			for (var i = 0; i < files.length; i++) {
				var reader = new FileReader();
				reader._NUM = i;
				reader.onload = (function (t) {
		            return function (e) {
		                images.push(e.target.result);
		                left -= 1;
		                if(left == 0) {
		                	t.accommodation.images = images;
		        			t.accommodation.type = t.type.id;
		        			t.accommodation.category = t.category.id;
		        			t.accommodation.additionalServices = [];
		        			for (var i = 0; i < t.additionalServices.length; i++) {
		        				t.accommodation.additionalServices.push(t.additionalServices[i].id);
		        			}

		        			//console.log(this.accommodation);
		        			AccommodationService.addAccommodation(t.accommodation).then( () => {
		        				$state.go('home');
		        			}, (response) => {
		        				t.status = response.status;
		        			});
		                }
		            };
		        })(this);
				reader.readAsDataURL(files[i]);
			}
		};
	}
});