'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('clinic', {
                parent: 'entity',
                url: '/clinics',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                    pageTitle: 'nattymalsApp.clinic.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clinic/clinics.html',
                        controller: 'ClinicController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clinic');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('clinic.detail', {
                parent: 'entity',
                url: '/clinic/{id}',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                    pageTitle: 'nattymalsApp.clinic.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clinic/clinic-detail.html',
                        controller: 'ClinicDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clinic');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Clinic', function($stateParams, Clinic) {
                        return Clinic.get({id : $stateParams.id});
                    }]
                }
            })
            .state('clinic.new', {
                parent: 'clinic',
                url: '/new',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/clinic/clinic-dialog.html',
                        controller: 'ClinicDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    address: null,
                                    city: null,
                                    province: null,
                                    schedule: null,
                                    phoneNumber: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('clinic', null, { reload: true });
                    }, function() {
                        $state.go('clinic');
                    })
                }]
            })
            .state('clinic.edit', {
                parent: 'clinic',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/clinic/clinic-dialog.html',
                        controller: 'ClinicDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Clinic', function(Clinic) {
                                return Clinic.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('clinic', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('clinic.delete', {
                parent: 'clinic',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_PETOWNER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/clinic/clinic-delete-dialog.html',
                        controller: 'ClinicDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Clinic', function(Clinic) {
                                return Clinic.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('clinic', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
