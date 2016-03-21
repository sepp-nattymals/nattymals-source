'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('discount', {
                parent: 'entity',
                url: '/discounts',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.discount.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/discount/discounts.html',
                        controller: 'DiscountController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('discount');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('discount.company', {
                parent: 'entity',
                url: '/discounts/{companyName}',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.discount.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/discount/discounts.html',
                        controller: 'DiscountCompanyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('discount');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('discount.detail', {
                parent: 'entity',
                url: '/discount/{id}',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.discount.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/discount/discount-detail.html',
                        controller: 'DiscountDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('discount');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Discount', function($stateParams, Discount) {
                        return Discount.get({id : $stateParams.id});
                    }]
                }
            })
            .state('discount.new', {
                parent: 'discount',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/discount/discount-dialog.html',
                        controller: 'DiscountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    companyName: null,
                                    title: null,
                                    description: null,
                                    code: null,
                                    discountRate: null,
                                    startDate: null,
                                    endDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('discount', null, { reload: true });
                    }, function() {
                        $state.go('discount');
                    })
                }]
            })
            .state('discount.edit', {
                parent: 'discount',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/discount/discount-dialog.html',
                        controller: 'DiscountDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Discount', function(Discount) {
                                return Discount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('discount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('discount.delete', {
                parent: 'discount',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/discount/discount-delete-dialog.html',
                        controller: 'DiscountDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Discount', function(Discount) {
                                return Discount.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('discount', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
