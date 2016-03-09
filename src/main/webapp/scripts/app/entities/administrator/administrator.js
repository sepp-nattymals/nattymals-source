'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('administrator', {
                parent: 'entity',
                url: '/administrators',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.administrator.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/administrator/administrators.html',
                        controller: 'AdministratorController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('administrator');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('administrator.detail', {
                parent: 'entity',
                url: '/administrator/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.administrator.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/administrator/administrator-detail.html',
                        controller: 'AdministratorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('administrator');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Administrator', function($stateParams, Administrator) {
                        return Administrator.get({id : $stateParams.id});
                    }]
                }
            })
            .state('administrator.new', {
                parent: 'administrator',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/administrator/administrator-dialog.html',
                        controller: 'AdministratorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('administrator', null, { reload: true });
                    }, function() {
                        $state.go('administrator');
                    })
                }]
            })
            .state('administrator.edit', {
                parent: 'administrator',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/administrator/administrator-dialog.html',
                        controller: 'AdministratorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Administrator', function(Administrator) {
                                return Administrator.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('administrator', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('administrator.delete', {
                parent: 'administrator',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/administrator/administrator-delete-dialog.html',
                        controller: 'AdministratorDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Administrator', function(Administrator) {
                                return Administrator.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('administrator', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
