'use strict';

angular.module('home.pending_agents')
	.component('myPendingAgents', {
		templateUrl: '/part/pending_agents/pending_agents.template.html',
		controller: function( $rootScope, $state,UserAuthService) {
			
			this.send = () => {
				if(this.user.password !== this.user.passwordAgain)
				{
					this.status = 'Passwords don\'t match';
					return;
				}
				UserAuthService.addAgent(this.user).then(
					() => {
						this.status = 'New agent successfully added';
					},
					() => {
						this.status = 'Error';
					});
			};
		
		}
	});