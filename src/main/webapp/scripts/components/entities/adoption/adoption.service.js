'use strict';

angular.module('nattymalsApp')
    .factory('Adoption', function ($resource, DateUtils) {
        return $resource('api/adoptions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                    data.modificationDate = DateUtils.convertDateTimeFromServer(data.modificationDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
