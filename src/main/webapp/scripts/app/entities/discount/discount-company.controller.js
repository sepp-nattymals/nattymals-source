'use strict';

angular.module('nattymalsApp')
    .controller('DiscountCompanyController', function ($scope, $state, Discount, ParseLinks) {

        $scope.discounts = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Discount.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.discounts = result;
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
            $scope.discount = {
                companyName: null,
                title: null,
                description: null,
                code: null,
                discountRate: null,
                startDate: null,
                endDate: null,
                id: null
            };
        };
    });