function validateForm(){
    var firstDate = document.form.firstDate.value;
    var lastDate = document.form.lastDate.value;

    if(firstDate == null || lastDate == null){
        return false;
    }

    return true;
}

function getTrucks(){
    $.ajax({
        type: "post",
        url: "http://localhost:8080/horaires-camions",
        cache: false,
        data:"firstDate=" + $("#firstDate").val() + "&lastDate=" + $("#lastDate").val(),
        success: function(response){
            alert("Oui");
            $('#nbCamions').html("");
            var obj = JSON.parse(response);
            $('#nbCamions').html("Nombre de camion(s) trouvé(s): " + obj.features.size());
        },
        error: function(){
            alert('Erreur lors de la requête');
        }
    });
}