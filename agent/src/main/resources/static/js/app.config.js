'use strict';

angular.module('agent')
	.config(function($stateProvider, $urlRouterProvider) {
		$stateProvider
			.state({
				name: 'home',
				url: '/',
				component: 'myHome'
			})
			.state({
				name: 'home.accommodationForm',
				url: 'accommodation/add',
				component: 'myAccommodationForm'
			})
			.state({
				name: 'home.setUnavailable',
				url: 'accommodation/set-unavailable',
				component: 'mySetUnavailable'
			})
			.state({
				name: 'home.messages',
				url: 'messages',
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
	.run(function($rootScope, UserService) {
		UserService.getUser().then( (response) => {
				$rootScope.user = response.data;
		}, () => {
			$rootScope.user = null;
		});
	});
