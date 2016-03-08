 'use strict';

angular.module('nattymalsApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-nattymalsApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-nattymalsApp-params')});
                }
                return response;
            }
        };
    });
