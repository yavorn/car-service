'use strict';
let apiUrl = 'http://localhost:8080/';

//Initialize Customers table

let table = $('#customers-table').DataTable({
    "columnDefs": [{
        "orderable": false, "targets": [3, 4],
        "bProcessing": true
    }
    ]
});

let carsTable = $('#cars-table').DataTable();

function createCar(email){
    $('#user-name').attr("value", email);
    let modal = $('#create-customerCar-modal');
    modal.modal();

}

function viewCars() {

    let modal = $('#view-cars-modal');
    modal.modal();
}

function addUser() {
    let modal = $('#add-customer-modal');
    modal.modal();
    modal.on('hidden.bs.modal', function () {
        table.reset();
    })
}

function addAdmin() {
    let modal = $('#add-admin-modal');
    modal.modal();
    modal.on('hidden.bs.modal', function () {
        table.reset();
    })
}

function deleteUser(email) {
    let modal = $('#confirmation-modal');
    modal.modal();

    $('#user-to-delete').attr("value", email);

    modal.on('hidden.bs.modal', function () {
        table.reset();
    })
}

$(function () {
    $('[data-toggle="popover"]').popover({
        trigger: 'focus'
    })
});