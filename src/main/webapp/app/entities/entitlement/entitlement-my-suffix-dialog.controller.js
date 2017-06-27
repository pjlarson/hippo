(function() {
    'use strict';

    angular
        .module('hippoApp')
        .controller('EntitlementMySuffixDialogController', EntitlementMySuffixDialogController);

    EntitlementMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entitlement', 'Employee'];

    function EntitlementMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Entitlement, Employee) {
        var vm = this;

        vm.entitlement = entity;
        vm.clear = clear;
        vm.save = save;
        vm.employees = Employee.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entitlement.id !== null) {
                Entitlement.update(vm.entitlement, onSaveSuccess, onSaveError);
            } else {
                Entitlement.save(vm.entitlement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hippoApp:entitlementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
