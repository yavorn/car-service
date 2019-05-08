'use strict';
let apiUrl = 'http://localhost:8080/';

function addUser() {
    let modal = $('#add-customer-modal');
    modal.modal();
}

function addAdmin() {
    let modal = $('#add-admin-modal');
    modal.modal();
}

function deleteUser(){
    let modal = $('#delete-user-modal');
    modal.modal();
}

$(function () {
    $('[data-toggle="popover"]').popover({
        trigger: 'focus'
    })
});

