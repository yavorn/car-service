'use strict';


let carsTable = $('#cars-table').dataTable({
    "columnDefs": [
        {
            "orderable": false, "targets": [6, 7],
            "searchable": false
        },
        {
            "targets": [0, 1],
            "visible": false
        }
    ]
});

$('#select-customer').on('change', function () {
    let selectedValue = $(this).val();
    if (selectedValue === 'All') {
        carsTable.fnFilter('', 0);
        carsTable.fnFilter('');
    } else {
        carsTable.fnFilter("^" + selectedValue + "$", 0, true); //Exact value, column, reg
    }
});


function addProcedure(customerCarId) {
    window.location.href = '/carevents/add-carevent/' + customerCarId;
}

function viewProcedures(customerCarId) {
    window.location.href = '/customers/car/' + customerCarId;
}

$(function () {
    $('[data-toggle="popover"]').popover({
        trigger: 'focus'
    })
});
