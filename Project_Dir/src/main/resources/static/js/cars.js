'use strict';


let carsTable = $('#cars-table').dataTable({
    "columnDefs": [
        {
            "orderable": false, "targets": [6,7],
            "searchable": false
        },
        {
            "targets": [ 0 , 1 ],
            "visible": false
        }
    ]
});

$('#select-customer').on('change',function(){
    let selectedValue = $(this).val();
    carsTable.fnFilter("^"+selectedValue+"$", 0, true); //Exact value, column, reg
});


function addProcedure(customerCarId){
    window.location.href ='/carevents/add-carevent/' + customerCarId;
}

function viewProcedures(customerCarId) {
    window.location.href = '/customers/car/' + customerCarId;
}

$(function () {
    $('[data-toggle="popover"]').popover({
        trigger: 'focus'
    })
});


// $(document).ready(function() {
//     $('#cars-table').dataTable( {
//         initComplete: function () {
//             this.api().columns().every( function () {
//                 let column = this;
//                 let select = $('<select><option value=""></option></select>')
//                     .appendTo( $(column.footer()).empty() )
//                     .on( 'change', function () {
//                         let val = $.fn.dataTable.util.escapeRegex(
//                             $(this).val()
//                         );
//
//                         column
//                             .search( val ? '^'+val+'$' : '', true, false )
//                             .draw();
//                     } );
//
//                 column.data().unique().sort().each( function ( d, j ) {
//                     select.append( '<option value="'+d+'">'+d+'</option>' )
//                 } );
//             } );
//         }
//     } );
// } );