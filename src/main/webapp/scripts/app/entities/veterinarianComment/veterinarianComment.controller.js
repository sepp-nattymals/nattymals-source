'use strict';

angular.module('nattymalsApp')
    .controller('VeterinarianCommentController', function ($scope, $state, VeterinarianComment, ParseLinks) {

        $scope.veterinarianComments = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            VeterinarianComment.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.veterinarianComments = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.veterinarianComment = {
                creationDate: null,
                text: null,
                id: null
            };
        };
    });
