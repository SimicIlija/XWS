(function () {
    'use strict';
    angular
        .module('search')
        .controller('searchController', searchController);

    searchController.$inject = ['searchService', 'reservationService', '$rootScope', 'CatalogService', '$state'];

    function searchController(searchService, reservationService, $rootScope, CatalogService, $state) {
        var searchVm = this;
        searchVm.dto = {};
        searchVm.results = null;
        searchVm.dto.place = '';
        searchVm.dto.numberOfPeople = 1;
        searchVm.dto.startDate = new Date();
        searchVm.dto.endDate = new Date();
        searchVm.errorMessage = '';
        searchVm.sortCrit = "1";
        searchVm.catalogs = [];
        searchVm.search = search;
        searchVm.errorHappened = errorHappened;
        searchVm.showResults = showResults;
        searchVm.reserve = reserve;
        searchVm.loggedIn = loggedIn;
        searchVm.redirect = redirect;
        searchVm.sortiraj = sortiraj;



        CatalogService.getAll()
            .then((response) => {
                searchVm.catalogs = response.data;
            }, () => {
                this.catalogs = null;
            });

        function search() {
            console.log(searchVm.dto);

            let bool = verifyData();
            if (!bool) {
                return;
            }
            searchVm.errorMessage = '';
            searchService
                .search(searchVm.dto)
                .then(response => {
                    console.log(response);
                    searchVm.results = response;
                }).catch(response => {
                    console.log(response);
                });
        }

        function errorHappened() {
            return searchVm.errorMessage !== '';
        }

        function showResults() {
            return searchVm.results !== null;
        }

        function loggedIn() {
            return $rootScope.user !== null;
        }

        function reserve(id) {
            var dto = {};
            dto.lodgingId = id;
            dto.startDate = searchVm.results.startDate;
            dto.endDate = searchVm.results.endDate;
            if (!loggedIn()) {
                console.log("uloguj se");
                return;
            }
            reservationService.makeReservation(dto)
                .then(response => {
                    console.log(response);
                }).catch(response => {
                    console.log(response);
                });

        }

        function verifyData() {
            if (searchVm.dto.place === undefined || searchVm.dto.place.trim() === '') {
                searchVm.errorMessage = 'Define place.';
                return false;
            }
            if (searchVm.dto.numberOfPeople === undefined || searchVm.dto.numberOfPeople < 1) {
                searchVm.errorMessage = 'Number of people should at last be 1.';
                return false;
            }
            var today = new Date();
            if (searchVm.dto.startDate === undefined || searchVm.dto.startDate <= today) {
                searchVm.errorMessage = 'Define start date. Can not be before today.';
                return false;
            }
            if (searchVm.dto.endDate === undefined || searchVm.dto.endDate <= today || searchVm.dto.endDate <= searchVm.dto.startDate) {
                searchVm.errorMessage = 'Define end date. Can not be before today or start date.';
                return false;
            }
            return true;
        }

        function redirect(results, lodging){
            var myObject = {};
            myObject.results = results;
            myObject.current = lodging;
            console.log(myObject);
            $state.go('home.lodging', {myObject});
        }
        
        function sortiraj() {
            if(searchVm.sortCrit == "1"){
                searchVm.results.lodgings.sort(propComparator("avg"));
                return;
            }
            if(searchVm.sortCrit == "2"){
                searchVm.results.lodgings.sort(propComparator("price"));
                return;
            }
            if(searchVm.sortCrit == "3"){
                searchVm.results.lodgings.sort(propComparator("price"));
                return;
            }
        }

        function propComparator(prop) {
            return function(c1, c2) {
                    if (c1[prop] < c2[prop])
                        return 1;
                    if (c1[prop] > c2[prop])
                        return -1;
                    return 0;
            };
        }
    }
})();