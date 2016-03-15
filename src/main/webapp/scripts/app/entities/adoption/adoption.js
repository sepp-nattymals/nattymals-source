'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('adoption', {
                parent: 'entity',
                url: '/adoptions',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.adoption.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/adoption/adoptions.html',
                        controller: 'AdoptionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('adoption');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('adoption.detail', {
                parent: 'entity',
                url: '/adoption/{id}',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.adoption.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/adoption/adoption-detail.html',
                        controller: 'AdoptionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('adoption');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Adoption', function($stateParams, Adoption) {
                        return Adoption.get({id : $stateParams.id});
                    }]
                }
            })
            .state('adoption.new', {
                parent: 'adoption',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/adoption/adoption-dialog.html',
                        controller: 'AdoptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    informativeText: null,
                                    creationDate: null,
                                    modificationDate: null,
                                    isRemoved: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('adoption', null, { reload: true });
                    }, function() {
                        $state.go('adoption');
                    })
                }]
            })
            .state('adoption.edit', {
                parent: 'adoption',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/adoption/adoption-dialog.html',
                        controller: 'AdoptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Adoption', function(Adoption) {
                                return Adoption.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('adoption', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('adoption.delete', {
                parent: 'adoption',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/adoption/adoption-delete-dialog.html',
                        controller: 'AdoptionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Adoption', function(Adoption) {
                                return Adoption.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('adoption', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
