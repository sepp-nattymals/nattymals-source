'use strict';

describe('Controller Tests', function() {

    describe('PetRating Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPetRating, MockPetOwner, MockPet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPetRating = jasmine.createSpy('MockPetRating');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            MockPet = jasmine.createSpy('MockPet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PetRating': MockPetRating,
                'PetOwner': MockPetOwner,
                'Pet': MockPet
            };
            createController = function() {
                $injector.get('$controller')("PetRatingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:petRatingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
