'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pet', {
                parent: 'entity',
                url: '/pets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.pet.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pet/pets.html',
                        controller: 'PetController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pet.detail', {
                parent: 'entity',
                url: '/pet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.pet.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pet/pet-detail.html',
                        controller: 'PetDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Pet', function($stateParams, Pet) {
                        return Pet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pet.new', {
                parent: 'pet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pet/pet-dialog.html',
                        controller: 'PetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    sex: null,
                                    weight: null,
                                    hasPedigree: false,
                                    race: null,
                                    birthDate: null,
                                    photo: null,
                                    photoContentType: null,
                                    dating: false,
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pet', null, { reload: true });
                    }, function() {
                        $state.go('pet');
                    })
                }]
            })
            .state('pet.edit', {
                parent: 'pet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pet/pet-dialog.html',
                        controller: 'PetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Pet', function(Pet) {
                                return Pet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('pet.delete', {
                parent: 'pet',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/pet/pet-delete-dialog.html',
                        controller: 'PetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Pet', function(Pet) {
                                return Pet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
