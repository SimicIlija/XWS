'use strict';

angular.module('home.edit_user')
	.component('myEditUser', {
		templateUrl: '/part/edit_user/edit_user.template.html',
		controller: function( $rootScope, $state,UserService) {
			
			UserService.getAll()
			.then( (response) => {
				this.users = response.data;
			}, () => {
				this.users = null;
			});

			this.deleteUser = (user) => {
				
				UserService.deleteUser(user.id)
				.then( (response) => {
					
					var usr = user;
					this.users = this.users.filter(function(el){
						return el.id !== usr.id;
					});
					
				}, () => {
					alert('Delete failed');
					
				});
			};
			
			this.block_unblock = (user) => {
				
				UserService.block(user.id)
				.then( (response) => {
					
					this.users.forEach(function(user) {
						if(user.id == response.data.id)
							user.blocked = response.data.blocked
					});
					
				}, () => {
					alert('Block/unblock failed');
					
				});
			};
			
		}
	});