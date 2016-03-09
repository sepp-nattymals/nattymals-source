'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('veterinarianRating', {
                parent: 'entity',
                url: '/veterinarianRatings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.veterinarianRating.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/veterinarianRating/veterinarianRatings.html',
                        controller: 'VeterinarianRatingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('veterinarianRating');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('veterinarianRating.detail', {
                parent: 'entity',
                url: '/veterinarianRating/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.veterinarianRating.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/veterinarianRating/veterinarianRating-detail.html',
                        controller: 'VeterinarianRatingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('veterinarianRating');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VeterinarianRating', function($stateParams, VeterinarianRating) {
                        return VeterinarianRating.get({id : $stateParams.id});
                    }]
                }
            })
            .state('veterinarianRating.new', {
                parent: 'veterinarianRating',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/veterinarianRating/veterinarianRating-dialog.html',
                        controller: 'VeterinarianRatingDialogController',
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
                        $state.go('veterinarianRating', null, { reload: true });
                    }, function() {
                        $state.go('veterinarianRating');
                    })
                }]
            })
            .state('veterinarianRating.edit', {
                parent: 'veterinarianRating',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/veterinarianRating/veterinarianRating-dialog.html',
                        controller: 'VeterinarianRatingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['VeterinarianRating', function(VeterinarianRating) {
                                return VeterinarianRating.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('veterinarianRating', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('veterinarianRating.delete', {
                parent: 'veterinarianRating',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/veterinarianRating/veterinarianRating-delete-dialog.html',
                        controller: 'VeterinarianRatingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['VeterinarianRating', function(VeterinarianRating) {
                                return VeterinarianRating.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('veterinarianRating', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
