'use strict';

describe('Controller Tests', function() {

    describe('Pet Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPet, MockPetOwner, MockPetComment, MockAdoption, MockPetRating;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPet = jasmine.createSpy('MockPet');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            MockPetComment = jasmine.createSpy('MockPetComment');
            MockAdoption = jasmine.createSpy('MockAdoption');
            MockPetRating = jasmine.createSpy('MockPetRating');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Pet': MockPet,
                'PetOwner': MockPetOwner,
                'PetComment': MockPetComment,
                'Adoption': MockAdoption,
                'PetRating': MockPetRating
            };
            createController = function() {
                $injector.get('$controller')("PetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:petUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
