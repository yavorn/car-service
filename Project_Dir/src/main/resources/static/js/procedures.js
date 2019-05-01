'use strict';
let apiUrl = 'http://localhost:8080/';

//Initilize Procedure table
let table = $('#procedures-table').DataTable( {
    "columnDefs": [ {
        "searchable": false,
        "orderable": false,
        "targets": 0
    } ],
    "order": [[ 1, 'asc' ]]
} );

table.on( 'order.dt search.dt', function () {
    table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
        cell.innerHTML = i+1;
    } );
} ).draw();









function addProcedure() {

    $('#add-procedure-modal').modal();
}

function deleteProcedure() {
    let btnTarget = event.target;
    let id = $(btnTarget).val();
    $('#confirmation-modal').modal();
    $('#confirmation-yes').on('click', function (event) {
        $.ajax({
            url: apiUrl + 'procedures/' + id,
            type: 'DELETE',
            data: '_method=DELETE',

            success: function (data) {
                $('#info-modal').modal();
                document.getElementById('info-modal-text').innerHTML = "Procedure with name " + name + " was deleted!";
                console.log('Procedure deleted successfully!');
                console.log(JSON.stringify(data));
                location.reload();
            },
            error: function (error) {
                console.log('Error when deleting procedure!');
                console.log(JSON.stringify(error));
            }
        });
    });


}

