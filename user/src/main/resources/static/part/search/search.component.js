'use strict';

angular.module('search')
	.component('mySearch',{
		templateUrl: '/part/search/search.template.html',
        controller: 'searchController',
        controllerAs: 'searchVm'
	});
