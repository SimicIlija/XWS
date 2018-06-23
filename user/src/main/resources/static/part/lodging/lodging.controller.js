(function () {
    'use strict';
    angular
        .module('lodging')
        .controller('lodgingController', lodgingController);

    lodgingController.$inject = ['$stateParams', '$state', 'reservationService', 'ratingService','$rootScope'];
    function lodgingController($stateParams, $state, reservationService, ratingService,$rootScope) {
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
            console.log(lodgingVm.data);
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
                            lodgingVm.comments = [];
                        });
                }, (error) => {
                    lodgingVm.avg = 0;
                });
        }

        function book() {
        	if($rootScope.user.blocked){
        		alert("Can not reserved if blocked");
        		return;
        	}
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