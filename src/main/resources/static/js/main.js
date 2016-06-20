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

function getToday(){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();

    if(dd<10) {
        dd='0'+dd
    }

    if(mm<10) {
        mm='0'+mm
    }

    today = yyyy+'-'+mm+'-'+dd;
    getTrucks(today,today);
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
                    var marker = makeMarkers(jsonReponse.features[i].geometry.coordinates[1], jsonReponse.features[i].geometry.coordinates[0]);
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
                    var marker = makeMarkers(jsonReponse.stations[i].la, jsonReponse.stations[i].lo);
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

function getVelos(coordinates){
    var url = "http://localhost:8080/arceaux?lat=" + encodeURIComponent(coordinates[1]) + "&lon=" +  encodeURIComponent(coordinates[0]);
    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Erreur lors de la requête HTTP');
        return false;
    }

    httpRequest.onreadystatechange = function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                var jsonReponse = JSON.parse(httpRequest.responseText);
                removeVeloMarkers();
                for(i = 0; i < jsonReponse.arceaux.length; i++) {
                    var marker = makeMarkers(jsonReponse.arceaux[i].la, jsonReponse.arceaux[i].lo);
                    marker.data = jsonReponse.arceaux[i];
                    makeVeloPopup(marker)
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