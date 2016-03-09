'use strict';

angular.module('nattymalsApp')
    .factory('Contract', function ($resource, DateUtils) {
        return $resource('api/contracts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                    data.terminationDate = DateUtils.convertDateTimeFromServer(data.terminationDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
