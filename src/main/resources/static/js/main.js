function validateDate(){
    var firstDate = document.getElementById("firstDate").value;
    var lastDate = document.getElementById("lastDate").value;

    if(!firstDate || !lastDate ){
        alert("Veuillez entrer deux dates valides.");
        return false;
    }

    var fDate = new Date(firstDate);
    var lDate = new Date(lastDate);

    if(firstDate > lastDate){
        alert("La deuxième date doit être plus grande ou égale à la première.");
        return false;
    }

    getTrucks(firstDate, lastDate);
}

function getTrucks(firstDate, lastDate){
    var url = "http://localhost:8080/horaires-camions?du=" + encodeURIComponent(firstDate) + "&au=" + encodeURIComponent(lastDate);

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Erreur lors de la requête HTTP');
        return false;
    }

    httpRequest.onreadystatechange = function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                var jsonReponse = JSON.parse(httpRequest.responseText);
                addMarkers(jsonReponse.features);

                var nbCamions = 0;
                for(i = 0; i < jsonReponse.features.length; i++) {
                    nbCamions++;
                }
                document.getElementById("nbCamions").innerHTML = "Nombre d'horaires de camions trouvés: " + nbCamions;
            } else {
                alert('Erreur lors de la requête HTTP');
                return false;
            }
        }
    };

    httpRequest.open('GET', url);
    httpRequest.send();
}