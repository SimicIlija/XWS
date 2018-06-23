(function () {
    'use strict';

    angular
        .module('core.reservation')
        .service('ratingService', ratingService);

    ratingService.$inject = ['$http'];

    function ratingService($http) {
        var service = {
            getAvg: getAvg,
            getComments: getComments,
            checkComment: checkComment,
            addNew: addNew
            
        };
        var url = 'http://localhost:8080';

        return service;

        function getAvg(id){
            return $http.get(url + '/api/rating/avg/' + id)
                .then(function success(response) {
                    return response.data;
                })
                .catch(function error(response) {
                    throw response.data;
                });
        }

        function getComments(id){
            return $http.get(url + '/api/rating/data/' + id)
                .then(function success(response) {
                    return response.data;
                })
                .catch(function error(response) {
                    throw response.data;
                });
        }

        function checkComment(id){
            return $http.get(url + '/api/rating/check/' + id)
                .then(function success(response) {
                    return response.data;
                })
                .catch(function error(response) {
                    throw response.data;
                });
        }

        function addNew(dto){
            return $http.post(url + '/api/rating', dto)
                .then(function success(response) {
                    return response.data;
                })
                .catch(function error(response) {
                    throw response.data;
                });
        }
    }
})();