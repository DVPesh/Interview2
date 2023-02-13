angular.module('university', ['ngStorage']).controller('indexController',
    function ($scope, $http, $window, $localStorage) {

        const contextPath = 'http://localhost:8189/university/api/v1/students';

        $scope.getStudents = function () {
            $http.get(contextPath)
                .then(function (response) {
                    $scope.students = response.data;
                });
        }

        $scope.createStudent = function () {
            $http.post(contextPath, $scope.student)
                .then(function successCallback(response) {
                    $scope.student = null;
                    $scope.getStudents();
                }, function errorCallback(response) {
                    if (response.data) {
                        alert(response.data.error)
                    }
                });
        }

        $scope.deleteStudent = function (id) {
            $http.delete(contextPath + '/' + id)
                .then(function (response) {
                    $scope.getStudents();
                });
        }

        $scope.updateStudent = function (id) {
            $localStorage.id = id;
            $window.location.href = 'student.html';
        }

        $scope.getStudents();
    });
