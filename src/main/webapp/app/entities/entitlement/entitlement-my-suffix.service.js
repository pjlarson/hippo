(function() {
    'use strict';
    angular
        .module('hippoApp')
        .factory('Entitlement', Entitlement);

    Entitlement.$inject = ['$resource'];

    function Entitlement ($resource) {
        var resourceUrl =  'api/entitlements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
