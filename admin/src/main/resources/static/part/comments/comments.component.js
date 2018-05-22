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
			
			this.approve = (comment) =>{
				CommentService.approve(comment.id)
				.then( (response) => {
					var com = comment;
					this.comments = this.comments.filter(function(el){
						return el.id !== com.id;
					});
				}, () =>{
					aler("Approving failed");
				});
			}
			
		}
	});