'use strict';
let apiUrl = 'http://localhost:8080/';

//Initialize Procedure table
$('#procedures-table').DataTable({
    "columnDefs": [
        {"orderable": false, "targets": [2, 3]}
    ]
});


$('#test-button').on('click', function (e) {
    e.preventDefault();

    let test = $('#test-true').val();

    if (checkIfProcedureExists(test).valueOf()){
        alert ('duplicate')
    } else {
        alert('not duplicate')
    }

});


function addProcedure() {
    $('#add-procedure-modal').modal();

    $('#add-procedure-form').on('submit', function (e) {
        e.preventDefault();

        let procedureName = $('#procedure-name-input').val();
        let procedurePrice = $('#procedure-price-input').val();

        if (checkIfProcedureExists(procedureName) === 'true') {
            console.log("Duplicate");
        }
        console.log("NOT duplicated!");


        let payload = {
            'procedureName': procedureName,
            'procedurePrice': procedurePrice
        };

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: apiUrl + 'procedures',
            type: 'POST',
            dataType: "json",
            data: JSON.stringify(payload),

            success: function (data) {
                $('#info-modal').modal();
                document.getElementById('info-modal-text').innerHTML = "Procedure with name " + name + " was created!";
                console.log('Procedure created successfully!');
                console.log(JSON.stringify(payload));
                $('#add-procedure-modal').modal('hide');
                location.reload();
            },
            error: function (error) {
                console.log('Error function triggered when creating procedure!');
                console.log(JSON.stringify(payload));
                $('#add-procedure-modal').modal('hide');
                location.reload();
            }
        });

    })

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
    let result = true;
    $.ajax({
        url: apiUrl + 'procedures/check/' + procedureName,
        type: 'GET',
        data: '_method=GET',

        success: function (data) {
            console.log('OK');
            console.log(data);
            result = JSON.parse(data);
        },
        error: function (error) {
            console.log('Error with checking if procedure exists!');
            console.log(JSON.stringify(error));
        }
    });

    return result;
}