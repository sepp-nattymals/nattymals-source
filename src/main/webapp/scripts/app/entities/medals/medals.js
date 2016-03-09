'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('medals', {
                parent: 'entity',
                url: '/medalss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.medals.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/medals/medalss.html',
                        controller: 'MedalsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('medals');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('medals.detail', {
                parent: 'entity',
                url: '/medals/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.medals.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/medals/medals-detail.html',
                        controller: 'MedalsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('medals');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Medals', function($stateParams, Medals) {
                        return Medals.get({id : $stateParams.id});
                    }]
                }
            })
            .state('medals.new', {
                parent: 'medals',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/medals/medals-dialog.html',
                        controller: 'MedalsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    code: null,
                                    description: null,
                                    points: null,
                                    icon: null,
                                    iconContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('medals', null, { reload: true });
                    }, function() {
                        $state.go('medals');
                    })
                }]
            })
            .state('medals.edit', {
                parent: 'medals',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/medals/medals-dialog.html',
                        controller: 'MedalsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Medals', function(Medals) {
                                return Medals.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('medals', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('medals.delete', {
                parent: 'medals',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/medals/medals-delete-dialog.html',
                        controller: 'MedalsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Medals', function(Medals) {
                                return Medals.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('medals', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
