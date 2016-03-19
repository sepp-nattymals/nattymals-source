'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('veterinarianComment', {
                parent: 'entity',
                url: '/veterinarianComments',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.veterinarianComment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/veterinarianComment/veterinarianComments.html',
                        controller: 'VeterinarianCommentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('veterinarianComment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('veterinarianComment.detail', {
                parent: 'entity',
                url: '/veterinarianComment/{id}',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                    pageTitle: 'nattymalsApp.veterinarianComment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/veterinarianComment/veterinarianComment-detail.html',
                        controller: 'VeterinarianCommentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('veterinarianComment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'VeterinarianComment', function($stateParams, VeterinarianComment) {
                        return VeterinarianComment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('veterinarianComment.new', {
                parent: 'veterinarianComment',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/veterinarianComment/veterinarianComment-dialog.html',
                        controller: 'VeterinarianCommentDialogController',
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
                        $state.go('veterinarianComment', null, { reload: true });
                    }, function() {
                        $state.go('veterinarianComment');
                    })
                }]
            })
            .state('veterinarianComment.edit', {
                parent: 'veterinarianComment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/veterinarianComment/veterinarianComment-dialog.html',
                        controller: 'VeterinarianCommentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['VeterinarianComment', function(VeterinarianComment) {
                                return VeterinarianComment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('veterinarianComment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('veterinarianComment.delete', {
                parent: 'veterinarianComment',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMINISTRATOR'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/veterinarianComment/veterinarianComment-delete-dialog.html',
                        controller: 'VeterinarianCommentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['VeterinarianComment', function(VeterinarianComment) {
                                return VeterinarianComment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('veterinarianComment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
