(function() {
    'use strict';

    angular
        .module('hippoApp')
        .controller('EntitlementMySuffixDeleteController',EntitlementMySuffixDeleteController);

    EntitlementMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Entitlement'];

    function EntitlementMySuffixDeleteController($uibModalInstance, entity, Entitlement) {
        var vm = this;

        vm.entitlement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Entitlement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
