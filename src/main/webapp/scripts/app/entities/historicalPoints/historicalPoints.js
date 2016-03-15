'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('historicalPoints', {
                parent: 'entity',
                url: '/historicalPointss',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.historicalPoints.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/historicalPoints/historicalPointss.html',
                        controller: 'HistoricalPointsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('historicalPoints');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('historicalPoints.detail', {
                parent: 'entity',
                url: '/historicalPoints/{id}',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.historicalPoints.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/historicalPoints/historicalPoints-detail.html',
                        controller: 'HistoricalPointsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('historicalPoints');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HistoricalPoints', function($stateParams, HistoricalPoints) {
                        return HistoricalPoints.get({id : $stateParams.id});
                    }]
                }
            })
            .state('historicalPoints.new', {
                parent: 'historicalPoints',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/historicalPoints/historicalPoints-dialog.html',
                        controller: 'HistoricalPointsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    operationPoints: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('historicalPoints', null, { reload: true });
                    }, function() {
                        $state.go('historicalPoints');
                    })
                }]
            })
            .state('historicalPoints.edit', {
                parent: 'historicalPoints',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/historicalPoints/historicalPoints-dialog.html',
                        controller: 'HistoricalPointsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HistoricalPoints', function(HistoricalPoints) {
                                return HistoricalPoints.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('historicalPoints', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('historicalPoints.delete', {
                parent: 'historicalPoints',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/historicalPoints/historicalPoints-delete-dialog.html',
                        controller: 'HistoricalPointsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HistoricalPoints', function(HistoricalPoints) {
                                return HistoricalPoints.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('historicalPoints', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
