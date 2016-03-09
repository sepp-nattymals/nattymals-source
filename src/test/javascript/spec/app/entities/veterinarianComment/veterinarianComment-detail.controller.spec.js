'use strict';

describe('Controller Tests', function() {

    describe('VeterinarianComment Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockVeterinarianComment, MockVeterinarian, MockPetOwner;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVeterinarianComment = jasmine.createSpy('MockVeterinarianComment');
            MockVeterinarian = jasmine.createSpy('MockVeterinarian');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'VeterinarianComment': MockVeterinarianComment,
                'Veterinarian': MockVeterinarian,
                'PetOwner': MockPetOwner
            };
            createController = function() {
                $injector.get('$controller')("VeterinarianCommentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:veterinarianCommentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
