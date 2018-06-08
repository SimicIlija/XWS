(function () {
    'use strict';

    angular
        .module('core.reservation')
        .service('reservationService', reservationService);

    reservationService.$inject = ['$http'];

    function reservationService($http) {
        var service = {
            getAll: getAll,
            makeReservation: makeReservation,
            getFuture: getFuture,
            getPast: getPast,
            cancelReservation: cancelReservation
        };
        var url = 'http://localhost:8080';

        return service;

        function getAll() {
            return $http.get(url + '/api/reservations')
                .then(function success(response) {
                    return response.data;
                })
                .catch(function error(response) {
                    throw response.data;
                });
        }

        function makeReservation(dto) {
            return $http.post(url + '/api/reservations', dto)
                .then(function success(response) {
                    return response.data;
                })
                .catch(function error(response) {
                    throw response.data;
                });
        }

        function getFuture() {
            return $http.get(url + '/api/reservations/personal/future')
                .then(function success(response) {
                    return response.data;
                })
                .catch(function error(response) {
                    throw response.data;
                });
        }

        function getPast() {
            return $http.get(url + '/api/reservations/personal/history')
                .then(function success(response) {
                    return response.data;
                })
                .catch(function error(response) {
                    throw response.data;
                });
        }

        function cancelReservation(id) {
            return $http.delete(url + '/api/reservations/' + id)
                .then(function success(response) {
                    return response.data;
                })
                .catch(function error(response) {
                    throw response.data;
                });
        }

    }
})();