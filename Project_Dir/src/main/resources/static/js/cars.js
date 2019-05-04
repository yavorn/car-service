'use strict';


let carsTable = $('#cars-table').dataTable({
    "columnDefs": [{
        "visible": false, "targets": [0],
        "bProcessing": true
    }
    ]
});

$('#select-customer').on('change',function(){
    let selectedValue = $(this).val();
    carsTable.fnFilter("^"+selectedValue+"$", 0, true); //Exact value, column, reg
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