'use strict';

describe('Controller Tests', function() {

    describe('Administrator Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAdministrator, MockDiscount, MockAnnouncement;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAdministrator = jasmine.createSpy('MockAdministrator');
            MockDiscount = jasmine.createSpy('MockDiscount');
            MockAnnouncement = jasmine.createSpy('MockAnnouncement');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Administrator': MockAdministrator,
                'Discount': MockDiscount,
                'Announcement': MockAnnouncement
            };
            createController = function() {
                $injector.get('$controller')("AdministratorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:administratorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
