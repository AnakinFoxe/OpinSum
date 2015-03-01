var aspectControllers = angular.module("aspectControllers", []);

aspectControllers.controller('PhoneListController', ['$scope', '$http', 'PhonesService', 'ApplicationUserState',
    function($scope, $http, phonesService,ApplicationUserState) {
        if(ApplicationUserState.firstStartApp){
            $scope.showSearchBoxDropDown = true;
            ApplicationUserState.firstStartApp = false;
        }
        $scope.mPhonesService = phonesService;
        $scope.arrayOfPhone = [];
        //tell service to do the query
        $scope.submit = function() {
            phonesService.getPhones('http://anakinfoxe.com/webapi/query',$scope.text);
        }
        //if the model change replace the model
        $scope.$watch('mPhonesService.state',function(){
            if(phonesService.state){
                phonesService.state.then(function(data){
                    $scope.arrayOfPhone = data;
                });
            }
        });
    }
]);

aspectControllers.controller('PhoneController', ['$scope', '$http', '$routeParams','$sce', 'PhoneService',
    function($scope, $http, $routeParams, $sce, phoneService) {
        //query phone by id
        $scope.description = "";
        $scope.setDescription = function(data){
            $scope.description = $sce.trustAsHtml(data);
        }
        phoneService.getPhone('http://anakinfoxe.com/webapi/device/' 
            + $routeParams.phoneId + '/summaries').then(function(data){
            $scope.phone = data;
        });
    }
]);


