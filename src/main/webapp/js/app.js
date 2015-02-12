var aspectApp = angular.module('Aspect', ['ngRoute','aspectControllers','highcharts','aspectServices']);

aspectApp.config(['$routeProvider',function($routeProvider){
	$routeProvider.
		when('/phones',{
			templateUrl: 'views/phones.html',
			controller: 'PhoneListController'
		}).
		when('/phones/:phoneId',{
			templateUrl: 'views/phone.html',
			controller: 'PhoneController'
		}).
		otherwise({
			redirectTo: '/phones'
		});
}]);