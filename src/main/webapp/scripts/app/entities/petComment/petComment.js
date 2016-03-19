'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('petComment', {
                parent: 'entity',
                url: '/petComments',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.petComment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petComment/petComments.html',
                        controller: 'PetCommentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petComment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('petComment.detail', {
                parent: 'entity',
                url: '/petComment/{id}',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.petComment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petComment/petComment-detail.html',
                        controller: 'PetCommentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petComment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PetComment', function($stateParams, PetComment) {
                        return PetComment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('petComment.new', {
                parent: 'petComment',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petComment/petComment-dialog.html',
                        controller: 'PetCommentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    creationDate: null,
                                    text: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('petComment', null, { reload: true });
                    }, function() {
                        $state.go('petComment');
                    })
                }]
            })
            .state('petComment.edit', {
                parent: 'petComment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petComment/petComment-dialog.html',
                        controller: 'PetCommentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PetComment', function(PetComment) {
                                return PetComment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petComment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('petComment.delete', {
                parent: 'petComment',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petComment/petComment-delete-dialog.html',
                        controller: 'PetCommentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PetComment', function(PetComment) {
                                return PetComment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petComment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
