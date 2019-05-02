'use strict';
let apiUrl = 'http://localhost:8080/';

//Initialize Procedure table
$('#procedures-table').DataTable();





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

function checkIfProcedureExists(procedureName) {
    let result = false;

    $.ajax({
        url: apiUrl + 'procedures/check/' + procedureName,
        type: 'GET',
        data: '_method=GET',

        success: function (data) {
            console.log('OK');
            console.log(JSON.stringify(data));
            result = data;
        },
        error: function (error) {
            console.log('Error with checking if procedure exists!');
            console.log(JSON.stringify(error));
        }
    });
    return result;
}