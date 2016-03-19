'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('veterinarian', {
                parent: 'entity',
                url: '/veterinarians',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.veterinarian.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/veterinarian/veterinarians.html',
                        controller: 'VeterinarianController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('veterinarian');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('veterinarian.detail', {
                parent: 'entity',
                url: '/veterinarian/{id}',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.veterinarian.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/veterinarian/veterinarian-detail.html',
                        controller: 'VeterinarianDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('veterinarian');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Veterinarian', function($stateParams, Veterinarian) {
                        return Veterinarian.get({id : $stateParams.id});
                    }]
                }
            })
            .state('veterinarian.new', {
                parent: 'veterinarian',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/veterinarian/veterinarian-dialog.html',
                        controller: 'VeterinarianDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    bankAccount: null,
                                    webAddress: null,
                                    refereeNumber: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('veterinarian', null, { reload: true });
                    }, function() {
                        $state.go('veterinarian');
                    })
                }]
            })
            .state('veterinarian.edit', {
                parent: 'veterinarian',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/veterinarian/veterinarian-dialog.html',
                        controller: 'VeterinarianDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Veterinarian', function(Veterinarian) {
                                return Veterinarian.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('veterinarian', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('veterinarian.delete', {
                parent: 'veterinarian',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/veterinarian/veterinarian-delete-dialog.html',
                        controller: 'VeterinarianDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Veterinarian', function(Veterinarian) {
                                return Veterinarian.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('veterinarian', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
