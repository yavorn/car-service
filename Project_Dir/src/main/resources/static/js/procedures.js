$(document).ready(function () {
    let apiUrl = 'http://localhost:8080/';
    $('#procedures-table').DataTable();


    $('#delete-procedure-button').click(function(e) {
        e.preventDefault();
        let id = $('#delete-procedure-button').val();

        $.ajax({
            url: apiUrl + 'procedures/delete/' + id,
            type: 'GET',
            data: '_method=GET',

            success: function() {
                alert('ok');
            },
            error: function(result) {
                alert('error');
            }
        });
    });
    

});