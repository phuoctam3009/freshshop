$(document).ready(function(){
    $(".c-input-text").change(function(e){
        var id = e.target.id;
        var quantity = e.target.value;
        var data = {
            "productid" : id,
            "quantity" : quantity
        }
        var url = "/cart/update"
        $.ajax({
            type: "POST",
            url: url,
            dateType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data)
        }).done(function (response){
            if(response === 'Success'){
                location.reload();
            }else{
            console.log(response);
            $(".modal-title").text("Shopping Cart");
            $("#modalBody").text(response);
            $("#myModal").modal();
            }
        }).fail(function() {
            $(".modal-title").text("Shopping Cart");
            $("#modalBody").text("Error while updating product");
            $("#myModal").modal();
        });

    });
})
