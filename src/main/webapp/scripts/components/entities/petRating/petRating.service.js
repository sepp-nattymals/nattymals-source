'use strict';

angular.module('nattymalsApp')
    .factory('PetRating', function ($resource, DateUtils) {
        return $resource('api/petRatings/:id', {}, {
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
