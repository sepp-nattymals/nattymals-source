'use strict';

angular.module('nattymalsApp')
    .factory('Medals', function ($resource, DateUtils) {
        return $resource('api/medalss/:id', {}, {
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
