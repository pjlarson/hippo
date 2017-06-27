(function() {
    'use strict';

    angular
        .module('hippoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entitlement-my-suffix', {
            parent: 'entity',
            url: '/entitlement-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hippoApp.entitlement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entitlement/entitlementsmySuffix.html',
                    controller: 'EntitlementMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entitlement');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entitlement-my-suffix-detail', {
            parent: 'entitlement-my-suffix',
            url: '/entitlement-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hippoApp.entitlement.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entitlement/entitlement-my-suffix-detail.html',
                    controller: 'EntitlementMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entitlement');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Entitlement', function($stateParams, Entitlement) {
                    return Entitlement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'entitlement-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entitlement-my-suffix-detail.edit', {
            parent: 'entitlement-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitlement/entitlement-my-suffix-dialog.html',
                    controller: 'EntitlementMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entitlement', function(Entitlement) {
                            return Entitlement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entitlement-my-suffix.new', {
            parent: 'entitlement-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitlement/entitlement-my-suffix-dialog.html',
                    controller: 'EntitlementMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                appCode: null,
                                enabled: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('entitlement-my-suffix', null, { reload: 'entitlement-my-suffix' });
                }, function() {
                    $state.go('entitlement-my-suffix');
                });
            }]
        })
        .state('entitlement-my-suffix.edit', {
            parent: 'entitlement-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitlement/entitlement-my-suffix-dialog.html',
                    controller: 'EntitlementMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entitlement', function(Entitlement) {
                            return Entitlement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entitlement-my-suffix', null, { reload: 'entitlement-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entitlement-my-suffix.delete', {
            parent: 'entitlement-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entitlement/entitlement-my-suffix-delete-dialog.html',
                    controller: 'EntitlementMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entitlement', function(Entitlement) {
                            return Entitlement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entitlement-my-suffix', null, { reload: 'entitlement-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
