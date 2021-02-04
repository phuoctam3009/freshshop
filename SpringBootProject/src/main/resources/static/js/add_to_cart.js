$(document).ready(function() {
    $(".cart").click(function(e){
        var x = e.target.id;
        url = "/cart/add?" + x;
        $.ajax({
            type: "GET",
            url: url
        }).done(function (response){
            $(".modal-title").text("Shopping Cart");
            $("#modalBody").text(response);
            $("#myModal").modal();
        }).fail(function() {
            $(".modal-title").text("Shopping Cart");
            $("#modalBody").text("Error while adding product to shopping cart");
            $("#myModal").modal();
        });
    })

    $(".deleteProduct").click(function(e){
        var y = e.target.id;
        alert(y);
    })

    $("#close-modal").click(function(){
        location.reload();
    })
});
