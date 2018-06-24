(function () {
    'use strict';
    angular
        .module('reservation_list')
        .controller('reservationController', reservationController);


    reservationController.$inject = ['reservationService', 'MessageService', '$stateParams', '$state'];


    function reservationController(reservationService, MessageService, $stateParams, $state) {
        var reservationVm = this;
        reservationVm.init = init;
        reservationVm.list = [];
        reservationVm.showHistory = showHistory;
        reservationVm.rating = rating;
        reservationVm.cancelReservation = cancelReservation;
        reservationVm.setMessage = setMessage;
        reservationVm.messageForm = null;
        reservationVm.sendMessage = sendMessage;
        reservationVm.status = null;
        reservationVm.msgContent = null;
        reservationVm.redirect = redirect;

        init();

        function showHistory() {
            return $stateParams.type === 'history';
        }

        function init() {
            if (reservationVm.showHistory()) {
                reservationService.getPast()
                    .then(response => {
                        reservationVm.list = response;
                    }).catch(response => {
                        console.log(response);
                    });
            } else {
                reservationService.getFuture()
                    .then(response => {
                        reservationVm.list = response;
                    }).catch(response => {
                        console.log(response);
                    });
            }
        }
        
        function sendMessage(reservationId) {
        	if(reservationVm.msgContent == null || reservationVm.msgContent.trim().length == 0)
        		return;
        	
        	MessageService.addMessage({receiver: -1, master: -1, reservation: reservationId, content: reservationVm.msgContent}).then(() => {
        		reservationVm.messageForm = null;
        	}, (response) => {
        		reservationVm.status = response.status;
        	});
        	
        }
        
        function setMessage(id) {
        	if(reservationVm.messageForm == id)
        		reservationVm.messageForm = null;
        	else
        		reservationVm.messageForm = id;
        }

        function rating(id) {
            console.log(id);
        }

        function cancelReservation(id) {
            reservationService.cancelReservation(id)
                .then(response => {
                    init();
                }).catch(response => {
                    console.log(response);
                });
        }

        function redirect(reservation) {
            var myObject = {};
            myObject = reservation;
            $state.go('home.past_reservation', { myObject });
        }
    }
})();