'use strict';

angular.module('nattymalsApp')
    .factory('Folder', function ($resource, DateUtils) {
        return $resource('api/folders/:id', {}, {
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
