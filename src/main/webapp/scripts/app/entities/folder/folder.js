'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('folder', {
                parent: 'entity',
                url: '/folders',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.folder.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/folder/folders.html',
                        controller: 'FolderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('folder');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('folder.detail', {
                parent: 'entity',
                url: '/folder/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.folder.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/folder/folder-detail.html',
                        controller: 'FolderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('folder');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Folder', function($stateParams, Folder) {
                        return Folder.get({id : $stateParams.id});
                    }]
                }
            })
            .state('folder.new', {
                parent: 'folder',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/folder/folder-dialog.html',
                        controller: 'FolderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('folder', null, { reload: true });
                    }, function() {
                        $state.go('folder');
                    })
                }]
            })
            .state('folder.edit', {
                parent: 'folder',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/folder/folder-dialog.html',
                        controller: 'FolderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Folder', function(Folder) {
                                return Folder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('folder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('folder.delete', {
                parent: 'folder',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/folder/folder-delete-dialog.html',
                        controller: 'FolderDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Folder', function(Folder) {
                                return Folder.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('folder', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
