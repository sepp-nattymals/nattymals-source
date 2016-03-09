'use strict';

angular.module('nattymalsApp')
    .controller('ContractController', function ($scope, $state, Contract, ParseLinks) {

        $scope.contracts = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Contract.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.contracts = result;
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
            $scope.contract = {
                title: null,
                fee: null,
                referenceCode: null,
                creationDate: null,
                terminationDate: null,
                terms: null,
                registered: false,
                id: null
            };
        };
    });
