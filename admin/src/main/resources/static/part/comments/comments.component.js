'use strict';

angular.module('home.comments')
	.component('myComments', {
		templateUrl: '/part/comments/comments.template.html',
		controller: function( $rootScope, $state,CommentService) {
			
			CommentService.getNotApproved()
			.then( (response) => {
				this.comments = response.data;
			}, () => {
				this.comments = null;
			});
			
			this.approve = (idRes) =>{
				console.log(idRes);
				CommentService.approve(idRes)
				.then( (response) => {
					this.comments = this.comments.filter(function(el){
						return el.idReservation !== idRes;
					});
				}, () =>{
					alert("Approving failed");
				});
			}
			
		}
	});