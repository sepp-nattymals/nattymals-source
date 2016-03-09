'use strict';

describe('Controller Tests', function() {

    describe('PetComment Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPetComment, MockPetOwner, MockPet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPetComment = jasmine.createSpy('MockPetComment');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            MockPet = jasmine.createSpy('MockPet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PetComment': MockPetComment,
                'PetOwner': MockPetOwner,
                'Pet': MockPet
            };
            createController = function() {
                $injector.get('$controller')("PetCommentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:petCommentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
