$(document).ready(function () {
    $('#customers-dropdown').on('change', (function (){
        let userID = $('#customers-dropdown').val();

        $('#test1').append(userID);
        $('#test2').append(userID);
    }))

});
//
// //return select user ID and list him cars
// $('#selectCustomer').on('change', (function () {
//     const userId = $('#selectCustomer').val();
//     $.ajax({
//         type: 'GET',
//         url: '/customer-cars/' + userId,
//         dataType: 'json',
//         success: function (data) {
//             $.each(data, function (i) {
//                 const option = "<option value = " + data[i].id + ">" + data[i].licensePlate + "</option>";
//                 $("#selectCar").append(option);
//             });
//         },
//         error: function () {
//             alert('error');
//         }
//     })
// }));
// let lastVisitId;
// //save selected customer car to table 'visit' in DB
// $('#selectCar').on('change', function () {
//     const customerCarId = $('#selectCar').val();
//     if (confirm('Do you want to get the car into the workshop')) {
//         $.ajax({
//             type: 'POST',
//             url: "/add-visit",
//             contentType: 'application/json; charset=utf-8',
//             data: JSON.stringify({customerCar: {id: customerCarId}, totalPrice: 0}),
//             dataType: 'json',
//             success: function (data) {
//                 lastVisitId = data;
//                 console.log(lastVisitId)
//             }
//         });
//     }
// });
// let totalPrice = 0;
// let listOfServices = {};
// // add services to 'Selected Services' and 'visit_services' in DB
// $('.service-button').on('click', event, (function () {
//     let btnTarget = event.target;
//     listOfServices.push(btnTarget);
//     alert(listOfServices);
//     let btnText = $(btnTarget).text();
//     const serviceId = $(btnTarget).val();
//     $('.list').append("<b><li class='addedService' id=" + serviceId + ">" + btnText + "</li></b>");
//     btnTarget.disabled = true;
//     $(btnTarget).addClass('selected-service');
//     $.ajax({
//         type: 'POST',
//         url: "/" + lastVisitId + "/add-service/" + serviceId,
//         dataType: 'json',
//         success: function (data) {
//             totalPrice = data;
//             console.log(totalPrice)
//             $('#totalPrice').replaceWith("<span id='totalPrice'>Total Price: " + totalPrice + "</span>");
//         }
//     });
// }));
// // remove service form 'Selected Services' and 'visit_services' in DB
// $('.list').on('click', 'li', event, (function () {
//     let targetLi = $(event.target);
//     let serviceId = $(event.target).attr('id');
//     targetLi.remove();
//     $.ajax({
//         type: 'DELETE',
//         url: "/" + lastVisitId + "/delete-service/" + serviceId,
//         dataType: 'json',
//         success: function (data) {
//             totalPrice = data;
//             $('#totalPrice').replaceWith("<span id='totalPrice'>Total Price: " + totalPrice + "</span>");
//         }
//     });
// }));
//
