'use strict';

describe('Controller Tests', function() {

    describe('PetCompany Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPetCompany, MockContract;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPetCompany = jasmine.createSpy('MockPetCompany');
            MockContract = jasmine.createSpy('MockContract');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PetCompany': MockPetCompany,
                'Contract': MockContract
            };
            createController = function() {
                $injector.get('$controller')("PetCompanyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:petCompanyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
