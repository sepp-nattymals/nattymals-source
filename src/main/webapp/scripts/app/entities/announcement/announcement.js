'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('announcement', {
                parent: 'entity',
                url: '/announcements',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                    pageTitle: 'nattymalsApp.announcement.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/announcement/announcements.html',
                        controller: 'AnnouncementController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('announcement');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('announcement.detail', {
                parent: 'entity',
                url: '/announcement/{id}',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                    pageTitle: 'nattymalsApp.announcement.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/announcement/announcement-detail.html',
                        controller: 'AnnouncementDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('announcement');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Announcement', function($stateParams, Announcement) {
                        return Announcement.get({id : $stateParams.id});
                    }]
                }
            })
            .state('announcement.new', {
                parent: 'announcement',
                url: '/new',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/announcement/announcement-dialog.html',
                        controller: 'AnnouncementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    companyName: null,
                                    startDate: null,
                                    endDate: null,
                                    photo: null,
                                    photoContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('announcement', null, { reload: true });
                    }, function() {
                        $state.go('announcement');
                    })
                }]
            })
            .state('announcement.edit', {
                parent: 'announcement',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/announcement/announcement-dialog.html',
                        controller: 'AnnouncementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Announcement', function(Announcement) {
                                return Announcement.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('announcement', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('announcement.delete', {
                parent: 'announcement',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/announcement/announcement-delete-dialog.html',
                        controller: 'AnnouncementDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Announcement', function(Announcement) {
                                return Announcement.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('announcement', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
