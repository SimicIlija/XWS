(function () {
    'use strict';
    angular
        .module('lodging')
        .controller('lodgingController', lodgingController);

    lodgingController.$inject = ['$stateParams', '$state', 'reservationService'];
    function lodgingController($stateParams, $state, reservationService) {
        var lodgingVm = this;
        lodgingVm.data = $stateParams.myObject;
        lodgingVm.disabled = false;
        lodgingVm.check = check;
        lodgingVm.book = book;
        

        check();

        function check(){
            if(lodgingVm.data === undefined){
                $state.go('error');
            }
        }
        
        function book(){
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