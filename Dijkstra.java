import java.util.HashMap;

public class Dijkstra {
    private Trajet trajet;

    public Dijkstra(Station depart, Station arrivee, HashMap<Integer, Station> stations) {
        // initialiser le trajet
        this.trajet = new Trajet();

        // initialiser les temps requis pour aller de la station de depart a toutes les
        // autres stations a l'infini
        for (Station station : stations.values()) {
            // si la station est la station de depart, le temps requis est 0
            if (station.equals(depart))
                this.trajet.ajouterTempsAStation(station, 0);
            else {
                this.trajet.ajouterTempsAStation(station, Integer.MAX_VALUE);
            }

            // marquer toutes les stations comme non visitees
            this.trajet.ajouterStationAVisiter(station);
        }
    }

    public Trajet getTrajet() {
        // tant qu'il reste des stations a visiter
        while (!trajet.getStationsAVisiter().isEmpty()) {
            // trouver la station la plus proche
            Station plusProche = null;
            for (Station station : trajet.getStationsAVisiter()) {
                if (plusProche == null || trajet.getTempsAStation(station) < trajet.getTempsAStation(plusProche)) {
                    plusProche = station;
                }
            }

            // visiter la station la plus proche
            trajet.visiterStation(plusProche);

            // boucler sur les connexions de la station la plus proche
            for (Connection connexion : plusProche.getConnexions()) {
                // la destination de la connexion
                Station destination = connexion.getDestination();
                // le temps pour aller de la station la plus proche a la destination
                int tempsConnection = connexion.getTemps();
                // le temps actuel pour aller de la station de depart a la destination
                int tempsActuel = trajet.getTempsAStation(destination);
                // le nouveau temps pour aller de la station de depart a la destination
                // en passant par la station la plus proche
                int tempsNouveau = trajet.getTempsAStation(plusProche) + tempsConnection;

                // si le nouveau temps est plus petit que le temps actuel
                if (tempsNouveau < tempsActuel) {
                    // mettre a jour le temps pour aller de la station de depart a la destination
                    trajet.ajouterTempsAStation(destination, tempsNouveau);
                }
            }
        }

        return trajet;
    }
}
