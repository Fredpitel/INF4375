document.addEventListener('DOMContentLoaded', function () {
  var model = new Model();
  model.subscribers.push(renderFavoriteFoodtruckCollectionView);
  model.subscribers.push(renderFoodtruckMarkersView);
  getFavoriteTrucks(model)
  bindInputDateController(model);
  initMap(model);
});

class Model{
    constructor() {
        this.subscribers = [];
        this.foodtruckClusters = null;
        this.foodtrucks = [];
        this.bixis = [];
        this.velos = [];
        this.favoriteFoodtrucks = [];
        this.userId = 1;
        this.map = null;
    }

    notify(){
        this.subscribers.forEach(function (each){
            each(this);
        }.bind(this));
    }

    addTrucks(foodtruck){
        this.foodtrucks.push(foodtruck);
        this.notify();
    }

    removeTrucks(){
        removeMarkers(this);
        this.notify();
    }

    addBixi(marker){
        this.bixiMarkes.push(marker);
        this.notify();
    }

    addVelo(marker){
        this.veloMarkers.push(marker);
        this.notify();
    }

    addFavoriteTrucks(liste){
        this.favoriteFoodtrucks = liste;
        this.notify();
    }
}

function initMap(model){
    model.foodtruckClusters = new L.MarkerClusterGroup();

    model.map = L.map('mapid').setView([45.52, -73.66], 12);

    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
        maxZoom: 18,
        id: 'mapbox.streets'
    }).addTo(model.map);
}

function getFavoriteTrucks(model) {
    var url = "http://localhost:8080/" + model.userId + "/favoris";
    var httpRequest = new XMLHttpRequest();
    var favorites = [];

    if (!httpRequest) {
        alert('Erreur lors de la requête HTTP');
        return favorites;
    }

    httpRequest.onreadystatechange = function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                var jsonReponse = (JSON.parse(httpRequest.responseText));

                model.addFavoriteTrucks(jsonReponse.favoris);
            } else {
                alert('Erreur lors de la requête HTTP');
            }
        }
    };

    httpRequest.open('GET', url);
    httpRequest.send();
}

function renderFavoriteFoodtruckCollectionView(model){
    var el = document.getElementById('favoriteTrucks');
    var html = "<h3>Camions préférés:</h3><br/>";
    for (i = 0; i < model.favoriteFoodtrucks.length; i++){
        html += "<li>" + model.favoriteFoodtrucks[i] + "</li><br/>";
    }
    el.innerHTML = html;
}

function bindInputDateController(model) {
    var form = document.getElementById('form');
    var today = document.getElementById('today')

    form.addEventListener('submit', function(e) {
        e.preventDefault();
        validateDate(model);
    });

    today.addEventListener('click', function(e) {
        getToday(model);
    });
}

function validateDate(model){
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

    getTrucks(firstDate, lastDate, model);
}

function getToday(model){
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
    getTrucks(today,today, model);
}

function getTrucks(firstDate, lastDate, model){
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
                model.removeTrucks();
                for(i = 0; i < jsonReponse.features.length; i++){
                    marker = makeMarkers(jsonReponse.features[i].geometry.coordinates[1], jsonReponse.features[i].geometry.coordinates[0]);
                    marker.data = jsonReponse.features[i];
                    makeFoodtruckPopup(marker, model);
                    model.addTrucks(marker);
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

function renderFoodtruckMarkersView(model){
    var nbCamions = 0;
    for(i = 0; i < model.foodtrucks.length; i++) {
        model.foodtruckClusters.addLayer(model.foodtrucks[i]);
        model.map.addLayer(model.foodtruckClusters);
        nbCamions++;
    }
    document.getElementById("nbCamions").innerHTML = "Nombre d'horaires de camions trouvés: " + nbCamions;
}

function makeMarkers(lat, lon){
	latLong = new L.LatLng(lat, lon);
	marker = new L.Marker(latLong);
    return marker;
}

function makeFoodtruckPopup(marker, model){
    coord = marker.data.geometry.coordinates;
    popup = "<dl><dt>Camion: </dt>"
            + "<dd>" + marker.data.properties.Camion + "</dd>"
            + "<dt>Emplacement: </dt>"
            + "<dd>" + marker.data.properties.Lieu + "</dd>"
            + "<dt>Jour: </dt>"
            + "<dd>" + marker.data.properties.Date + "</dd>"
            + "<dt>Heure d'arrivée: </dt>"
            + "<dd>" + marker.data.properties.Heure_debut + "</dd>"
            + "<dt>Heure de départ: </dt>"
            + "<dd>" +marker.data.properties.Heure_fin + "</dd></dl>"
            + "<input id='bixiCheck' type='checkbox' name='vehicle'> Bixi<br>"
            + "<input id='arceauCheck' type='checkbox' name='vehicle'> Arceau<br><br>"
            + "<div align=\'center\'>"
            + "<input id='bixiVeloButton' type='button' value='Trouver' align='center'><br>";
            + "</div>"

    marker.bindPopup(popup);
    marker.on("click", function (e) {
        marker.openPopup();
    })

    //bindPopupController(marker, model);
}

function bindPopupController(marker, model){
    bixiVeloButton = document.getElementById("bixiVeloButton");

    bixiVeloButton.addEventListener('click', function(e) {
        lat = marker.data.geometry.coordinates[1];
        lon = marker.data.geometry.coordinates[0];

        e.preventDefault();
        removeBixiMarkers();
        removeVeloMarkers();
        bixi = document.getElementById("bixiCheck");
        arceau = document.getElementById("arceauCheck");

        if (bixi.checked)
            getBixis(lat, lon);
        if (arceau.checked)
            getVelos(lat ,lon);
    });
}

function removeMarkers(model){
    for (i = 0; i < model.foodtrucks.length; i++) {
        model.map.removeLayer(model.foodtrucks[i]);
    }
    model.map.removeLayer(model.foodtruckClusters);
    model.foodtruckClusters = new L.MarkerClusterGroup();
    model.foodtrucks = [];

    removeBixiMarkers(model);
    removeVeloMarkers(model);
}

function removeBixiMarkers(model) {
	for (i = 0; i < model.bixis.length; i++) {
		model.map.removeLayer(model.bixis[i]);
	}
	model.bixis = [];
}

function removeVeloMarkers(model) {
	for (i = 0; i < model.velos.length; i++) {
		model.map.removeLayer(model.velos[i]);
	}
	model.velos = [];
}