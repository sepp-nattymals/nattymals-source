'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('petRating', {
                parent: 'entity',
                url: '/petRatings',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                    pageTitle: 'nattymalsApp.petRating.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petRating/petRatings.html',
                        controller: 'PetRatingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petRating');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('petRating.detail', {
                parent: 'entity',
                url: '/petRating/{id}',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                    pageTitle: 'nattymalsApp.petRating.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petRating/petRating-detail.html',
                        controller: 'PetRatingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petRating');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PetRating', function($stateParams, PetRating) {
                        return PetRating.get({id : $stateParams.id});
                    }]
                }
            })
            .state('petRating.new', {
                parent: 'petRating',
                url: '/new',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petRating/petRating-dialog.html',
                        controller: 'PetRatingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    rating: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('petRating', null, { reload: true });
                    }, function() {
                        $state.go('petRating');
                    })
                }]
            })
            .state('petRating.edit', {
                parent: 'petRating',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petRating/petRating-dialog.html',
                        controller: 'PetRatingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PetRating', function(PetRating) {
                                return PetRating.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petRating', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('petRating.delete', {
                parent: 'petRating',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petRating/petRating-delete-dialog.html',
                        controller: 'PetRatingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PetRating', function(PetRating) {
                                return PetRating.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petRating', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
