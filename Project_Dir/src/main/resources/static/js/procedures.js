'use strict';
$(document).ready(function () {

    $('#procedures-table').DataTable();

});

let apiUrl = 'http://localhost:8080/';

function addProcedure(){

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

            success: function(data) {
                $('#info-modal').modal();
                document.getElementById('info-modal-text').innerHTML = "Procedure with name " + name + " was deleted!";
                console.log('Procedure deleted successfully!');
                console.log(JSON.stringify(data));
                location.reload();
            },
            error: function(error) {
                console.log('Error when deleting procedure!');
                console.log(JSON.stringify(error));
            }
        });
    });


}

