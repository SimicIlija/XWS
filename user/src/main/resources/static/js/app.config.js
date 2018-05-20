'use strict';

angular.module('booking-admin')
	.config(function($stateProvider, $urlRouterProvider) {
		$stateProvider
			.state({
				name: 'home',
				url: '/',
				component: 'myHome'
			})
			.state({
				name: 'home.lodging',
				url: '^/lodging',
				component: 'mylodging'
			})
			.state({
				name: 'home.reservations',
				url: '^/reservations/{type:future|history}',
				component: 'myReservationList'
			})
			.state({
				name: 'home.search',
				url: '^/search',
				component: 'mySearch'
			})
			
			.state({
				name: 'error',
				url: '/error',
				template: '<h1>Error 404</h1>'
			});

		$urlRouterProvider
			.when('', '/')
			.otherwise('/error');
	})
	.run(function($rootScope, UserAuthService) {
		UserAuthService.getUser().then(
			(response) => {
				$rootScope.user = response.data;
			},
			() => {
				$rootScope.user = null;
			});
	});
