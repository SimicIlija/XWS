'use strict';

angular.module('core.catalog')
	.service('CatalogService',function ($http){
		
		this.prefix = 'http://localhost:8080';
		
		this.getAll = () => {
			return $http.get(this.prefix + '/api/catalog');
		};
		this.getOne = (id) => {
			return $http.get(this.prefix + '/api/catalog/'+id);
		};
		this.add = (data) => {
			return $http.post(this.prefix + '/api/catalog/', data);
		};
		this.deleteCatalog = (id) =>{
			return $http.delete(this.prefix + '/api/catalog/'+ id);
		};
	});
