'use strict';

angular.module('nattymalsApp')
    .controller('AdoptionController', function ($scope, $state, Adoption, ParseLinks) {

        $scope.adoptions = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Adoption.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.adoptions = result;
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
            $scope.adoption = {
                informativeText: null,
                creationDate: null,
                modificationDate: null,
                isRemoved: false,
                id: null
            };
        };
    });
