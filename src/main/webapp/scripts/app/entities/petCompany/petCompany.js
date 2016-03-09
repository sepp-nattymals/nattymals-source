'use strict';

angular.module('nattymalsApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('petCompany', {
                parent: 'entity',
                url: '/petCompanys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.petCompany.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petCompany/petCompanys.html',
                        controller: 'PetCompanyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petCompany');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('petCompany.detail', {
                parent: 'entity',
                url: '/petCompany/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'nattymalsApp.petCompany.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/petCompany/petCompany-detail.html',
                        controller: 'PetCompanyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('petCompany');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PetCompany', function($stateParams, PetCompany) {
                        return PetCompany.get({id : $stateParams.id});
                    }]
                }
            })
            .state('petCompany.new', {
                parent: 'petCompany',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petCompany/petCompany-dialog.html',
                        controller: 'PetCompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    bankAccount: null,
                                    nif: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('petCompany', null, { reload: true });
                    }, function() {
                        $state.go('petCompany');
                    })
                }]
            })
            .state('petCompany.edit', {
                parent: 'petCompany',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petCompany/petCompany-dialog.html',
                        controller: 'PetCompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PetCompany', function(PetCompany) {
                                return PetCompany.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petCompany', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('petCompany.delete', {
                parent: 'petCompany',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/petCompany/petCompany-delete-dialog.html',
                        controller: 'PetCompanyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PetCompany', function(PetCompany) {
                                return PetCompany.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('petCompany', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
