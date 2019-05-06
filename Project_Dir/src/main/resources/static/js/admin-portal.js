'use strict';
let apiUrl = 'http://localhost:8080/';

function addCustomer(){
    let modal = $('#add-customer-modal');

    $('#add-username-input').val();
    $('#add-phone-input').val();
    $('#add-name-input').val();
    modal.modal();
}

$(function () {
    $('[data-toggle="popover"]').popover({
        trigger: 'focus'
    })
});

