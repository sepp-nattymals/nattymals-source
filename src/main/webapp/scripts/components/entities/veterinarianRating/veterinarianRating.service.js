'use strict';

angular.module('nattymalsApp')
    .factory('VeterinarianRating', function ($resource, DateUtils) {
        return $resource('api/veterinarianRatings/:id', {}, {
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
