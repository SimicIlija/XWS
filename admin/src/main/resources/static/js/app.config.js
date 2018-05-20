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
				name: 'userAuth',
				url: '/user-auth',
				component: 'myUserAuth'
			})
			.state({
				name: 'home.lodging',
				url: '^/lodging',
				component: 'mylodging'
			})
			.state({
				name: 'home.edit_user',
				url: '^/edit_user',
				component: 'myEditUser'
			})
			.state({
				name: 'home.pending_agents',
				url: '^/pending_agents',
				component: 'myPendingAgents'
			})
			.state({
				name: 'home.comments',
				url: '^/comments',
				component: 'myComments'
			})
			.state({
				name: 'home.catalogue',
				url: '^/catalogue',
				component: 'myCatalogue'
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
