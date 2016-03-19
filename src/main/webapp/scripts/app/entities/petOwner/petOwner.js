'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('petOwner', {
                parent: 'entity',
                url: '/petOwners',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.petOwner.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petOwner/petOwners.html',
                        controller: 'PetOwnerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petOwner');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('petOwner.detail', {
                parent: 'entity',
                url: '/petOwner/{id}',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.petOwner.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petOwner/petOwner-detail.html',
                        controller: 'PetOwnerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petOwner');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PetOwner', function($stateParams, PetOwner) {
                        return PetOwner.get({id : $stateParams.id});
                    }]
                }
            })
            .state('petOwner.new', {
                parent: 'petOwner',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petOwner/petOwner-dialog.html',
                        controller: 'PetOwnerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    totalPoints: null,
                                    isBlocked: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('petOwner', null, { reload: true });
                    }, function() {
                        $state.go('petOwner');
                    })
                }]
            })
            .state('petOwner.edit', {
                parent: 'petOwner',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petOwner/petOwner-dialog.html',
                        controller: 'PetOwnerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PetOwner', function(PetOwner) {
                                return PetOwner.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petOwner', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('petOwner.delete', {
                parent: 'petOwner',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petOwner/petOwner-delete-dialog.html',
                        controller: 'PetOwnerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PetOwner', function(PetOwner) {
                                return PetOwner.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petOwner', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
