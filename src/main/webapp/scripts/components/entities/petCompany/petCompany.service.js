'use strict';

angular.module('nattymalsApp')
    .factory('PetCompany', function ($resource, DateUtils) {
        return $resource('api/petCompanys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
