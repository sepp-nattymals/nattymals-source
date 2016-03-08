'use strict';

describe('Controller Tests', function() {

    describe('Payment Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPayment, MockActor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPayment = jasmine.createSpy('MockPayment');
            MockActor = jasmine.createSpy('MockActor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Payment': MockPayment,
                'Actor': MockActor
            };
            createController = function() {
                $injector.get('$controller')("PaymentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:paymentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
