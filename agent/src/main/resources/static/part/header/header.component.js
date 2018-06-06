'use strict';

angular.module('header').component('myHeader', {
	templateUrl: '/part/header/header.template.html',
	controller: function(UserService, $rootScope, $state) {
		this.logOut = () => {
			UserService.logOut().then( () => {
				$rootScope.user = null;
				$state.go('home');
			});
		};
	}
});
