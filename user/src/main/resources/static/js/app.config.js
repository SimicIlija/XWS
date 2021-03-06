'use strict';

angular.module('booking-admin')
	.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {

		$httpProvider.defaults.withCredentials = true;

		$stateProvider
			.state({
				name: 'home',
				url: '/',
				component: 'myHome'
			})
			.state({
				name: 'home.lodging',
				url: '^/lodging/',
				component: 'myLodging',
				params: {myObject:{}}
			})
			.state({
				name: 'home.past_reservation',
				url: '^/past_reservation/',
				component: 'myPastReservation',
				params: {myObject:{}}
			})
			.state({
				name: 'userAuth',
				url: '/user-auth',
				component: 'myUserAuth'
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
			}).state({
				name: 'home.messages',
				url: '^/messages',
				component: 'myMessages'
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
	.run(function ($rootScope, UserAuthService) {
		UserAuthService.getUser().then(
			(response) => {
				$rootScope.user = response.data;
			},
			() => {
				$rootScope.user = null;
			});
	});

