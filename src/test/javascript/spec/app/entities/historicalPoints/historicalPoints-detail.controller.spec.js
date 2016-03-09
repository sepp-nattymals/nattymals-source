'use strict';

describe('Controller Tests', function() {

    describe('HistoricalPoints Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockHistoricalPoints, MockPetOwner;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockHistoricalPoints = jasmine.createSpy('MockHistoricalPoints');
            MockPetOwner = jasmine.createSpy('MockPetOwner');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'HistoricalPoints': MockHistoricalPoints,
                'PetOwner': MockPetOwner
            };
            createController = function() {
                $injector.get('$controller')("HistoricalPointsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:historicalPointsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
