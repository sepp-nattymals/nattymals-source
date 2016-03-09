'use strict';

describe('Controller Tests', function() {

    describe('Clinic Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockClinic, MockVeterinarian;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockClinic = jasmine.createSpy('MockClinic');
            MockVeterinarian = jasmine.createSpy('MockVeterinarian');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Clinic': MockClinic,
                'Veterinarian': MockVeterinarian
            };
            createController = function() {
                $injector.get('$controller')("ClinicDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:clinicUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
