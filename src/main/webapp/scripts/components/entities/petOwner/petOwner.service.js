'use strict';

angular.module('nattymalsApp')
    .factory('PetOwner', function ($resource, DateUtils) {
        return $resource('api/petOwners/:id', {}, {
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
