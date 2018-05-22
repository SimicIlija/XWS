'use strict';

angular.module('home.catalogue')
	.component('myCatalogue', {
		templateUrl: '/part/catalogue/catalogue.template.html',
		controller: function( $rootScope, $state, CatalogService,$window) {
			
			CatalogService.getAll()
			.then( (response) => {
				this.catalogs = response.data;
			}, () => {
				this.catalogs = null;
			});
			
			
			this.deleteCatalog = (catalog) => {
				
				
				CatalogService.deleteCatalog(catalog.id)
				.then( (response) => {
					
					var cat = catalog;
					this.catalogs = this.catalogs.filter(function(el){
						return el.id !== cat.id;
					});
					
				}, () => {
					alert('Delete failed');
					
				});
			};
			
			this.send = (type) => {
				if(type == "TYPE")
					this.value = this.valueT;
				else if(type == "CATEGORY")
					this.value = this.valueC;
				else if(type == "ADDITIONAL_SERVICES")
					this.value = this.valueA;
				else 
					return ;
				this.input = {
					"value":this.value,
					"type": type
				}
				CatalogService.add(this.input)
				.then( (response) => {
					
					this.catalogs.push(response);
					$window.location.reload();
					
				}, () => {
					alert('Adding failed');
					
				});
			};
		}
	});