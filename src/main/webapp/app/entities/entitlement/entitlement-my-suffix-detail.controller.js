(function() {
    'use strict';

    angular
        .module('hippoApp')
        .controller('EntitlementMySuffixDetailController', EntitlementMySuffixDetailController);

    EntitlementMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entitlement', 'Employee'];

    function EntitlementMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Entitlement, Employee) {
        var vm = this;

        vm.entitlement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hippoApp:entitlementUpdate', function(event, result) {
            vm.entitlement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
