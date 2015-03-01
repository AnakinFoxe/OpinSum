var aspectServices = angular.module("aspectServices", []);

aspectServices.factory("PhonesService",["$http","$q", function($http, $q){
	var mService = {};
	mService.getPhones = function(urlString, searchString){
		var state = $q.defer();
		$http.get(urlString,{
            params: {
                keyword : searchString
            }})
            .success(function(data) {
                var arrayOfPhone = [];
                while(data.length){
                    arrayOfPhone.push(data.splice(0,3));
                }
                state.resolve(arrayOfPhone);
        	});
        mService.state = state.promise;
        return state.promise;
	}

	return mService;

}]);

aspectServices.factory("PhoneService",["$http","$q",function($http, $q){
	var mService = {};
	mService.getPhone = function(urlString){
		var phone = $q.defer();
		$http.get(urlString)
            .success(function(data) {
                phone.resolve(data);
        	});
        return phone.promise;
	}

	return mService;

}]);

aspectServices.service("ApplicationUserState",function(){
    this.firstStartApp = true;
});

