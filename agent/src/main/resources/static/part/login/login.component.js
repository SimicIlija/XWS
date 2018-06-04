'use strict';

angular.module('login').component('myLogin', {
	templateUrl: '/part/login/login.template.html',
	controller: function(UserService, $rootScope, $state) {
		this.send = () => {
			UserService.logIn(this.user).then( (response) => {
					$rootScope.user = response.data;
					$state.go('home');
			}, () => {
				this.status = 'Wrong email/password.';
			});
		};
	}
});
