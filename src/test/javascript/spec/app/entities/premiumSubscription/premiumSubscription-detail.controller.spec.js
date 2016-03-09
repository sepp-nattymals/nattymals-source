'use strict';

describe('Controller Tests', function() {

    describe('PremiumSubscription Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPremiumSubscription, MockCustomer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPremiumSubscription = jasmine.createSpy('MockPremiumSubscription');
            MockCustomer = jasmine.createSpy('MockCustomer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PremiumSubscription': MockPremiumSubscription,
                'Customer': MockCustomer
            };
            createController = function() {
                $injector.get('$controller')("PremiumSubscriptionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:premiumSubscriptionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
