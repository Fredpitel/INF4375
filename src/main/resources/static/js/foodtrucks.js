function Model(){
    this.subscribers = [];
    this.foodtruckClusters = null;
    this.foodtrucks = [];
    this.bixis = [];
    this.velos = [];
    this.favoriteFoodtrucks = [];
    this.userId = 1;
    this.map = null;
}

Model.prototype.notify = function(index){
    this.subscribers[index](this);
}

Model.prototype.addTrucks = function(foodtruck){
    this.foodtrucks.push(foodtruck);
    this.notify(1);
}

Model.prototype.removeTrucks = function(){
    removeMarkers(this);
    this.notify(1);
}

Model.prototype.addBixi = function(bixi){
    this.bixis.push(bixi);
    this.notify(2);
}

Model.prototype.removeBixis = function(){
    removeBixiMarkers(this);
    this.notify(2);
}

Model.prototype.addVelo = function(velo){
    this.velos.push(velo);
    this.notify(3);
}

Model.prototype.removeVelos = function(){
    removeVeloMarkers(this);
    this.notify(3);
}

Model.prototype.addFavoriteTrucks = function(favorite){
    this.favoriteFoodtrucks.push(favorite);
    this.notify(0);
}

Model.prototype.removeFavoriteTrucks = function(favorite){
    var index = this.favoriteFoodtrucks.indexOf(favorite);
    this.favoriteFoodtrucks.splice(index, 1);
    this.notify(0);
}


document.addEventListener('DOMContentLoaded', function () {
    var model = new Model();
    model.subscribers.push(renderFavoriteFoodtruckListView);
    model.subscribers.push(renderFoodtruckMarkersView);
    model.subscribers.push(renderBixiMarkersView);
    model.subscribers.push(renderVeloMarkersView);
    getFavoriteTrucksList(model);
    bindInputDateController(model);
    initMap(model);
});

function initMap(model){
    model.foodtruckClusters = new L.MarkerClusterGroup();

    model.map = L.map('mapid').setView([45.52, -73.66], 12);

    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
        maxZoom: 18,
        id: 'mapbox.streets'
    }).addTo(model.map);
}

function getFavoriteTrucksList(model) {
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
                for(var i = 0; i < jsonReponse.favoris.length; i++){
                    model.addFavoriteTrucks(jsonReponse.favoris[i]);
                }
            } else {
                alert('Erreur lors de la requête HTTP');
            }
        }
    };

    httpRequest.open('GET', url);
    httpRequest.send();
}

