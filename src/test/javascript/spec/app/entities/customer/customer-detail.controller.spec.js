'use strict';

describe('Controller Tests', function() {

    describe('Customer Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCustomer, MockPremiumSubscription, MockFolder;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockPremiumSubscription = jasmine.createSpy('MockPremiumSubscription');
            MockFolder = jasmine.createSpy('MockFolder');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Customer': MockCustomer,
                'PremiumSubscription': MockPremiumSubscription,
                'Folder': MockFolder
            };
            createController = function() {
                $injector.get('$controller')("CustomerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:customerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
