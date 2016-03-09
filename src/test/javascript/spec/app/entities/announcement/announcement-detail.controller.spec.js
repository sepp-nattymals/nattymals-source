'use strict';

describe('Controller Tests', function() {

    describe('Announcement Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAnnouncement, MockAdministrator;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAnnouncement = jasmine.createSpy('MockAnnouncement');
            MockAdministrator = jasmine.createSpy('MockAdministrator');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Announcement': MockAnnouncement,
                'Administrator': MockAdministrator
            };
            createController = function() {
                $injector.get('$controller')("AnnouncementDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:announcementUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
