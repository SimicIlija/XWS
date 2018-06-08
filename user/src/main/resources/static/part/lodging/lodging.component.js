'use strict';

angular.module('lodging')
	.component('myLodging',{
		templateUrl: '/part/lodging/lodging.template.html',
        controller: 'lodgingController',
        controllerAs: 'lodgingVm'
	});
