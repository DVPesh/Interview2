angular.module('student', ['ngStorage']).controller('studentController',
    function ($rootScope, $scope, $http, $window, $localStorage) {

        const contextPath = 'http://localhost:8189/university/api/v1/students';

        $scope.getStudent = function (id) {
            $http.get(contextPath + '/' + id)
                .then(function successCallback(response) {
                    $scope.student = response.data;
                }, function errorCallback(response) {
                    if (response.data) {
                        alert(response.data.error)
                    }
                });
        }

        $scope.updateStudent = function () {
            $http.put(contextPath, $scope.student)
                .then(function successCallback(response) {
                    $scope.student = null;
                    $window.location.href = 'index.html';
                }, function errorCallback(response) {
                    if (response.data) {
                        alert(response.data.error)
                    }
                });
        }

        $scope.getStudent($localStorage.id);
    });
