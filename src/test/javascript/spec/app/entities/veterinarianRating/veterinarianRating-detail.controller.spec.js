'use strict';

describe('Controller Tests', function() {

    describe('VeterinarianRating Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockVeterinarianRating, MockVeterinarian, MockPetOwner;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVeterinarianRating = jasmine.createSpy('MockVeterinarianRating');
            MockVeterinarian = jasmine.createSpy('MockVeterinarian');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'VeterinarianRating': MockVeterinarianRating,
                'Veterinarian': MockVeterinarian,
                'PetOwner': MockPetOwner
            };
            createController = function() {
                $injector.get('$controller')("VeterinarianRatingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:veterinarianRatingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
