'use strict';

angular.module('nattymalsApp')
    .factory('Clinic', function ($resource, DateUtils) {
        return $resource('api/clinics/:id', {}, {
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
