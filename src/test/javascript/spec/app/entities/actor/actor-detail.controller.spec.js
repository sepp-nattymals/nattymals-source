'use strict';

describe('Controller Tests', function() {

    describe('Actor Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockActor, MockPayment, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockActor = jasmine.createSpy('MockActor');
            MockPayment = jasmine.createSpy('MockPayment');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Actor': MockActor,
                'Payment': MockPayment,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ActorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:actorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
