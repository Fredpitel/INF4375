CREATE TABLE foodtruck(
	idFoodtruck SERIAL PRIMARY KEY,
	camion TEXT NOT NULL,
	lieu TEXT NOT NULL,
	heure_debut TEXT NOT NULL,
	heure_fin TEXT NOT NULL,
	jour TEXT NOT NULL,
	coord GEOMETRY(POINT, 4326) NOT NULL
);