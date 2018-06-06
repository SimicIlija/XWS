'use strict';

angular.module('core.catalog').service('CatalogService', function($http) {
		this.getCatalogs = (type) => {
			return $http.get(`/rest/catalog/${type}`);
		};
	});