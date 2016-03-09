'use strict';

describe('Controller Tests', function() {

    describe('Adoption Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAdoption, MockPet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAdoption = jasmine.createSpy('MockAdoption');
            MockPet = jasmine.createSpy('MockPet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Adoption': MockAdoption,
                'Pet': MockPet
            };
            createController = function() {
                $injector.get('$controller')("AdoptionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:adoptionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
