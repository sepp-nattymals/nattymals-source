'use strict';

describe('Controller Tests', function() {

    describe('Veterinarian Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockVeterinarian, MockClinic, MockVeterinarianRating, MockVeterinarianComment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockVeterinarian = jasmine.createSpy('MockVeterinarian');
            MockClinic = jasmine.createSpy('MockClinic');
            MockVeterinarianRating = jasmine.createSpy('MockVeterinarianRating');
            MockVeterinarianComment = jasmine.createSpy('MockVeterinarianComment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Veterinarian': MockVeterinarian,
                'Clinic': MockClinic,
                'VeterinarianRating': MockVeterinarianRating,
                'VeterinarianComment': MockVeterinarianComment
            };
            createController = function() {
                $injector.get('$controller')("VeterinarianDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:veterinarianUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
