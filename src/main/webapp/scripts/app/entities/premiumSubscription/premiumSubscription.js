'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('premiumSubscription', {
                parent: 'entity',
                url: '/premiumSubscriptions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.premiumSubscription.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/premiumSubscription/premiumSubscriptions.html',
                        controller: 'PremiumSubscriptionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('premiumSubscription');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('premiumSubscription.detail', {
                parent: 'entity',
                url: '/premiumSubscription/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.premiumSubscription.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/premiumSubscription/premiumSubscription-detail.html',
                        controller: 'PremiumSubscriptionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('premiumSubscription');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PremiumSubscription', function($stateParams, PremiumSubscription) {
                        return PremiumSubscription.get({id : $stateParams.id});
                    }]
                }
            })
            .state('premiumSubscription.new', {
                parent: 'premiumSubscription',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/premiumSubscription/premiumSubscription-dialog.html',
                        controller: 'PremiumSubscriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    startDate: null,
                                    endDate: null,
                                    fee: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('premiumSubscription', null, { reload: true });
                    }, function() {
                        $state.go('premiumSubscription');
                    })
                }]
            })
            .state('premiumSubscription.edit', {
                parent: 'premiumSubscription',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/premiumSubscription/premiumSubscription-dialog.html',
                        controller: 'PremiumSubscriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PremiumSubscription', function(PremiumSubscription) {
                                return PremiumSubscription.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('premiumSubscription', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('premiumSubscription.delete', {
                parent: 'premiumSubscription',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/premiumSubscription/premiumSubscription-delete-dialog.html',
                        controller: 'PremiumSubscriptionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PremiumSubscription', function(PremiumSubscription) {
                                return PremiumSubscription.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('premiumSubscription', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
