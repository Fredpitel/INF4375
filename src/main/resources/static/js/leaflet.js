var map;
var listFoodtruckMarkers = [];
var listBixiMarkers = [];
var listVeloMarkers = [];
var markers;

function initMap(){
    markers = new L.MarkerClusterGroup();
    map = L.map('mapid').setView([45.52, -73.66], 12);

    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
        maxZoom: 18,
        id: 'mapbox.streets'
        }).addTo(map);
}

function makeMarkers(lat, lon){
	var latLong = new L.LatLng(lat, lon);
	var marker = new L.Marker(latLong);
    return marker;
}

function makeFoodtruckPopup(marker){
    var popup = "<dl><dt>Camion: </dt>"
                + "<dd>" + marker.data.properties.Camion + "</dd>"
                + "<dt>Emplacement: </dt>"
                + "<dd>" + marker.data.properties.Lieu + "</dd>"
                + "<dt>Jour: </dt>"
                + "<dd>" + marker.data.properties.Date + "</dd>"
                + "<dt>Heure d'arrivée: </dt>"
                + "<dd>" + marker.data.properties.Heure_debut + "</dd>"
                + "<dt>Heure de départ: </dt>"
                + "<dd>" +marker.data.properties.Heure_fin + "</dd></dl>";

    marker.bindPopup(popup);
    marker.on("mouseover", function (e) {
        marker.openPopup();
    })

    marker.on("mouseout", function (e) {
        marker.closePopup();
    })

    marker.on('click', function (e) {
        getBixis(marker.data.geometry.coordinates);
        getVelos(marker.data.geometry.coordinates);
    });

    markers.addLayer(marker)
	map.addLayer(markers);
	listFoodtruckMarkers.push(marker);
}

function removeFoodtruckMarkers() {
	for (i = 0; i < listFoodtruckMarkers.length; i++) {
		map.removeLayer(listFoodtruckMarkers[i]);
	}
	listFoodtruckMarkers = [];
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
	map.addLayer(marker);
	listBixiMarkers.push(marker);
}

function removeBixiMarkers() {
	for (i = 0; i < listBixiMarkers.length; i++) {
		map.removeLayer(listBixiMarkers[i]);
	}
	listBixiMarkers = [];
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
    map.addLayer(marker);
    listVeloMarkers.push(marker);
}

function removeVeloPopup(){
    for (i = 0; i < listVeloMarkers.length; i++) {
        map.removeLayer(listVeloMarkers[i]);
    }
    listVeloMarkers = [];
}

function removeVeloMarkers() {
    for (i = 0; i < listVeloMarkers.length; i++) {
        map.removeLayer(listVeloMarkers[i]);
    }
    listVeloMarkers = [];
}