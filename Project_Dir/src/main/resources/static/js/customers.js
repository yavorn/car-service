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

let carsTable = $('#cars-table').DataTable({
    autoWidth: false,
    columns: [
        {width: '50px'},
        {width: '50px'},
        {width: '50px'},
        {width: '50px'},
        {width: '50px'},
        {width: '50px'}
    ]
});

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

