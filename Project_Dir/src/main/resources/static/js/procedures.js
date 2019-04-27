$(document).ready(function () {
    let apiUrl = 'http://localhost:8080/';
    $('#procedures-table').DataTable();


    $('#delete-procedure-button').click(function(e) {
        e.preventDefault();
        let id = $('#delete-procedure-button').val();
        $.ajax({
            type: 'PUT',
            url: apiUrl + 'procedures/delete/' + id,
            data: '_method=PUT',
            dataType: 'json',
            success: function() {
                alert('ok');
            },
            error: function(result) {
                alert('error');
            }
        });
    });
    

});