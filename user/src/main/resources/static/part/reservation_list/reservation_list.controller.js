(function () {
    'use strict';
    angular
        .module('reservation_list')
        .controller('reservationController', reservationController);

    reservationController.$inject = ['reservationService', '$stateParams', '$state'];

    function reservationController(reservationService, $stateParams, $state) {
        var reservationVm = this;
        reservationVm.init = init;
        reservationVm.list = [];
        reservationVm.showHistory = showHistory;
        reservationVm.rating = rating;
        reservationVm.cancelReservation = cancelReservation;
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