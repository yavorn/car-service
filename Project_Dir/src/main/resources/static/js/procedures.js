'use strict';
let apiUrl = 'http://localhost:8080/';

//Initialize Procedure table
$('#procedures-table').DataTable();





function addProcedure() {
    $('#add-procedure-modal').modal();

    $('#add-procedure-form').on("submit", function (e){
        e.preventDefault();

        let procedureName = $('#procedure-name-input').val();
        let procedurePrice = $('#procedure-price-input').val();

        if(checkIfProcedureExists(procedureName)){
           console.log ("Duplicate");
        } else {
            console.log("NOT duplicated!");
        }

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
    let result ='';
    $.ajax({
        url: apiUrl + 'procedures/check/' + procedureName,
        type: 'GET',
        data: '_method=GET',

        success: function (data) {
            console.log('OK');
            console.log(JSON.stringify(data));
            console.log(data);
            result = data;
        },
        error: function (error) {
            console.log('Error with checking if procedure exists!');
            console.log(JSON.stringify(error));
        }
    });
   if (JSON.stringify(result) === 'true') {
       return true;
   }
    if (JSON.stringify(result) === 'false') {
        return false;
    }
}