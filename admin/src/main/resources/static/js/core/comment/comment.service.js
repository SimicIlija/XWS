'use strict';

angular.module('core.comments')
	.service('CommentService',function ($http){
		
		this.prefix = 'http://localhost:8080';
		
		this.getAll = () => {
			return $http.get(this.prefix + '/api/comment');
		};
		this.getOne = (id) => {
			return $http.get(this.prefix + '/api/comment/'+id);
		};
		this.getNotApproved = (id) => {
			return $http.get(this.prefix + '/api/rating/notconfirmed');
		};
		this.add = (data) => {
			return $http.post(this.prefix + '/api/comment/', data);
		};
		this.deleteCatalog = (id) =>{
			return $http.delete(this.prefix + '/api/comment/'+ id);
		};
		this.approve = (id) =>{
			return $http.post(this.prefix + '/api/rating/confirm/'+ id);
		};
	});