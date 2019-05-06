'use strict';


let carsTable = $('#customer-portal-table').DataTable({
    "columnDefs": [
        {
            "orderable": false, "targets": [6]
        }
    ]
});

function viewProcedures(customerCarId) {
    window.location.href = '/customers/car/' + customerCarId;
}