function renderFavoriteFoodtruckListView(model){
    var el = document.getElementById('favoriteTrucks');
    var html = "<h3>Camions favoris:</h3><br/>";
    for (var i = 0; i < model.favoriteFoodtrucks.length; i++){
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
        e.preventDefault();
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
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();

    if(dd < 10) {
        dd = '0' + dd
    }

    if(mm < 10) {
        mm = '0' + mm
    }

    today = yyyy + '-' + mm + '-' + dd;
    getTrucks(today,today, model);
}

function getTrucks(firstDate, lastDate, model){
    var url = "http://localhost:8080/horaires-camions?du=" + encodeURIComponent(firstDate) + "&au=" + encodeURIComponent(lastDate);
    var httpRequest = new XMLHttpRequest();
    var favoris = document.getElementById("favorisCheck");

    if (!httpRequest) {
        alert('Erreur lors de la requête HTTP');
        return false;
    }

    httpRequest.onreadystatechange = function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                var jsonReponse = JSON.parse(httpRequest.responseText);
                model.removeTrucks();
                for(var i = 0; i < jsonReponse.features.length; i++){
                    if(((favoris.checked) && (model.favoriteFoodtrucks.indexOf(jsonReponse.features[i].properties.Camion)) !== -1) || !(favoris.checked)){
                        marker = makeMarkers(jsonReponse.features[i].geometry.coordinates[1], jsonReponse.features[i].geometry.coordinates[0]);
                        marker.data = jsonReponse.features[i];
                        makeFoodtruckPopup(marker, model);
                        model.addTrucks(marker);
                    }
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
    for(var i = 0; i < model.foodtrucks.length; i++) {
        model.foodtruckClusters.addLayer(model.foodtrucks[i]);
        model.map.addLayer(model.foodtruckClusters);
        nbCamions++;
    }
    document.getElementById("nbCamions").innerHTML = "Nombre d'horaires de camions trouvés: " + nbCamions;
}

function getBixis(lat, lon, model){
    var url = "http://localhost:8080/bixis?lat=" + encodeURIComponent(lat) + "&lon=" +  encodeURIComponent(lon);
    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Erreur lors de la requête HTTP');
        return false;
    }

    httpRequest.onreadystatechange = function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                var jsonReponse = JSON.parse(httpRequest.responseText);
                for(var i = 0; i < jsonReponse.stations.length; i++) {
                    marker = makeMarkers(jsonReponse.stations[i].la, jsonReponse.stations[i].lo);
                    marker.data = jsonReponse.stations[i];
                    makeBixiPopup(marker);
                    model.addBixi(marker);
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

function renderBixiMarkersView(model){
    for(var i = 0; i < model.bixis.length; i++) {
        model.map.addLayer(model.bixis[i]);
    }
}

function getVelos(lat, lon, model){
    var url = "http://localhost:8080/arceaux?lat=" + encodeURIComponent(lat) + "&lon=" +  encodeURIComponent(lon);
    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Erreur lors de la requête HTTP');
        return false;
    }

    httpRequest.onreadystatechange = function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
            if (httpRequest.status === 200) {
                var jsonReponse = JSON.parse(httpRequest.responseText);
                for(var i = 0; i < jsonReponse.arceaux.length; i++) {
                    var marker = makeMarkers(jsonReponse.arceaux[i].la, jsonReponse.arceaux[i].lo);
                    marker.data = jsonReponse.arceaux[i];
                    makeVeloPopup(marker);
                    model.addVelo(marker)
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

function renderVeloMarkersView(model){
    for(var i = 0; i < model.velos.length; i++) {
        model.map.addLayer(model.velos[i]);
    }
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
            + "<input id='bixiVeloButton' type='button' value='Trouver' align='center'><br><br>"
            + "<input id='ajouterFavoris' type='button' value='Ajouter aux favoris' align='center'><br></div>"
            + "<div id='erreurAjout'></div>"

    marker.bindPopup(popup);
    marker.on("click", function (e) {
        marker.openPopup();
        bindPopupController(marker, model);
    })

    marker.on("close", function (e) {
        model.subscribers.pop();
    })
}

function makeBixiPopup(marker){
    var bixiIcon = L.icon({
        iconUrl: '/js/images/bixi-marker-icon.png',
        shadowUrl: '/js/images/marker-shadow.png'
    })

    var popup = "<h4 align=\'center\'>Station Bixi</h4>"
                + "<dl><dt>Nombre de vélo(s): </dt>"
                + "<dd>" + marker.data.ba + "</dd>"
                + "<dt>Nombre de place(s) libre(s): </dt>"
                + "<dd>" + marker.data.da + "</dd></dl>";

    marker.on("mouseover", function (e) {
        marker.openPopup();
    })

    marker.on("mouseout", function (e) {
        marker.closePopup();
    })

    marker.bindPopup(popup);
    marker.setIcon(bixiIcon);
}

function makeVeloPopup(marker){
    var veloIcon = L.icon({
        iconUrl: '/js/images/velo-icon.png',
        shadowUrl: '/js/images/marker-shadow.png'
    })

    var popup = "<h4 align=\'center\'>Arceaux de velo</h4>";

    marker.on("mouseover", function (e) {
        marker.openPopup();
    })

    marker.on("mouseout", function (e) {
        marker.closePopup();
    })

    marker.bindPopup(popup);
    marker.setIcon(veloIcon);
}

function bindPopupController(marker, model){
    var bixiVeloButton = document.getElementById("bixiVeloButton");
    var ajouterFavoris = document.getElementById("ajouterFavoris");
    var favoris = false;

    if(model.favoriteFoodtrucks.indexOf(marker.data.properties.Camion) !== -1){
        ajouterFavoris.value = "Enlever des favoris";
        favoris = true;
    }

    bixiVeloButton.addEventListener('click', function(e) {
        e.preventDefault();
        lat = marker.data.geometry.coordinates[1];
        lon = marker.data.geometry.coordinates[0];
        model.removeBixis();
        model.removeVelos();
        bixi = document.getElementById("bixiCheck");
        arceau = document.getElementById("arceauCheck");

        if (bixi.checked)
            getBixis(lat, lon, model);
        if (arceau.checked)
            getVelos(lat ,lon, model);
    });

    ajouterFavoris.addEventListener('click', function(e) {
        e.preventDefault();
        if(!favoris){
            model.addFavoriteTrucks(marker.data.properties.Camion);
            favoris = true;
            ajouterFavoris.value = "Enlever des favoris";
        } else {
            model.removeFavoriteTrucks(marker.data.properties.Camion);
            favoris = false;
            ajouterFavoris.value = "Ajouter aux favoris";
        }
    });
}

function removeMarkers(model){
    for (var i = 0; i < model.foodtrucks.length; i++) {
        model.map.removeLayer(model.foodtrucks[i]);
    }
    model.map.removeLayer(model.foodtruckClusters);
    model.foodtruckClusters = new L.MarkerClusterGroup();
    model.foodtrucks = [];

    model.removeBixis();
    model.removeVelos();
}

function removeBixiMarkers(model) {
	for (var i = 0; i < model.bixis.length; i++) {
		model.map.removeLayer(model.bixis[i]);
	}
	model.bixis = [];
}

function removeVeloMarkers(model) {
	for (var i = 0; i < model.velos.length; i++) {
		model.map.removeLayer(model.velos[i]);
	}
	model.velos = [];
}