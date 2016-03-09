'use strict';

describe('Controller Tests', function() {

    describe('Discount Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDiscount, MockAdministrator;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDiscount = jasmine.createSpy('MockDiscount');
            MockAdministrator = jasmine.createSpy('MockAdministrator');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Discount': MockDiscount,
                'Administrator': MockAdministrator
            };
            createController = function() {
                $injector.get('$controller')("DiscountDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:discountUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
