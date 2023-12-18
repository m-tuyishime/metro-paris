import java.util.HashMap;
import java.util.LinkedList;

public class BellManFord extends Dijkstra {
    public BellManFord(Station depart, Station arrivee, HashMap<Integer, Station> stations) {
        // initialiser les distances aux stations a l'infini
        super(depart, arrivee, stations);
    }

    @Override
    public Trajet getTrajet() {
        // toutes les stations du metro
        LinkedList<Station> stations = new LinkedList<Station>(trajet.getStationsAVisiter());

        for (int i = 0; i < stations.size() - 1; i++) {
            // boucler sur toutes les stations
            for (Station station : stations) {
                // boucler sur toutes les connexions de la station
                for (Connection connexion : station.getConnexions()) {
                    // la destination de la connexion
                    Station destination = connexion.getDestination();
                    // le temps pour aller de la station a la destination
                    int tempsConnection = connexion.getTemps();
                    // le temps actuel pour aller de la station de depart a la destination
                    int tempsActuel = trajet.getTempsAStation(destination);

                    // Check pour overflow
                    if (tempsActuel != Integer.MAX_VALUE &&
                    // si le nouveau temps est plus petit que le temps actuel
                            tempsActuel + tempsConnection < trajet.getTempsAStation(destination)) {
                        // mettre a jour le temps pour aller de la station de depart a la destination
                        trajet.ajouterTempsAStation(destination, tempsActuel + tempsConnection);
                    }
                }
            }
        }
        return trajet;
    }

}
