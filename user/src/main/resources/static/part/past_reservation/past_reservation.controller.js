(function () {
    'use strict';
    angular
        .module('past_reservation')
        .controller('pastResController', pastResController);

    pastResController.$inject = ['$stateParams', '$state', 'ratingService'];

    function pastResController($stateParams, $state, ratingService) {
        var pastResVm = this;
        pastResVm.data = $stateParams.myObject;
        pastResVm.ratingDto = null;
        pastResVm.newRating = {};
        pastResVm.newRating.rating = "1";
        pastResVm.newRating.comment = "";
        pastResVm.showRating = false;
        pastResVm.errorMessage = null;
        pastResVm.check = check;
        pastResVm.addNewRating = addNewRating;

        check();

        function check() {
            if (pastResVm.data === undefined) {
                $state.go('error');
            }
            ratingService.checkComment(pastResVm.data.id)
                .then(response => {
                    pastResVm.ratingDto = response;
                    pastResVm.showRating = true;
                }).catch(response => {
                    pastResVm.showRating = true;
                    console.log(response);
                });
        }

        function addNewRating() {
            if(pastResVm.newRating.comment === null || pastResVm.newRating.comment === ''){
                pastResVm.errorMessage = "Comment is empty";
                return;
            }
            pastResVm.errorMessage = null;
            pastResVm.newRating.idReservation = pastResVm.data.id;
            console.log(pastResVm.newRating);
            ratingService.addNew(pastResVm.newRating)
                .then(response => {
                    check();
                }).catch(response => {
                    console.log(response);
                });
        }



    }
})();