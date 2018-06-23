(function () {
    'use strict';
    angular
        .module('lodging')
        .controller('lodgingController', lodgingController);

    lodgingController.$inject = ['$stateParams', '$state', 'reservationService', 'ratingService'];
    function lodgingController($stateParams, $state, reservationService, ratingService) {
        var lodgingVm = this;
        lodgingVm.data = $stateParams.myObject;
        lodgingVm.avg = null;
        lodgingVm.comments = null;
        lodgingVm.disabled = false;
        lodgingVm.check = check;
        lodgingVm.book = book;
        lodgingVm.getAvgAndComments = getAvgAndComments;

        check();

        function check() {
            if (lodgingVm.data === undefined) {
                $state.go('error');
            }
            getAvgAndComments();
        }

        function getAvgAndComments() {
            ratingService.getAvg(lodgingVm.data.current.id)
                .then((response) => {
                    lodgingVm.avg = response;
                    ratingService.getComments(lodgingVm.data.current.id)
                        .then((response) => {
                            lodgingVm.comments = response;
                        }, (error) => {
                            console.log(error);
                        });
                }, (error) => {
                    console.log(error);
                });
        }

        function book() {
            var dto = {};
            dto.lodgingId = lodgingVm.data.current.id;
            dto.startDate = lodgingVm.data.results.startDate;
            dto.endDate = lodgingVm.data.results.endDate;
            dto.numberOfPeople = lodgingVm.data.results.numberOfPeople;
            reservationService.makeReservation(dto)
                .then(response => {
                    lodgingVm.disabled = true;
                    console.log(response);
                }).catch(response => {
                    console.log(response);
                    alert(response.message);
                });

        }

    }
})();