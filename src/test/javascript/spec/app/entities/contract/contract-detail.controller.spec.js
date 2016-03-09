'use strict';

describe('Controller Tests', function() {

    describe('Contract Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockContract, MockPetCompany;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockContract = jasmine.createSpy('MockContract');
            MockPetCompany = jasmine.createSpy('MockPetCompany');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Contract': MockContract,
                'PetCompany': MockPetCompany
            };
            createController = function() {
                $injector.get('$controller')("ContractDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:contractUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
