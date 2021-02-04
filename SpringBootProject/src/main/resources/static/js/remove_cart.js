$(document).ready(function() {
    $(".link-remove").on("click", function(evt){
       evt.preventDefault();
       removeFromCart($(this));
    });

    $("#close-modal").click(function(){
        location.reload();
    })
});

function removeFromCart(link){
    url = link.attr("href");
        $.ajax({
            type: "POST",
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
}
