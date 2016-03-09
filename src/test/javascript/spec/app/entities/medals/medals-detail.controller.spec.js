'use strict';

describe('Controller Tests', function() {

    describe('Medals Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMedals, MockPetOwner;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMedals = jasmine.createSpy('MockMedals');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Medals': MockMedals,
                'PetOwner': MockPetOwner
            };
            createController = function() {
                $injector.get('$controller')("MedalsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:medalsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
