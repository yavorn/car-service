$(document).ready(function () {
    let apiUrl = 'http://localhost:8080/';
    $('#procedures-table').DataTable();

});

function deleteProcedure() {

    let id = $('#delete-procedure-button').val();

    $.ajax({
        url: 'http://localhost:8080/procedures/' + id + '/',
        type: 'DELETE',
        data: '_method=DELETE',

        success: function() {
            alert('ok');
        },
        error: function(result) {
            alert('error');
        }
    });
}