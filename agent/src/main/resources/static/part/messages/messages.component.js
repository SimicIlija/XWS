'use strict';

angular.module('messages').component('myMessages', {
	templateUrl: '/part/messages/messages.template.html',
	controller: function(MessageService, $state, $rootScope) {
		if(!$rootScope.user)
			$state.go('home');
		
		MessageService.getMessages().then((response) => {
			this.messages = response.data;
		});
		
		this.displayMessage = (index) => {
			this.message = this.messages[index];
		};
		
		this.refresh = () => {
			MessageService.getMessage(this.message.id).then( (response) => {
				this.message = response.data;
			}, (response) => {
				this.status = response.status;
			});
		};
		
		this.send = () => {
			var msg = {content: this.toSend, master: this.message.id}
			MessageService.addMessage(msg).then( (response) => {
				this.message.messages.push(response.data);
				this.toSend = "";
			},(response) => {
				this.status = response.status;
			});
		};
	}
});