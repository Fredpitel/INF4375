--DROP TABLE listefavoris;

--DROP TABLE usager;

--DROP TABLE arceau;

--DROP TABLE bixi;

--DROP TABLE foodtruck;


CREATE TABLE foodtruck(
	idFoodtruck SERIAL PRIMARY KEY,
	camion TEXT NOT NULL,
	lieu TEXT NOT NULL,
	heure_debut TEXT NOT NULL,
	heure_fin TEXT NOT NULL,
	jour TEXT NOT NULL,
	coord GEOMETRY(POINT, 4326) NOT NULL
);

CREATE TABLE bixi(
	idBixi SERIAL PRIMARY KEY,
	coord GEOMETRY(POINT, 4326) NOT NULL,
	nbBikes INTEGER NOT NULL,
	nbEmptyDocks INTEGER NOT NULL
);

CREATE TABLE arceau(
  idArceau SERIAL PRIMARY KEY,
  coord GEOMETRY(POINT, 4326) NOT NULL
);

CREATE TABLE usager(
	idusager SERIAL PRIMARY KEY,
);

CREATE TABLE listefavoris(
	idlistefavoris SERIAL PRIMARY KEY,
	idusager INTEGER,
	camion TEXT,
	FOREIGN KEY (idusager) REFERENCES usager(idusager)
)