function validateDate(){
    var firstDate = document.getElementById("firstDate").value;
    var lastDate = document.getElementById("lastDate").value;

    if(!firstDate || !lastDate ){
        alert("Veuillez entrer deux dates valides.");
        return false;
    }

    if(!(/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/.test(firstDate)) || !(/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/.test(lastDate)) ){
        alert("Le format d'entrée des dates est AAAA-MM-JJ");
        return false;
    }

    if(firstDate > lastDate){
        alert("La deuxième date doit être plus grande ou égale à la première.");
        return false;
    }

    getTrucks(firstDate, lastDate);
}

function getTrucks(firstDate, lastDate){
    var url = "http://localhost:8080/horaires-camions?du=" + encodeURIComponent(firstDate) + "&au=" + encodeURIComponent(lastDate);
    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Erreur lors de la requête HTTP');
        return false;
    }

    httpRequest.onreadystatechange = function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                var jsonReponse = JSON.parse(httpRequest.responseText);
                var nbCamions = 0;

                removeFoodtruckMarkers();
                removeBixiMarkers();
                for(i = 0; i < jsonReponse.features.length; i++) {
                    var marker = makeMarkers(jsonReponse.features[i].geometry.coordinates[0], jsonReponse.features[i].geometry.coordinates[1]);
                    marker.data = jsonReponse.features[i];
                    makeFoodtruckPopup(marker)
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

function getBixis(coordinates){
    var url = "http://localhost:8080/bixis?lat=" + encodeURIComponent(coordinates[1]) + "&lon=" +  encodeURIComponent(coordinates[0]);
    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Erreur lors de la requête HTTP');
        return false;
    }

    httpRequest.onreadystatechange = function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                var jsonReponse = JSON.parse(httpRequest.responseText);
                removeBixiMarkers();
                for(i = 0; i < jsonReponse.stations.length; i++) {
                    var marker = makeMarkers(jsonReponse.stations[i].lo, jsonReponse.stations[i].la);
                    marker.data = jsonReponse.stations[i];
                    makeBixiPopup(marker)
                }
            } else {
                alert('Erreur lors de la requête HTTP');
                return false;
            }
        }

    };

    httpRequest.open('GET', url);
    httpRequest.send();
}