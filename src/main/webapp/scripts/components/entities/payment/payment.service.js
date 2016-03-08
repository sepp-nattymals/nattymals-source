'use strict';

angular.module('nattymalsApp')
    .factory('Payment', function ($resource, DateUtils) {
        return $resource('api/payments/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.paymentDate = DateUtils.convertDateTimeFromServer(data.paymentDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
