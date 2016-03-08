'use strict';

angular.module('nattymalsApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


