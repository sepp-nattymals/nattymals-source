'use strict';

describe('Controller Tests', function() {

    describe('PetOwner Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPetOwner, MockHistoricalPoints, MockVeterinarianComment, MockPetComment, MockMedals, MockPet, MockPetRating, MockVeterinarianRating;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            MockHistoricalPoints = jasmine.createSpy('MockHistoricalPoints');
            MockVeterinarianComment = jasmine.createSpy('MockVeterinarianComment');
            MockPetComment = jasmine.createSpy('MockPetComment');
            MockMedals = jasmine.createSpy('MockMedals');
            MockPet = jasmine.createSpy('MockPet');
            MockPetRating = jasmine.createSpy('MockPetRating');
            MockVeterinarianRating = jasmine.createSpy('MockVeterinarianRating');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PetOwner': MockPetOwner,
                'HistoricalPoints': MockHistoricalPoints,
                'VeterinarianComment': MockVeterinarianComment,
                'PetComment': MockPetComment,
                'Medals': MockMedals,
                'Pet': MockPet,
                'PetRating': MockPetRating,
                'VeterinarianRating': MockVeterinarianRating
            };
            createController = function() {
                $injector.get('$controller')("PetOwnerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:petOwnerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
