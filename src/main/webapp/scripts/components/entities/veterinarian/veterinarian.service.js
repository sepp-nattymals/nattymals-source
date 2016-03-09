'use strict';

angular.module('nattymalsApp')
    .factory('Veterinarian', function ($resource, DateUtils) {
        return $resource('api/veterinarians/:id', {}, {
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
