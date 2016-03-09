'use strict';

describe('Controller Tests', function() {

    describe('Folder Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFolder, MockCustomer, MockMessage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFolder = jasmine.createSpy('MockFolder');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockMessage = jasmine.createSpy('MockMessage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Folder': MockFolder,
                'Customer': MockCustomer,
                'Message': MockMessage
            };
            createController = function() {
                $injector.get('$controller')("FolderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nattymalsApp:folderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
