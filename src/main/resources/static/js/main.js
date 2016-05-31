function getTrucks(){
    var firstDate = document.getElementById("firstDate").value;
    var lastDate = document.getElementById("lastDate").value;
    var url = "http://localhost:8080/horaires-camions?du=" + encodeURIComponent(firstDate) + "&au=" + encodeURIComponent(lastDate);

    httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
      alert('Erreur lors de la requête HTTP.');
      return false;
    }

    httpRequest.onreadystatechange = function(){
        if (httpRequest.readyState === XMLHttpRequest.DONE) {
          if (httpRequest.status === 200) {
            alert(httpRequest.responseText);
            var jsonReponse = JSON.parse(httpRequest.responseText);
            var nbCamions = 0;
            var i;
            for(i = 0; i < jsonReponse.features.length; i++) {
              nbCamions++;
            }
            document.getElementById("nbCamions").innerHTML = "Nombre de camions trouvés: " + nbCamions;
          } else {
            alert('Erreur lors de la requête HTTP ' + httpRequest.status);
          }
        }
    };

    httpRequest.open('GET', url);
    httpRequest.send();
}