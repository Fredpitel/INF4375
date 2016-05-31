var map;
var listMarkers = [];

function initMap(){
    map = L.map('mapid').setView([45.52, -73.66], 12);

    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
        maxZoom: 18,
        id: 'mapbox.streets'
        }).addTo(map);

}

function addMarkers(foodtrucks){
    removeMarkers();

    var i;
    for (i = 0; i < foodtrucks.length; i++) {
        var lon = foodtrucks[i].geometry.coordinates[0];
        var lat = foodtrucks[i].geometry.coordinates[1];
	    var latLong = new L.LatLng(lat, lon, true);
		var marker = new L.Marker(latLong);
        var popup = "<dl><dt>Camion: </dt>"
                    + "<dd>" + foodtrucks[i].properties.Camion + "</dd>"
                    + "<dt>Emplacement: </dt>"
                    + "<dd>" + foodtrucks[i].properties.Lieu + "</dd>"
                    + "<dt>Jour: </dt>"
                    + "<dd>" + foodtrucks[i].properties.Date + "</dd>"
                    + "<dt>Heure d'arrivée: </dt>"
                    + "<dd>" + foodtrucks[i].properties.Heure_debut + "</dd>"
                    + "<dt>Heure de départ: </dt>"
                    + "<dd>" + foodtrucks[i].properties.Heure_fin + "</dd>"
                    + "</dl>";

        marker.bindPopup(popup);
		marker.data = foodtrucks[i];
		map.addLayer(marker);
		listMarkers.push(marker);
    }
}

function removeMarkers() {
	for (i = 0; i < listMarkers.length;i++) {
		map.removeLayer(listMarkers[i]);
	}
	listMarkers = [];
}