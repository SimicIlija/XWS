(function () {
    'use strict';

    angular
        .module('search')
        .service('searchService', searchService);

    searchService.$inject = ['$http'];

    function searchService($http) {
        var service = {
            search: search
        };
        var url = 'http://localhost:8080';

        return service;

        function search(dto) {
            return $http.post(url + '/api/search', dto)
                .then(function success(response) {
                    return response.data;
                })
                .catch(function error(response) {
                    throw response.data;
                });
        }

    }
})();